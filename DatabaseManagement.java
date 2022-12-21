import java.sql.*;

public class DatabaseManagement {
    Connection conn;
    Statement stm;

    DatabaseManagement() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaLab", "root", "");
        System.out.println("connected.....");
        stm = conn.createStatement();
        insertIntoTelephoneDirectoryStatement();
        deleteFromTelephoneDirectoryStatement();
    }

    String getData(String s, String selectedColumn) throws SQLException {
        String tmp2;
        tmp2 = "select * from telephoneDirectory where " + selectedColumn + " Like '%" + s + "%'";
        ResultSet rs = stm.executeQuery(tmp2);
        StringBuilder tmp = new StringBuilder("telephoneNumber\t|\tName\t|\tAddress\n");
        tmp.append("------------------------------------------------------------------------------------\n");
        while (rs.next()){
            tmp.append(rs.getString("telephoneNumber")).append("\t|\t").append(rs.getString("Name")).append("\t|\t")
                    .append(rs.getString("Address")).append(" \n");}
        return (tmp.toString());
    }

    PreparedStatement psmtAdd, psmtDel;

    public void insertIntoTelephoneDirectoryStatement() throws SQLException {
        String query = "INSERT INTO telephoneDirectory (`TelephoneNumber`,`name`,`address`) value(?,?,?)";
        psmtAdd = conn.prepareStatement(query);
    }

    public void insertData(long number, String name, String add) throws SQLException {
        psmtAdd.setLong(1, number);
        psmtAdd.setString(2, name);
        psmtAdd.setString(3, add);
        psmtAdd.executeUpdate();
    }

    private void deleteFromTelephoneDirectoryStatement() throws SQLException {
        String query = "delete from telephoneDirectory where `TelephoneNumber`  like ?";
        psmtDel = conn.prepareStatement(query);
    }

    public int deleteData(long number) throws SQLException {
        psmtDel.setLong(1, number);
        return psmtDel.executeUpdate();
    }
}
