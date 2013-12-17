package at.ac.tuwien.swa.SWAzam.Client.GUIView;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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
        setLayout(new GridLayout(3,2));
        setLookAndFeel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        uLabel = new JLabel("Username");
        pLabel = new JLabel("Password");

        uname=new JTextField(20);
        pass=new JPasswordField(20);

        login=new JButton("Login");
        cancel=new JButton("Cancel");

        add(uLabel);
        add(uname);

        add(pLabel);
        add(pass);

        add(cancel);
        add(login);
       
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
