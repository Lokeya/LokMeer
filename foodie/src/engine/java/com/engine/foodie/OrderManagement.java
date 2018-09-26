package com.engine.foodie;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dbutils.foodie.*;

public class OrderManagement {
	
	public JSONObject getDishInstanceCountforDate(String dt, int commId)
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	        String query = "SELECT COUNT(di.dish_instance_id) di_cnt FROM FoodieDB.tb_dish_instance di JOIN FoodieDB.tb_dish d ON di.dish_id = d.dish_id JOIN FoodieDB.tb_user u ON d.user_id = u.user_id WHERE u.community_id = " 
					+  commId + " AND di.menu_dt = CAST('" + dt + "' AS DATE) AND di.dish_instance_status IN (1,2)";
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONObject jsonObj = new JSONObject();
			while (resultSet.next())
			{
				jsonObj.put("dishinstCnt", resultSet.getInt(1));
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonObj.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Dish Instance Count", jsonObj);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}
	
	
	public JSONObject getActiveDates(int commId)
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	        String query = "SELECT DISTINCT di.menu_dt menu_dt FROM	FoodieDB.tb_dish_instance di JOIN FoodieDB.tb_dish d ON di.dish_id = d.dish_id "
	        		+ "JOIN FoodieDB.tb_user u ON d.user_id = u.user_id "
	        		+ "WHERE u.community_id = " + commId 
	        		+ " AND di.menu_dt >= "
	        		+ "CAST('2018-07-20' AS DATE) "
	        		//+ "CURRENT_DATE() "
	        		+ "AND di.dish_instance_status IN (1,2)";
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("menuDt", resultSet.getDate(1));
				jsonArr.add(jsonObj);
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Active Dates", jsonArr);
			
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
