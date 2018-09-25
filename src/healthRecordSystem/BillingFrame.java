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
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BillingFrame extends JFrame implements ActionListener {
    protected static final Component Frame = null;
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    String id = "";
    JTextField fee = null;
    JTextField service = null;
    JTable table;
    Vector patLst = new Vector();
    JComboBox<ComboItem> pat;
    ResultSet patients = null;
	
    public BillingFrame() {
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
           JButton close = new JButton("Close");
           bottom.add(close);
           close.addActionListener(this);
           this.setLayout(new GridLayout(7, 1));
           setSize(1000, 500);
           setVisible(true);
           this.setLayout(new BorderLayout());
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (Exception e) {}
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
    	    Object[][] data = new Object[100][4];
            try {
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");
                // Do something with the Connection
                stmt = conn.createStatement();
                if (stmt.execute("select * from bill;")) {
                    rs = stmt.getResultSet();
                }
                // loop over results
                int rowCounter = 0;
                while (rs.next()) {
                    String id = rs.getString("id");
                    data[rowCounter][0] = id;
                    String pat_id = rs.getString("pat_id");
                    data[rowCounter][1] = pat_id;
                    String fee = rs.getString("fee");
                    data[rowCounter][2] = fee;
                    String service = rs.getString("service");
                    data[rowCounter][3] = service;
                    rowCounter++;
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            String[] columnNames = {"id",
                "pat_id",
                "fee",
                "service"};
            table = new JTable(data, columnNames);
            table.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = table.rowAtPoint(evt.getPoint());
                    for (int j = 0; j < 8; j++) {
                        id = ((String) table.getValueAt(row, 0));
                        pat.setSelectedItem(new ComboItem(table.getValueAt(row, 1).toString()));
                        fee.setText((String) table.getValueAt(row, 2));
                        service.setText((String) table.getValueAt(row, 3));
                    }
                }
            });
            JScrollPane jb = new JScrollPane(table);
            left.add(jb);
            JLabel f = new JLabel("Fee:");
            right.add(f);
            fee = new JTextField(10);
            right.add(fee);
            JLabel s = new JLabel("Service:");
            right.add(s);
            service = new JTextField(10);
        right.add(service);
            try {
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/health_recordsystem?user=root&password=");
                // Do something with the Connection
                stmt = conn.createStatement();
                if (stmt.execute("select * from `patients`;")) {
                    patients = stmt.getResultSet();
                }
                int rowCounter = 0;
                while (patients.next()) {
                    System.out.println(patients.getString("id") + patients.getString("firstname"));
                    ComboItem combo = new ComboItem(patients.getString("id"), patients.getString("firstname"));
                    patLst.add(combo);
                    rowCounter++;
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            pat = new JComboBox(patLst);
            pat.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index,
                            isSelected, cellHasFocus);
                    if (value != null) {
                        ComboItem ComboItem = (ComboItem) value;
                        setText(ComboItem.getLabel());
                    }
                    return this;
                }
            });
        right.add(pat);
        JButton save = new JButton("save");
        save.addActionListener(this);
        save.setActionCommand("Save");
        right.add(save);
            this.add(top, BorderLayout.NORTH);
            this.add(bottom, BorderLayout.SOUTH);
            this.add(left, BorderLayout.WEST);
            this.add(right, BorderLayout.EAST);
            validate();
            repaint();  
	}
    public void logout() {
        ///	this.dispose();
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
            if (e.getActionCommand().equals("Save")) {
                save();
            }
    }
    public void save() {
        try {
            System.out.println("Starting save ");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {}
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
                sql.append("INSERT INTO health_recordsystem.bill");
                sql.append(" (fee,service, pat_id)");
                sql.append(" VALUES (");
                sql.append("'").append(fee.getText()).append("', ");
                sql.append("'").append(service.getText()).append("', ");
                ComboItem item = (ComboItem) pat.getSelectedItem();
                sql.append(item.getValue());
                sql.append("  ) ");
            } else {
                sql.append("UPDATE health_recordsystem.bill");
                sql.append(" SET  ");
                sql.append("fee = '").append(fee.getText()).append("', ");
                sql.append("service = '").append(service.getText()).append("' ");
                ComboItem item = (ComboItem) pat.getSelectedItem();
                sql.append("pat_id = ").append(item.getValue());
                sql.append(" Where id = ").append(id);
            }
            System.out.println("SQL " + sql);
            if (stmt.execute(sql.toString())) {
                rs = stmt.getResultSet();
            }
            conn.close();
            this.setVisible(false);
            BillingFrame df = new BillingFrame();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
