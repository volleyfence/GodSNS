package godsns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	public static Connection getConnection() throws SQLException{
		String db_url = "jdbc:mysql://localhost:3306/godsns?useSSL=false&characterEncoding=UTF-8&serverTimezone=JST";
	    String db_userid = "volleyfence";
	    String db_password = "Zaq1xsw2";

	    Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(db_url, db_userid, db_password);
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
		return conn;
	}
}


