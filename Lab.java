import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Lab extends Frame implements ActionListener {
    TextArea ta;
    TextField searchKey;
    Choice choice = new Choice();
    Button b;

    Lab(String title) throws SQLException, ClassNotFoundException {
        super(title);
        connectDb();
        setVisible(true);
        setSize(500, 500);
        setLayout(null);
        closeWin();

        addComp(new Label("Select : "), 20, 50, 40, 25);
        addSelectionList();

        addComp(new Label("Enter : "), 20, 100, 40, 25);
        addComp(searchKey = new TextField(), 100, 100, 150, 25);

        addComp(b = new Button("OK"), 100, 150, 40, 25);
        b.addActionListener(this);
        addComp(ta = new TextArea(), 20, 200, 400, 200);

    }

    private void closeWin() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addSelectionList() {
        addComp(choice, 100, 50, 150, 25);
        choice.add("Telephone Number");
        choice.add("Name");
        choice.add("Address");
    }

    Connection conn = null;
    Statement stm = null;

    private void connectDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaLab", "root", "");
            conn.setAutoCommit(false);
            System.out.println("connected.....");
        } catch (SQLException | ClassNotFoundException e) {
            conn.rollback();
            e.printStackTrace();
        }
        // stm = conn.createStatement();

        // DataBAse MetaData
        // DatabaseMetaData dbm= conn.getMetaData();
        // System.out.println(dbm.getDatabaseProductName());
        // System.out.println(dbm.getConnection());
        // System.out.println(dbm.getMaxColumnsInIndex());
        // System.out.println(dbm.getDriverName());
        // MetaData
        // DatabaseMetaData databaseMetaData=conn.getMetaData();
        // System.out.println("----"+databaseMetaData.getConnection());
        // System.out.println("----"+databaseMetaData.getDatabaseProductName());
        // System.out.println("----"+databaseMetaData.getDriverName());
        //
        // ResultSet rs=stm.executeQuery("select * from Product");
        // rs.next();
        // ResultSetMetaData resultSetMetaData=rs.getMetaData();
        //
        // System.out.println("rs----"+resultSetMetaData.getColumnClassName(1));
        // System.out.println("rs----"+resultSetMetaData.getColumnType(1));
        // System.out.println("rs----"+resultSetMetaData.getColumnCount());
        // System.out.println("rs----"+resultSetMetaData.getScale(1));
    }

    private void addComp(Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        add(c);
    }

    public static void main(String[] args) {
        try {
            // new Lab("Telephone directory");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // System.out.println(ae.getActionCommand());
        ta.setText("");
        if (ae.getActionCommand().equals("OK")) {
            try {
                getData(searchKey.getText().trim());
                searchKey.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void getData(String s) throws SQLException {
        String tmp2 = "";
        if (choice.getSelectedItem().equals("Name"))
            tmp2 = "select * from telephoneDirectory where name='" + s + "'";
        else if (choice.getSelectedItem().equals("Address"))
            tmp2 = "select * from telephoneDirectory where address like '%" + s + "%'";
        else
            tmp2 = "select * from telephoneDirectory where TelephoneNumber='" + s + "'";
        ResultSet rs = stm.executeQuery(tmp2);
        String tmp = "telephoneNumber\t|\tName\t|\tAddress\n"
                + "------------------------------------------------------------------------------------\n";
        while (rs.next())
            tmp += (rs.getString("telephoneNumber") + "\t|\t" + rs.getString("Name")) + "\t|\t"
                    + rs.getString("Address") + " \n";
        ta.setText(tmp);
    }

}