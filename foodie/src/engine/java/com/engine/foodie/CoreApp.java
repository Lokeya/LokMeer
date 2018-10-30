package com.engine.foodie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONObject;

import com.utils.foodie.DbHandler;
import com.utils.foodie.JSONHandler;

public class CoreApp {
	
	public JSONObject getDietTypes()
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
			
	        String query = "SELECT diet_type_id diet_id, "
	        		+ "diet_type_desc diet_desc "  
	        		+ "FROM "
	        		+ "FoodieDB.tb_diet_type "; 
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONObject jsonObj = new JSONObject();
			while (resultSet.next())
			{
				jsonObj.put("dietTypeId", resultSet.getInt(1));
				jsonObj.put("dietTypeDesc", resultSet.getString(2));
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonObj.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Diet Types", jsonObj);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}

}
