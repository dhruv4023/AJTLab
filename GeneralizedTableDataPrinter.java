import java.sql.*;

public class GeneralizedTableDataPrinter {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaLab", "root", "");
            System.out.println("connected.....");
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("Select * from telephonedirectory");
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnCount());
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < cols; i++)
                    System.out.print(rs.getString(1+i)+"\t");
                System.out.println();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
