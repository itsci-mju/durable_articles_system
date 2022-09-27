package ac.th.itsci.durable.util;
import java.sql.*;


public class ConnectionDB {
	
	Connection conn;
	String url = "jdbc:mysql://localhost:3306/durablewebservice?characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
	String uname = "root";
	String pwd = "123456";

	public Connection getConnection() {
		conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,uname,pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String args[]) {
		ConnectionDB condb = new ConnectionDB();
		System.out.println("***** database check " + condb.getConnection());
	}

}