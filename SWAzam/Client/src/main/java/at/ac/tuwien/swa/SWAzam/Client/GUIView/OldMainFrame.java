package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;


import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

/**
 * The client GUI
 * Created by Karatekiwi on 12/17/13.
 */
public class OldMainFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(OldMainFrame.class.getName());

    private Controller controller;

	private JMenuBar mbar;
	private User user;
	
	private JMenuItem exitM;
	private JMenuItem logoutM;
	private JMenuItem faq;
	private JMenuItem about;
	
	private JFrame infoFrame;
	private JButton aboutFrameOkBtn;
	
	private JPanel panel_south;
	
	private JFrame chooseFileFrame;
	private JFileChooser fileChooser;
	
	private JLabel progress_label;
	
	private JTable table;
	
	private JButton record, open_file;
	private ImageIcon record_icon, record_stop_icon;

    public OldMainFrame(User user) {
    	this.user = user;
    	log.info("Logged in with username: " + this.user.getUsername() + " and password: " + this.user.getPassword());
    	initComponents();	
    }

    //New Constructor that is called from the controller: should load mainframe, show loginframe and then work as before
    public OldMainFrame(Controller c){
        this.controller = c;
        //new LoginDialog(this);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
        initComponents();
    }

	private void initComponents() {
	    setTitle("SWAzam");
	    setSize(600, 700);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    mbar = new JMenuBar();
   
	    JMenu file = new JMenu("File");
	    JMenu help = new JMenu("Help");
	    JMenu logout = new JMenu("Logged in as " + user.getUsername());
	    
	    exitM = new JMenuItem("Exit");
	    logoutM = new JMenuItem("Logout");
        faq = new JMenuItem("F.A.Q");
        about = new JMenuItem("About");
        
        file.add(exitM);
        help.add(faq);
        help.add(about);
        logout.add(logoutM);
        
        exitM.addActionListener(this);
        logoutM.addActionListener(this);
        faq.addActionListener(this);
        about.addActionListener(this);
        
        mbar.add(file);
        mbar.add(help);
        mbar.add(Box.createHorizontalGlue());
        mbar.add(logout);

        JPanel panel_center = new JPanel();
        panel_center.setLayout(new GridBagLayout());
        JLabel overview = new JLabel();
        overview.setHorizontalAlignment(JLabel.CENTER);
        JLabel coins = new JLabel();
        JLabel progress = new JLabel();
        Font f = new Font(Font.SERIF, 0, 20);
        overview.setFont(f);
        coins.setFont(f);
        coins.setHorizontalAlignment(JLabel.CENTER);
        coins.setFont(f);
        progress.setFont(f);
        progress.setHorizontalAlignment(JLabel.CENTER);
        
        overview.setText("Request History");
        overview.setBorder(new EmptyBorder(10, 10, 10, 10));
                
        coins.setText("Coins");
        coins.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        progress.setText("Current Progress");
        progress.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        
        panel_center.add(overview, c);
        fillTablewithDummyData();
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setMinimumSize(new Dimension(600, 100));
        
        c.gridy = 1;
        
        c.fill = GridBagConstraints.BOTH; 
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel_center.add(tablePane, c);
        
        c.fill = GridBagConstraints.NONE; 
        c.gridy = 2;
        panel_center.add(coins, c);
        
        //TODO get the # coins
        JLabel coin_label = new JLabel();
        coin_label.setHorizontalAlignment(SwingConstants.CENTER); 
        coin_label.setText("You have currently " + 5 + " coins left.");
        
        c.gridy = 3;
        panel_center.add(coin_label, c);
        c.gridy = 4;
        panel_center.add(coin_label, c);
        
        progress_label = new JLabel();
        progress_label.setHorizontalAlignment(SwingConstants.CENTER); 
        progress_label.setText("");
        
        c.gridy = 5;
        panel_center.add(progress, c);
        c.gridy = 6;
        panel_center.add(progress_label, c);
        c.gridy = 7;
        //send = new JButton("Send");
        //send.setEnabled(false);
        //panel_center.add(send, c);
        
        panel_south = new JPanel(new GridLayout(1,2));
        chooseFileFrame = new JFrame();
        fileChooser = new JFileChooser();
        chooseFileFrame.add(fileChooser);
        chooseFileFrame.setSize(600, 600);
        chooseFileFrame.setLocationRelativeTo(null);

        record = new JButton();
        open_file = new JButton();

        record_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/Img/record.png");
        record_stop_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/Img/record_stop.png");
        ImageIcon open_folder_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/Img/open_folder.png");
        
        record.setIcon(record_icon);
        open_file.setIcon(open_folder_icon);
        
        panel_south.add(record);
        panel_south.add(open_file);
        
        record.addActionListener(this);
        open_file.addActionListener(this);
        
        add(mbar, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
        add(panel_south, BorderLayout.SOUTH);

        //add(panel_center, BorderLayout.SOUTH);
        //add(panel_south, BorderLayout.CENTER);

        //Change the font of all components in the panel_center
        changeFont(panel_center, new Font("Verdana", 10, 10));
	}

    private static void changeFont(Component comp, Font font){
        int size = comp.getFont().getSize();
        int style = comp.getFont().getStyle();
        comp.setFont(new Font(font.getName(), style, size));

        if ( comp instanceof Container ){
            for (Component child : ((Container)comp).getComponents()) {
                changeFont(child, font);
            }
        }
    }


    private void fillTablewithDummyData() {
		String[] columnNames = {"Date",
                "Time",
                "Success",
                "Artist",
                "Song Name"};
		
		
		long millis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        Date resultdate = new Date(millis);
		
		Object[][] data = {
			    {sdf.format(resultdate), sdf2.format(resultdate), "yes", "Justin Bieber", "Justin Bieber Song"},
			    {sdf.format(resultdate), sdf2.format(resultdate), "yes", "Madonna", "Hung Up"},
			    {sdf.format(resultdate), sdf2.format(resultdate), "no", "-", "-"},
			    {sdf.format(resultdate), sdf2.format(resultdate), "no", "-", "-"},
			    {sdf.format(resultdate), sdf2.format(resultdate), "no", "-", "-"}
			};
		
		table = new JTable(data, columnNames);
	}

	private void start_stop_recording() {
		// Start the recording
		// TODO
		if (record.getIcon().equals(record_icon)) {
			progress_label.setText("Recording started.");
			record.setIcon(record_stop_icon);	
			//send.setEnabled(false);

            controller.startRecordingFromMic();
		}
		
		// Stop the recording
		// TODO
		else {
			// TODO - check if correct mp3, wav file??
			progress_label.setText("Recording finished. Do you want to send it to the MP3 Recognition Service?");
			record.setIcon(record_icon);		
			//send.setEnabled(true);

            controller.stopRecordingFromMic();
		}
	}
	

	public JFrame showInfos() {     
        infoFrame = new JFrame("About");
        JPanel infopanel = new JPanel();
		JPanel panel_south = new JPanel();
		JPanel panel_center = new JPanel();
		JPanel panel_center1 = new JPanel();
		JPanel panel_north = new JPanel();
		JTextPane text = new JTextPane();
		
		panel_center.setOpaque(false);
		panel_center.setLayout(new GridLayout(2,1));
		
		infoFrame.getContentPane().add(infopanel);
		infoFrame.getContentPane().setLayout(new BorderLayout());
		infoFrame.setBounds(400, 200, 300, 250);
		infoFrame.setResizable(false);
		aboutFrameOkBtn = new JButton("Ok");
		aboutFrameOkBtn.addActionListener(this);
		        		
		text.setText("Rumpold Gernot (0728159)\n"
        		+ "Binder Johannes (0727990)\n"
        		+ "Weilharter Manuela (0826002)\n"
        		+ "Wolf Markus (0825595)\n"
        		+ "El-Isa Kamil (0726881)");
		
		text.setEditable(false);
		panel_center1.add(text);
		
		text.setOpaque(false);
		text.setBackground(new Color(0,0,0,0));
		
		panel_center.add(panel_center1);
		
		panel_south.add(aboutFrameOkBtn);
		infoFrame.getContentPane().add(panel_north, BorderLayout.NORTH);
		infoFrame.getContentPane().add(panel_south, BorderLayout.SOUTH);
		infoFrame.getContentPane().add(panel_center, BorderLayout.CENTER);
		
		infoFrame.setVisible(true);
		
		return infoFrame;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitM){
			System.exit(0);
        }
		
		else if (e.getSource() == faq) {
			//TODO - remove or implement
		}
		
		else if (e.getSource() == about) {
			showInfos();
		}
		
		else if (e.getSource() == aboutFrameOkBtn) {
			infoFrame.setVisible(false);
		}
		
		else if (e.getSource() == logoutM) {
			dispose();
			//new LoginDialog(this);
		}
		
		else if (e.getSource() == record) {
			start_stop_recording();
		}
		
		else if (e.getSource() == open_file) {
			int returnVal = fileChooser.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            progress_label.setText("Opened: " + file.getName() + ". Do you want to send it to the MP3 Recognition Service?");    
	        }

	        //send.setEnabled(true);
		}
		
	}

    public static void setUIFont(FontUIResource f){
        Enumeration keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), orig.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

}
