package at.ac.tuwien.swa.SWAzam.Client.GUIView;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;


/**
 * The client GUI
 * Created by Karatekiwi on 12/17/13.
 */
public class LoginDialog extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(LoginDialog.class.getName());

	private JButton login, cancel;
	private JTextField uname;
	private JPasswordField pass;
	private JLabel uLabel, pLabel;
	private JCheckBox rememberPw;

    private Controller c;

    public LoginDialog(Controller c, JFrame owner){
        super(owner);

        this.c = c;
        initComponents();
    }
    	
    private void initComponents() {
        this.setContentPane(new ImagePanel(this.getClass().getResource("/Images/background.png").getFile()));
        this.setTitle("SWAzam Login");
    	this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModal(true);

        uLabel = new JLabel("Username");
        uLabel.setForeground(Color.lightGray);
        pLabel = new JLabel("Password");
        pLabel.setForeground(Color.lightGray);

        uname = new JTextField(20);
        uname.addKeyListener(this);
        uname.setForeground(Color.darkGray);
        pass = new JPasswordField(20);
        pass.addKeyListener(this);
        pass.setForeground(Color.darkGray);

        login = new JButton("Login");
        cancel = new JButton("Cancel"); 
        cancel.setPreferredSize(new Dimension(100, 25));
        login.setPreferredSize(new Dimension(100, 25));
        
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        
        JPanel center_left = new JPanel(new GridLayout(3,1));
        JPanel center_middle = new JPanel(new GridLayout(3,1));

        center_left.setOpaque(false);
        center_middle.setOpaque(false);

        center_left.add(uLabel);
        center_left.add(pLabel);
        center_left.add(new JLabel());
        center_left.setBorder(new EmptyBorder(10, 10, 10, 10));

        rememberPw = new JCheckBox("Remember Login");
        rememberPw.setForeground(Color.lightGray);
        center_middle.add(uname);
        center_middle.add(pass);
        center_middle.add(rememberPw);
        center_middle.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        center.add(center_left, BorderLayout.LINE_START);
        center.add(center_middle, BorderLayout.CENTER);

        JPanel south = new JPanel(new GridBagLayout());
        south.add(login);
        south.add(cancel);
        south.setOpaque(false);

        JLabel header = new JLabel("<html><body><strong>Login</strong></body></html>");
        header.setFont(new Font("Verdana", 0, 32));
        header.setVerticalAlignment(JLabel.CENTER);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.setPreferredSize(new Dimension(200, 40));
        header.setForeground(Color.lightGray);

        this.add(header, BorderLayout.PAGE_START);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.PAGE_END);
        
        uname.requestFocus();
        
        cancel.addActionListener(this);
        login.addActionListener(this);

        pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel){
            c.setUser(null, false);
            this.setVisible(false);
        }
		
		else if (e.getSource() == login) {
			 userLogin();
		}
	}
	
	private void userLogin() {
		String username = uname.getText();
		String password = new String(pass.getPassword());

		if (username.equals("") || password.equals(""))
			 JOptionPane.showMessageDialog(this, "Please provide username and password.", "Missing Information", JOptionPane.ERROR_MESSAGE);
		else {
            //TODO: Hash Password
			User user = new User(username, password);

            uname.requestFocus();
            uname.setText("");
            pass.setText("");

			if (rememberPw.isSelected())
			 	log.info("Remember PW is selected.");
			else
				log.info("Remember PW is not selected.");
			
		    c.setUser(user, rememberPw.isSelected());
		     
		    this.setVisible(false);
		 }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			userLogin();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

}
