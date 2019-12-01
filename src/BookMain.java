import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn =null;
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "BookManage","970882562");
		if(conn != null)
			System.out.println("Connected");
		else
			System.out.println("NotConnected");
		
		conn.close();

	}

}
