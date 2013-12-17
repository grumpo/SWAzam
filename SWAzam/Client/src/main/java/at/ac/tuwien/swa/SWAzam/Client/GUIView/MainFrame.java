package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

/**
 * The client GUI
 * Created by Karatekiwi on 12/17/13.
 */
public class MainFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(MainFrame.class.getName());
		
	private JMenuBar mbar;
	private User user;
	
	private JMenuItem exitM;
	private JMenuItem logoutM;
	private JMenuItem faq;
	private JMenuItem about;
	
	private JFrame infoFrame;
	private JButton aboutFrameOkBtn;
	
	private JPanel panel_south;
	
	private JTable table;
	
	private JButton record, open_file;
	private ImageIcon record_icon, record_stop_icon;

    public MainFrame(User user) {
    	this.user = user;
    	log.info("Logged in with username: " + this.user.getUsername() + " and password: " + this.user.getPassword());
    	initComponents();	
    }

	private void initComponents() {
	    setTitle("SWAzam");
	    setSize(600, 600);
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

        JPanel panel_center = new JPanel(new BorderLayout());
        
        JTextPane overview = new JTextPane();
        Font f = new Font(Font.SERIF, 0, 25);
        overview.setFont(f);
        
        overview.setText("Request History");
        overview.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        StyledDocument doc = overview.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        panel_center.add(overview, BorderLayout.PAGE_START);
        fillTablewithDummyData();
        panel_center.add(new JScrollPane(table));
        
        panel_south = new JPanel(new GridLayout(1,2));
        
        record = new JButton();
        open_file = new JButton();
        
        record_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/icons/record.png");
        record_stop_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/icons/record_stop.png");
        ImageIcon open_folder_icon = new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/icons/open_folder.png");
        
        record.setIcon(record_icon);
        open_file.setIcon(open_folder_icon);
        
        panel_south.add(record);
        panel_south.add(open_file);
        
        record.addActionListener(this);
        
        add(mbar, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
        add(panel_south, BorderLayout.SOUTH);
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
			log.info("Recording started.");
			record.setIcon(record_stop_icon);		
		}
		
		// Stop the recording
		// TODO
		else {
			log.info("Recording stopped.");
			record.setIcon(record_icon);		
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
			new LoginFrame();
		}
		
		else if (e.getSource() == record) {
			start_stop_recording();
		}
		
	}

}
