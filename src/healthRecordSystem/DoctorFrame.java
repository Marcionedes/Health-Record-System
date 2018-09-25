package healthRecordSystem;
import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DoctorFrame extends JFrame implements ActionListener {
    protected static final Component Frame = null;
	String id;
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JLabel firstnamelabel = new javax.swing.JLabel("First name");
    JTextField firstname = new javax.swing.JTextField(45);
    JLabel lastnamelabel = new javax.swing.JLabel("Last name");
    JTextField lastname = new javax.swing.JTextField(45);
    JLabel doblabel = new javax.swing.JLabel("DOB");
    JTextField dob = new javax.swing.JTextField(45);
    JLabel addresslabel = new javax.swing.JLabel("Address");
    JTextField address = new javax.swing.JTextField(45);
    JLabel noteslabel = new javax.swing.JLabel("Notes");
    JTextField notes = new javax.swing.JTextField(45);
    JLabel medslabel = new javax.swing.JLabel("Meds");
    JTextField meds = new javax.swing.JTextField(45);
    JLabel callrecordslabel = new javax.swing.JLabel("Call Records");
    JTextField callrecords = new javax.swing.JTextField(45);
    JLabel savelabel = new javax.swing.JLabel("<SAVE>");
    JTable table;

	public DoctorFrame() {
        JButton close = new JButton("Close");
        bottom.add(close);
        close.addActionListener(this);
        JButton save = new JButton("Save");
        save.addActionListener(this);
        save.setActionCommand("Save");
        this.add(save);
        // closing window event 
      		this.addWindowListener(new java.awt.event.WindowAdapter() {
                  @Override
                  public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                      if (JOptionPane.showConfirmDialog(Frame, 
                        "Are you sure to close this window?", "Really Closing?", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ) {
                        System.exit(0);
                        }
                   }
            });
            setSize(1000, 600);
	        setVisible(true);
	 	    this.setLayout(new BorderLayout());
			try {
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch(Exception e ){}
			Connection conn = null;
	    	Statement stmt = null;
	    	ResultSet rs = null;
    	    Object[][] data = new Object[100][8];
	    	try {
	    	    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");
	    	    // Do something with the Connection
	    	    stmt = conn.createStatement();
	    	    if (stmt.execute("select * from `patients`;")) {
	    	        rs = stmt.getResultSet();
	    	    }
	    	    // loop over results            
	            int rowCounter = 0;
	    	    while(rs.next()){
	    	 	String id = rs.getString("id");
	    	   	data[rowCounter][0] = id;
	    	    String firstname = rs.getString("firstname");
	    	   	data[rowCounter][1] = firstname;
	    	  	String lastname = rs.getString("lastname");    	    
	    	  	data[rowCounter][2] = lastname;
	    	   	String dob = rs.getString("dob");
	    	   	data[rowCounter][3] = dob;	
	    	  	String address = rs.getString("address");
	    	   	data[rowCounter][4] = address;
	    	    String notes = rs.getString("notes");
	    	   	data[rowCounter][5] = notes;
	    	   	String meds = rs.getString("meds");
	    	   	data[rowCounter][6] = meds;
	    	  	String callrecords = rs.getString("callrecords");
	    	   	data[rowCounter][7] = callrecords;
	    	    rowCounter++;        
	            }    
	    	} catch (SQLException ex) {
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());
	    	}
	    	String[] columnNames = {"id",
	                "firstname",
	                "lastname",
	                "dob",
	                "address",
	                "notes",
	                "meds",
	                "callrecords"};
            table = new JTable(data, columnNames);
            table.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = table.rowAtPoint(evt.getPoint());
                    for (int j = 0; j < 8; j++) {
                        id = ((String) table.getValueAt(row, 0));
                        firstname.setText((String) table.getValueAt(row, 1));
                        lastname.setText((String) table.getValueAt(row, 2));
                        dob.setText((String) table.getValueAt(row, 3));
                        address.setText((String) table.getValueAt(row, 4));
                        notes.setText((String) table.getValueAt(row, 5));
                        meds.setText((String) table.getValueAt(row, 6));
                        callrecords.setText((String) table.getValueAt(row, 7));
                    }
                }
            });
	    	JScrollPane js=new JScrollPane(table);
	    	left.add(js);
	    	bottom.add(close);
	    	right.setLayout(new GridLayout(13, 0));
	    	right.add(firstnamelabel);
	        right.add(firstname);
	        right.add(lastnamelabel);
	        right.add(lastname);
	        right.add(doblabel);
	        right.add(dob);
	        right.add(addresslabel);
	        right.add(address);
	        right.add(noteslabel);
	        right.add(notes);
	        right.add(medslabel);
	        right.add(meds);
	        right.add(callrecordslabel);
	        right.add(callrecords);
	        right.add(savelabel);
	        right.add(save);

	        this.add(top, BorderLayout.NORTH);
	        this.add(bottom, BorderLayout.SOUTH);
	        this.add(left, BorderLayout.WEST);
	        this.add(right, BorderLayout.EAST);
	         
	        validate();
            repaint();
	}
	public void logout() {
		this.setVisible(false);
    	Login df = new Login();
	}
    public void actionPerformed(ActionEvent e) {
		// if you want to set a label for each of the buttons
		// and then redirect the user to a different part of the program
		// you can use the getActionCommand to check which button
		// has sent the request
		if(e.getActionCommand().equals("Close")){	
		logout();
        }
        if(e.getActionCommand().equals("Save")) {
            save();
        }
    }
    public void save() {
        try {
            System.out.println("Starting save ");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (Exception e) {
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");
            // Do something with the Connection
            stmt = conn.createStatement();
            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            StringBuilder sql = new StringBuilder();
            if (id.equals("")) {
                sql.append("INSERT INTO health_recordsystem.patients");
                sql.append(" (firstname,lastname,dob,address,notes,meds,callrecords)");
                sql.append(" VALUES (");
                sql.append("'").append(firstname.getText()).append("', ");
                sql.append("'").append(lastname.getText()).append("', ");
                sql.append("'").append(dob.getText()).append("', ");
                sql.append("'").append(address.getText()).append("', ");
                sql.append("'").append(notes.getText()).append("', ");
                sql.append("'").append(meds.getText()).append("', ");
                sql.append("'").append(callrecords.getText()).append("' ");
                sql.append("  ) ");
                } else {
                    sql.append("UPDATE health_recordsystem.patients");
                    sql.append(" SET  ");
                    sql.append("firstname = '").append(firstname.getText()).append("', ");
                    sql.append("lastname = '").append(lastname.getText()).append("', ");
                    sql.append("dob = '").append(dob.getText()).append("', ");
                    sql.append("address = '").append(address.getText()).append("', ");
                    sql.append("notes = '").append(notes.getText()).append("', ");
                    sql.append("meds ='").append(meds.getText()).append("', ");
                    sql.append("callrecords ='").append(callrecords.getText()).append("' ");
                    sql.append(" Where id = ").append(id);
                }
                System.out.println("SQL " + sql);
                if (stmt.execute(sql.toString())) {
                    rs = stmt.getResultSet();
                }
                conn.close();
                this.setVisible(false);
                DoctorFrame df = new DoctorFrame();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}

