package at.ac.tuwien.swa.SWAzam.Client.Controller;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.StoredFingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor.FingerprintExtractorTask;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.LoginDialog;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.MainFrame;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetriever;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetrieverFactory;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.FileRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.IRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.MicRecorder;
import com.google.inject.Inject;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class Controller implements PropertyChangeListener {
    private final static Logger log = Logger.getLogger(Controller.class.getName());

    MainFrame mFrame;
    LoginDialog lFrame;
    IRecorder recorder;
    MetaDataRetriever retriever;
    User user;

    Connection con;

    FingerprintExtractorTask fpet;
    ProcessingTask pt;

    @Inject
    public Controller(MetaDataRetrieverFactory retrieverFactory){
        this.initializeDatabase();

        retriever = retrieverFactory.create(con);
        mFrame = new MainFrame(this);
        lFrame = new LoginDialog(this, mFrame);
        user = null;

        this.setLookAndFeel();
    }

    public void showComponents() {
        if(!restoreLogin())
            this.showLoginFrame();
        else
            mFrame.setVisible(true);
    }

    private void initializeDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:file:" + this.getClass().getResource("/Database").getFile() + "/localdb", "SA", "");
            log.info("Successfully connected to Database!");
        } catch (SQLException e) {
            System.err.println("Error while connecting to Database!");
            
            System.exit(1);
        }
    }

    private boolean restoreLogin() {
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT Username, Password FROM LoggedIn");

            if(rs.next()){
                this.setUser(new User(rs.getString("Username"), rs.getString("Password")), true);
                return true;
            }
        } catch (SQLException e) {
            //TODO: Remove Stacktrace
            e.printStackTrace();
        }

        return false;
    }

    public void loadMp3(File audioFile){
        recorder = new FileRecorder(audioFile);
        processInputStream(recorder.getStream());
    }

    public void startRecordingFromMic(){
        recorder = new MicRecorder();
        ((MicRecorder)recorder).startRecording();
    }

    public void stopRecordingFromMic(){
        if(recorder != null){
            ((MicRecorder)recorder).stopRecording();
            processInputStream(recorder.getStream());
        }
    }

    private void processInputStream(AudioInputStream stream){
        fpet = new FingerprintExtractorTask(stream);
        fpet.addPropertyChangeListener(this);
        fpet.execute();
    }

    public void showLoginFrame(){
        setUser(null, false);
        lFrame.setVisible(true);

        mFrame.setVisible(true);
    }

    public void setUser(User u, boolean rememberLogin){
        Statement stmt;

        if(u != null){
            user = verifyUser(u);

            if(user == null){
                //User tried to login but was not accepted by swazam
                mFrame.showLoginError();
            }
            else if(user != null){
                //User tried to login and was accepted by swazam

                if(rememberLogin){
                    try {
                        stmt = con.createStatement();
                        stmt.execute("DELETE FROM LoggedIn");
                        PreparedStatement pstmt = con.prepareStatement("INSERT INTO LoggedIn (Username, Password) VALUES (?, ?)");
                        pstmt.setString(1, user.getUsername());
                        pstmt.setString(2, user.getPassword());

                        pstmt.execute();
                    } catch (SQLException e) {
                        //TODO: Remove StackTrace
                        e.printStackTrace();
                    }
                }

                this.updateResultTable();
            }
        }
        else{
            //User logged out
            try {
                stmt = con.createStatement();
                stmt.execute("DELETE FROM LoggedIn");
            } catch (SQLException e) {
                //TODO: Remove StackTrace
                e.printStackTrace();
            }
        }

        mFrame.updateUI(user, retriever.getRegisteredPeersAmount());
    }

    private User verifyUser(User u){
        return retriever.verifyUser(u);
    }

    private void setLookAndFeel() {
//        UIManager.put("ProgressBar.repaintInterval", new Integer(50));
//        UIManager.put("ProgressBar.cycleTime", new Integer(50));

        if(!System.getProperty("os.name").toLowerCase().contains("mac")){
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if(info.getName().equals("Nimbus")){
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                //Default LAF is used
            }
        }
    }

    public void updateProgress(int progress, boolean indeterminate) {
        mFrame.updateProgressBar(progress, indeterminate);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == fpet){
            if(!fpet.isDone())
                updateProgress(fpet.getProgress(), true);
            else{
                updateProgress(50, false);
                processFingerprint(fpet.getResult());
            }
        }
        else if(evt.getSource() == pt){
            if(!pt.isDone())
                updateProgress(50 + pt.getProgress()/2, true);
            else{
                updateProgress(100, false);
                storeFingerprintResult(pt.getResult());
            }
        }
    }

    private void storeFingerprintResult(FingerprintResult result) {
        //TODO: Show new FingerprintResult and store it to the Database
    }

    private void processFingerprint(Fingerprint fp) {
        log.info("Processing Fingerprint!");
        pt = new ProcessingTask(retriever, fp, user);
        pt.addPropertyChangeListener(this);
        pt.execute();
    }

    private void updateResultTable(){
        List<StoredFingerprint> fingerprints = new ArrayList<StoredFingerprint>();
        PreparedStatement pstmt;
        ResultSet rs;

        try{
            pstmt = con.prepareStatement("SELECT Timestamp, Artist, Songtitle FROM Fingerprints WHERE LOWER(Username) = ? ORDER BY Timestamp DESC");
            pstmt.setString(1, user.getUsername().toLowerCase());

            rs = pstmt.executeQuery();

            while(rs.next()){
                fingerprints.add(new StoredFingerprint(rs.getString("Artist"), rs.getString("Songtitle"), rs.getTimestamp("Timestamp")));
            }

            mFrame.updateResultTable(fingerprints);
        }
        catch(SQLException e){
            //TODO: Remove StackTrace
            e.printStackTrace();
        }
    }
}
