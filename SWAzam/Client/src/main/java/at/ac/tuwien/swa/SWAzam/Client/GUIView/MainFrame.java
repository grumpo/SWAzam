package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.Entities.StoredFingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.ImageButton;
import at.ac.tuwien.swa.SWAzam.Client.Util.MusicFileFilter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Created by markus on 17.12.13.
 */
public class MainFrame extends JFrame implements ActionListener {
    JPanel mainPanel;
    JPanel infoPanel;
    JPanel buttonPanel;
    JPanel historyPanel;

    JMenuBar menu;
    JMenu loginInfoMenu;

    JMenuItem fileMenuExit;
    JMenuItem loginInfoMenuLogout;
    JMenuItem helpMenuAbout;

    JLabel availableCoins;
    JLabel registeredPeers;

    Icon recordIcon;
    Icon stopIcon;

    ImageButton recordButton;
    ImageButton fileButton;

    JTable historyTable;

    JFileChooser fileChooser;

    JProgressBar progressBar;

    boolean recording;

    Controller c;

    public MainFrame(Controller c){
        this.c = c;
        initComponents();
    }

    private void initComponents(){
        this.setTitle("SWAzam - P2P Music Recognition");

        mainPanel = new ImagePanel(this.getClass().getResource("/Images/background.png").getFile());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setForeground(Color.lightGray);

        menu = new JMenuBar();
        loginInfoMenu = new JMenu("Logged in as Z");
        loginInfoMenu.setPreferredSize(new Dimension(280, 20));
        JMenu helpMenu = new JMenu("Help");

        fileMenuExit = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/Images/exit.png").getFile()));
        loginInfoMenuLogout = new JMenuItem("Logout", new ImageIcon(this.getClass().getResource("/Images/logout.png").getFile()));
        helpMenuAbout = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/Images/about.png").getFile()));

        loginInfoMenu.add(loginInfoMenuLogout);
        loginInfoMenu.add(fileMenuExit);
        helpMenu.add(helpMenuAbout);

        fileMenuExit.addActionListener(this);
        loginInfoMenuLogout.addActionListener(this);
        helpMenuAbout.addActionListener(this);

        menu.add(loginInfoMenu);
        menu.add(Box.createHorizontalGlue());
        menu.add(helpMenu);

        this.setJMenuBar(menu);

        // Infopanel
        infoPanel = new JPanel();
        infoPanel.setLayout(new MigLayout(
                "",
                "[300!]",
                ""
        ));

        availableCoins = new JLabel("Available Coins: X", JLabel.CENTER);
        availableCoins.setForeground(Color.lightGray);
        registeredPeers = new JLabel("Registered Peers: Y", JLabel.CENTER);
        registeredPeers.setForeground(Color.lightGray);

        infoPanel.add(availableCoins, "wrap, growx");
        infoPanel.add(registeredPeers, "growx");

        // Buttonpanel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout(
                "",
                "[50!][140!][60!][30!]",
                "[20!][20!][140!]"));

        recordIcon = new ImageIcon(this.getClass().getResource("/Images/microphone.png").getFile());
        stopIcon = new ImageIcon(this.getClass().getResource("/Images/stop.png").getFile());
        ImageIcon recordIconDisabled = new ImageIcon(this.getClass().getResource("/Images/microphone_disabled.png").getFile());
        ImageIcon fileIcon = new ImageIcon(this.getClass().getResource("/Images/folder.png").getFile());
        ImageIcon fileIconDisabled = new ImageIcon(this.getClass().getResource("/Images/folder_disabled.png").getFile());

        BackgroundPanel recordButtonPanel = new BackgroundPanel(180, 180);
        BackgroundPanel fileButtonPanel = new BackgroundPanel(80, 80);

        recordButtonPanel.setLayout(new MigLayout("","59![]","31![]"));
        fileButtonPanel.setLayout(new BorderLayout());

        recordButton = new ImageButton(recordIcon, recordIconDisabled, 61, 118);
        fileButton = new ImageButton(fileIcon, fileIconDisabled, 32, 26);

        recordButton.addActionListener(this);
        fileButton.addActionListener(this);
        fileButtonPanel.add(fileButton);
        recordButtonPanel.add(recordButton, BorderLayout.CENTER);

        buttonPanel.add(recordButtonPanel, "cell 1 1 2 1");
        buttonPanel.add(fileButtonPanel, "cell 2 0 1 2");

        // Historypanel
        historyPanel = new JPanel();
        historyPanel.setLayout(new MigLayout(
                "",
                "[300!]",
                "[][][120!]"
        ));

        historyTable = new JTable(new DefaultTableModel(new Object[] {"Artist", "Songtitle", "Time"}, 0));
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(50);

        historyTable.setBackground(Color.lightGray);
        historyTable.setForeground(Color.darkGray);
        historyTable.setOpaque(false);
        JScrollPane tableScroll = new JScrollPane(historyTable);

        progressBar = new JProgressBar(0, 100);
        historyPanel.add(progressBar, "grow, wrap");

        JLabel historyLabel = new JLabel("<html><body><strong>SWAzam History</strong></body></html>");
        historyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        historyLabel.setForeground(Color.lightGray);

        historyPanel.add(historyLabel, "grow, wrap");
        historyPanel.add(tableScroll, "grow");

        infoPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        historyPanel.setOpaque(false);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setResizable(false);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MusicFileFilter());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == recordButton){
            if(!recording){
                recording = true;
                recordButton.setIconActive(stopIcon);
                fileButton.setEnabled(false);

                c.startRecordingFromMic();
            }
            else{
                recording = false;
                recordButton.setIconActive(recordIcon);
                fileButton.setEnabled(true);

                c.stopRecordingFromMic();
            }
        }
        else if(e.getSource() == fileButton){
            int result = fileChooser.showOpenDialog(this);
            File file;

            if(result == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();

                c.loadMp3(file);
            }
        }
        else if(e.getSource() == fileMenuExit){
            System.exit(0);
        }
        else if(e.getSource() == loginInfoMenuLogout){
            c.showLoginFrame();
        }
        else if(e.getSource() == helpMenuAbout){
            showInfo();
        }
    }

    public void showInfo() {
        final JFrame infoFrame = new JFrame("About SWAzam");
        JPanel panel_center = new JPanel();
        JLabel text = new JLabel();

        panel_center.setOpaque(false);
        infoFrame.getContentPane().setLayout(new MigLayout());
        infoFrame.setBounds(400, 200, 300, 250);
        infoFrame.setResizable(false);
        JButton aboutFrameOkBtn = new JButton("Ok");
        aboutFrameOkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoFrame.setVisible(false);
            }
        });

        text.setText("<html><body align='center'><h2>About SWAzam</h2>SWAzam was developed as an exercise for the<br/>" +
                "course Software Architecture (184.159-2013W)<br/>" +
                "at the Vienna University of Technology by<br/><br/>" +
                " <strong>Rumpold Gernot</strong> (0728159)<br/>" +
                "<strong>Binder Johannes</strong> (0727990)<br/>" +
                "<strong>Weilharter Manuela</strong> (0826002)<br/>" +
                "<strong>Wolf Markus</strong> (0825595)<br/>" +
                "<strong>El-Isa Kamil</strong> (0726881)</body></html>");

        panel_center.add(text);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        infoFrame.getContentPane().add(text, "gapleft 10, gapright 10, gaptop 1, gapbottom 1, wrap");
        infoFrame.getContentPane().add(aboutFrameOkBtn, "center");

        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    public void updateUI(User user, int registeredPeersAmount) {
        if(user != null){
            availableCoins.setText("Available Coins: " + user.getCoins());
            registeredPeers.setText("Registered Peers: " + registeredPeersAmount);

            loginInfoMenuLogout.setText("Logout");
            loginInfoMenu.setText("Logged in as " + user.getUsername());
        }
        else{
            availableCoins.setText("Available Coins: ");
            registeredPeers.setText("Registered Peers: ");

            loginInfoMenuLogout.setText("Login");
            loginInfoMenu.setText("Not logged in");
        }

        mainPanel.setEnabled(user != null);
        recordButton.setEnabled(user != null);
        fileButton.setEnabled(user != null);
    }

    public void updateProgressBar(int progress, boolean indeterminate){
        progressBar.setStringPainted(true);

        if(progress == 0){
            progressBar.setString("Extracting Fingerprint");
            loginInfoMenuLogout.setEnabled(false);
            recordButton.setEnabled(false);
            fileButton.setEnabled(false);
        }
        else if(progress == 50){
            progressBar.setString("Retrieving Information");
        }
        else if(progress == 100){
            progressBar.setString("Finished Processing");
            loginInfoMenuLogout.setEnabled(true);
            recordButton.setEnabled(true);
            fileButton.setEnabled(true);
        }

        progressBar.setIndeterminate(indeterminate);
        progressBar.setValue(progress);
    }

    public void updateResultTable(List<StoredFingerprint> fingerprints) {
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0);

        for(StoredFingerprint sfp : fingerprints){
            model.addRow(new Object[]{sfp.getArtist(), sfp.getSongtitle(), sfp.getTimestamp()});
        }
    }
}
