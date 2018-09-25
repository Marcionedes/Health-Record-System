package healthRecordSystem;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	protected static final Component Frame = null;
	JTextField username = null;
	JTextField password = null;
	JComboBox petList = null;

	public Register() {
		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(3,1));
		JLabel un = new JLabel("username:");
        this.add(un);
		username = new JTextField(20);
		this.add(username);
		JLabel pw = new JLabel("password:");
		this.add(pw);
		password = new JTextField(20);
		this.add(password);
		String[] petStrings = { "Billing", "Doctor","Recp"};
		petList = new JComboBox(petStrings);
		this.add(petList);
		JButton register = new JButton("Register");
		register.addActionListener(this);
		register.setActionCommand("Register");
		this.add(register);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(Frame, 
                    "Are you sure to close this window?", "Really Closing?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
		validate();
		repaint();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new Register();
	}
	public void registerNewUser() {
	    String un = username.getText();
	    String pw = password.getText();
	    String accType = (String)petList.getSelectedItem();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();	
		} catch(Exception e ){}
			Connection conn = null;
	    	Statement stmt = null;
	    	ResultSet rs = null;
	    	try {
	    	    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");                          
	    	    // Do something with the Connection
	    	    stmt = conn.createStatement();
	    	    if (stmt.execute("INSERT INTO `health_recordsystem`.`systemusers` (`username`, `password`, `type`) VALUES ('"+un+"', '"+pw+"', '"+accType+"');")) {
	    	        rs = stmt.getResultSet();
	    	    }  
	    	} catch(Exception e){ System.out.println(e);}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		registerNewUser();
	}
}
