/**
 * 
 */
package org.inakirj.ImageRulette.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author inaki
 *
 */
public class DBConnector {

    public DBConnector() {
	// createConnection();
    }

    public ResultSet runQuery(String query) {
	// TODO
	// Create the connection..
	// Run the query...
	// Return the resultset
	return null;
    }

    public void createConnection() {
	Connection c = null;
	try {
	    Class.forName("org.postgresql.Driver");
	    String dbHost = "localhost";
	    String dbPort = "5432";
	    String dbName = "postgres";
	    String dbUser = "inaki";
	    String dbPasswd = "";
	    c = DriverManager.getConnection("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName, dbUser,
		    dbPasswd);
	    // Select test
	    c.setAutoCommit(false);
	    System.out.println("Opened database successfully");
	    Statement stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM dice_rulette.pictures;");
	    while (rs.next()) {
		int id = rs.getInt("ID");
		String name = rs.getString("PICTURE_URL");
		System.out.println("ID = " + id);
		System.out.println("NAME = " + name);
		System.out.println();
	    }
	    rs.close();
	    stmt.close();
	    c.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Opened database successfully");
    }

    public void createProConnection() {
	Connection c = null;
	try {
	    Class.forName("org.postgresql.Driver");
	    String dbHost = "ec2-23-23-222-147.compute-1.amazonaws.com";
	    String dbPort = "5432";
	    String dbName = "d5m029g45cnv15";
	    String dbUser = "bwoorudwlpolnf";
	    String dbPasswd = "f3d9841ab08e2569d0fec1ba6ffc5a6df72b8e462359582e6eb96994630be5e1";
	    c = DriverManager
		    .getConnection("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName + "?user=" + dbUser
			    + "&password=" + dbPasswd + "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
	    c.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Opened database successfully");
    }

}
