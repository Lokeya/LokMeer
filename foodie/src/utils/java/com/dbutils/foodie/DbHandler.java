package com.dbutils.foodie;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHandler {

	public Connection getDBConnection()
	{
		Connection conn = null;
		 try {
	          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
	          String username = "Lokmeer";
	          String password = "Welcome123";

	          conn = DriverManager.getConnection(url, username, password);
	          
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
		 return conn;
	}
}
