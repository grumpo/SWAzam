package at.ac.tuwien.swa.SWAzam.Client.GUIView;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import at.ac.tuwien.swa.SWAzam.Client.Entities.User;




public class LoginFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton login, cancel;
	private JTextField uname;
	private JPasswordField pass;
	private JLabel uLabel, pLabel;
    
    public LoginFrame() {
    	initComponents();
    }
    	
    private void initComponents() {
    	setTitle("SWAzam Login");
    	setLayout(new BorderLayout());
        setLookAndFeel();
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        uLabel = new JLabel("Username");
        pLabel = new JLabel("Password");

        uname = new JTextField(20);
        pass = new JPasswordField(20);

        login = new JButton("Login");
        cancel = new JButton("Cancel"); 
        cancel.setPreferredSize(new Dimension(100, 25));
        login.setPreferredSize(new Dimension(100, 25));
        
        JPanel center = new JPanel(new BorderLayout());
        
        JPanel center_left = new JPanel(new GridLayout(3,1));
        JPanel center_middle = new JPanel(new GridLayout(3,1));
        
        center_left.add(uLabel);
        center_left.add(pLabel);
        center_left.add(new JLabel());
        center_left.setBorder(new EmptyBorder(10, 10, 10, 10));

        JCheckBox rememberPw = new JCheckBox("Remember Password");
        center_middle.add(uname);
        center_middle.add(pass);
        center_middle.add(rememberPw);
        center_middle.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        center.add(center_left, BorderLayout.LINE_START);
        center.add(center_middle, BorderLayout.CENTER);

        JPanel south = new JPanel(new GridBagLayout());
        south.add(cancel);
        south.add(login);
       
        //TODO - working on mac, linux?
        add(new JLabel(new ImageIcon("src/main/java/at/ac/tuwien/swa/SWAzam/Client/GUIView/img/login.png")), BorderLayout.PAGE_START);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.PAGE_END);
        
        uname.requestFocus();
        
        cancel.addActionListener(this);
        login.addActionListener(this);

        pack();
	}

   

	private void setLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, use default L&F
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel){
			System.exit(0);
        }
		
		else if (e.getSource() == login) {
			 String username = uname.getText();
			 String password = new String(pass.getPassword());

			 //TODO verify user + password
			 
			 User user = new User(username, password);
		     new MainFrame(user);
		     
		     setVisible(false);
		     
		}
	}

}
