package healthRecordSystem;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame implements ActionListener {
	protected static final Component Frame = null;
	JTextField username = null;
	JTextField password = null;
	
	public Login() { // default constructor 
		setSize(300,300);
		setVisible(true);
		this.setLayout(new GridLayout(3,1));
		JLabel un = new JLabel("username:");
        this.add(un);
		username = new JTextField(10);
		this.add(username);
		JLabel pw = new JLabel ("password:");
		this.add(pw);
		password = new JTextField(10);
		this.add(password);
		JButton login = new JButton("Login");
		login.addActionListener(this);
		login.setActionCommand("Login");
		this.add(login);
		JButton register = new JButton("Register");
		register.addActionListener(this);
        this.add(register);
        // closing window event 
		this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(Frame, 
                    "Are you sure to close this window?", "Really Closing?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ){
                    System.exit(0);
                }
                
            }
        });
		validate();
		repaint();
	}
	public static void main(String[] args) {
	    new Login();	
	}
	public void register() {   // method calling register frame 
		this.setVisible(false); 
    	Register rf = new Register();	
	}
	public void loginWithDatabase() {  // method calling connection with database
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		} catch (Exception e ){}
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
    	    conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");
    	    // Do something with the Connection
    	    stmt = conn.createStatement();
    	    // or alternatively, if you don't know ahead of time that
    	    // the query will be a SELECT...
    	    String un = username.getText();
    	    String pw = password.getText();
    	    if (stmt.execute("select * from `systemusers` where username = '"+un+"' and password = '"+pw+"'")) {
    	        rs = stmt.getResultSet();
    	    }
    	    // loop over results
    	    while(rs.next()){
    	    	String id = rs.getString("id");
    	        String sid = rs.getString("username");
    	        String type = rs.getString("type");
    	
    	        if(type.equals("Billing")) {
    	        	this.setVisible(false);
    	        	BillingFrame bf = new BillingFrame();
    	        }
    	        else if(type.equals("Doctor")) {
    	        	this.setVisible(false);
    	        	DoctorFrame df = new DoctorFrame();
    	        }
                else if(type.equals("Recp")) {
    	        	this.setVisible(false);
    	        	RecpFrame rf = new RecpFrame();
    	        }   
    	    } 
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
	}
	@Override
	public void actionPerformed(ActionEvent e) { // calling each event based on the command 
		if(e.getActionCommand().equals("Login")) { 
			loginWithDatabase();
		}
		else if(e.getActionCommand().equals("Register")) {
			 register();	 
		}
	}		
}


