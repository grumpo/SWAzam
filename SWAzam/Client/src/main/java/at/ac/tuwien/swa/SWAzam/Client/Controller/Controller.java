package at.ac.tuwien.swa.SWAzam.Client.Controller;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Entities.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor.FingerprintExtractor;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.LoginDialog;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.MainFrame;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetriever;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.FileRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.IRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.MicRecorder;

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class Controller {
    private final static Logger log = Logger.getLogger(Controller.class.getName());

    MainFrame mFrame;
    LoginDialog lFrame;
    IRecorder recorder;
    MetaDataRetriever retriever;
    User user;

    Connection con;

    public Controller(){
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:file:database/localdb", "SA", "");
            log.info("Successfully connected to database!");
            retriever = new MetaDataRetriever();
            mFrame = new MainFrame(this);
            lFrame = new LoginDialog(this, mFrame);
            user = null;

            this.setLookAndFeel();

            if(!restoreLogin())
                this.showLoginFrame();
            else
                mFrame.setVisible(true);
        } catch (SQLException e) {
            System.err.println("Error while connecting to database!");
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

        processFingerprint(FingerprintExtractor.extractFingerPrint(recorder.getStream()));
    }

    public void startRecordingFromMic(){
        recorder = new MicRecorder();
        ((MicRecorder)recorder).startRecording();
    }

    public void stopRecordingFromMic(){
        if(recorder != null){
            ((MicRecorder)recorder).stopRecording();
            processFingerprint(FingerprintExtractor.extractFingerPrint(recorder.getStream()));
        }
    }

    private void processFingerprint(Fingerprint fp){
        FingerprintResult fpr;

        if(fp != null){
            fpr = retriever.getFingerprintResult(fp, user);
        }
        else{
            log.log(Level.WARNING, "Fingerprint is null and cannot be processed!");
        }
    }

    public void showLoginFrame(){
        setUser(null, false);
        lFrame.setVisible(true);

        mFrame.setVisible(true);
    }

    public void setUser(User u, boolean rememberLogin){
        Statement stmt;
        user = verifyUser(u);

        mFrame.updateUI(user, retriever.getRegisteredPeersAmount());

        if(u != null && user == null){
            //User tried to login but was not accepted by swazam
        }
        else if(u != null && user != null){
            //User tired to login and was accepted by swazam

            if(rememberLogin){
                //TODO: REMEMBER LOGIN

                try {
                    stmt = con.createStatement();
                    stmt.execute("DELETE FROM LoggedIn");
                    PreparedStatement pstmt = con.prepareStatement("INSERT INTO LoggedIn (Username, Password) VALUES (?, ?)");
                    pstmt.setString(1, user.getUsername());
                    pstmt.setString(2, user.getPassword());

                    pstmt.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            //User logged out
            try {
                stmt = con.createStatement();
                stmt.execute("DELETE FROM LoggedIn");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private User verifyUser(User u){
        //TODO: Get User Information From Server and Verify Validity of Account

        return u;
    }

    private void setLookAndFeel() {
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
}
