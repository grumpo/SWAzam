package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;

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
        
        add(mbar, BorderLayout.NORTH);
               
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
		// panel_north.add(new JLabel(new ImageIcon("src/main/java/gui/locomotive.jpg")));
		        		
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
		
	}

}
