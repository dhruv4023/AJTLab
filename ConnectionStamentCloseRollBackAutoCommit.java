import java.sql.*;

public class ConnectionStamentCloseRollBackAutoCommit {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stm=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaLab", "root", "");
            conn.setAutoCommit(false);
            System.out.println("connected.....");
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("Select * from telephonedirectory");
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnCount());
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < cols; i++)
                    System.out.print(rs.getString(1+i)+"\t");
                System.out.println();
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.close();
                conn.rollback();
                stm.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
}
