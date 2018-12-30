package de.ninju.streambot.main;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class MySQL {
	
    public static String host = "127.0.0.1";
	public static String port = "3306";
	public static String database = "ts3bot";
	public static String username = "root";
	public static String password = "";
	public static Connection con;

	public static void connect() {
		if (!isConnected()) {
			try
			{
				con = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				System.out.println("[MySQL] [Sync] Verbindung aufgebaut!");
			}
			catch (SQLException e)
			{
				System.out.println("[MySQL] Connection failed");
				e.printStackTrace();
			}
		}
	}
	public static void disconnect()
	{
		if (isConnected()) {
			try
			{
				con.close();
				System.out.println("[MySQL] [Sync] Verbindung geschlossen");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static boolean isConnected()
	{
		return con != null;
	}

	public static Connection getConnection()
	{
		return con;
	}
	public static void update(String qry) {
		if (isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static ResultSet getResult(String qry) {
		if (isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static Object get(String whereresult, String where, String select, String database) {

		ResultSet rs = getResult("SELECT " + select + " FROM " + database + " WHERE " + where + "='" + whereresult + "'");
		try {
			if(rs.next()) {
				Object v = rs.getObject(select);
				return v;
			}
		} catch (SQLException e) {
			return "ERROR";
		}

		return "ERROR";
	}

}
