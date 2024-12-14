package database;

import java.sql.*;

public class Connect {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "";
	private final static String DATABASE = "StellarFest";
	private final static String HOST = "localhost:3306";
	private final static String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	public static Connect getInstance() {
		if(connect == null) return new Connect();
		else return connect;
	}
	
	public static String getUSERNAME() {
		return USERNAME;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static String getCONNECTION() {
		return CONNECTION;
	}

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int execute(String query) {
		try {
			int result = st.executeUpdate(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
