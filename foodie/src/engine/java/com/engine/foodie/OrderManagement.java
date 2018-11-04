package com.engine.foodie;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.utils.foodie.*;

public class OrderManagement {
	
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
	
	public JSONObject getHomeChefCountforDate(String dt, int commId)
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	        String query = "SELECT COUNT(DISTINCT u.user_id) hc_cnt "
	        		+ "FROM FoodieDB.tb_dish_instance di "  
	        		+ "JOIN FoodieDB.tb_dish d "
	        		+ "ON di.dish_id = d.dish_id "
	        		+ "JOIN FoodieDB.tb_user u "
	        		+ "ON d.user_id = u.user_id "
	        		+ "WHERE u.community_id = " + commId 
	        		+ " AND di.menu_dt = CAST('" + dt + "' AS DATE) "
	        		+ "AND di.dish_instance_status IN (1,2)"; 
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONObject jsonObj = new JSONObject();
			while (resultSet.next())
			{
				jsonObj.put("homeChefCnt", resultSet.getInt(1));
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonObj.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Home Chef Count", jsonObj);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}
	
	public JSONObject getDishInstancesForDate(String dt, int commId)
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	        String query = "SELECT" + 
	        		"	d.dish_id d_id," + 
	        		"    d.dish_nm dish_nm," + 
	        		"    d.dish_desc dish_desc," + 
	        		"    d.diet_type_id diet_id," + 
	        		"    d.qty qty," + 
	        		"    d.dish_img_url," + 
	        		"	di.dish_instance_id di_id," + 
	        		"    di.pickup_start_tm pickup_start," + 
	        		"    di.pickup_end_tm pickup_end," + 
	        		"    di.price_amt price_amt," + 
	        		"    di.dish_instance_status dish_status," + 
	        		"    u.user_nm hc_nm," + 
	        		"    u.phone_nbr hc_phnbr " + 
	        		"FROM" + 
	        		"	FoodieDB.tb_dish d" + 
	        		"    JOIN FoodieDB.tb_dish_instance di" + 
	        		"		ON d.dish_id = di.dish_id" + 
	        		"	JOIN FoodieDB.tb_user u" + 
	        		"		ON d.user_id = u.user_id " + 
	        		"WHERE" + 
	        		"	u.community_id = " + commId +
	        		"	AND di.menu_dt = CAST('" + dt + "' AS DATE)" + 
	        		"    AND di.dish_instance_status IN (1,2) " + 
	        		"ORDER BY di.pickup_start_tm, di.pickup_end_tm" ;
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("dishId", resultSet.getInt("d_id"));
				jsonObj.put("dishNm", resultSet.getString("dish_nm"));
				jsonObj.put("dishDesc", resultSet.getString("dish_desc"));
				jsonObj.put("dietTypeId", resultSet.getInt("diet_id"));
				jsonObj.put("qty", resultSet.getString("qty"));
				jsonObj.put("dishImgUrl", resultSet.getString("dish_img_url"));
				jsonObj.put("pickupStart", resultSet.getTime("pickup_start").toString());
				jsonObj.put("pickupEnd", resultSet.getTime("pickup_end").toString());
				jsonObj.put("priceAmt", resultSet.getFloat("price_amt"));
				jsonObj.put("dishStatus", resultSet.getInt("dish_status"));
				jsonObj.put("hcName", resultSet.getString("hc_nm"));
				jsonObj.put("hcPhone", resultSet.getString("hc_phnbr"));
				
				jsonArr.add(jsonObj);
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Dish instance detail", jsonArr);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}
	
	public JSONObject getHomeChefsForDate(String dt, int commId)
	{
		JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	        String query = "SELECT DISTINCT " +
	        		"u.user_id," + 
	        	    "u.user_nm, " +
	        	    "b.building_nm, " +
	        	    "f.flat_nm, " + 
	        	    "u.profile_img_url "+
	        	"FROM "+
	        		"FoodieDB.tb_dish d "+
	        	    "JOIN FoodieDB.tb_dish_instance di "+
	        			"ON d.dish_id = di.dish_id " +
	        		"JOIN FoodieDB.tb_user u "+ 
	        			"ON d.user_id = u.user_id "+
	        		"JOIN FoodieDB.tb_flat f "+
	        			"ON u.flat_id = f.flat_id "+
	        	        "AND u.building_id = f.building_id "+
	        		"JOIN FoodieDB.tb_building b "+
	        			"ON f.building_id = b.building_id "+
	        	"WHERE "+
	        		"u.community_id = "+ commId +
	        		" AND di.menu_dt = " + "CAST('" + dt + "' AS DATE) " +
	        	    "AND di.dish_instance_status IN (1,2)"; 
	        		
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("userId", resultSet.getInt("user_id"));
				jsonObj.put("userNm", resultSet.getString("user_nm"));
				jsonObj.put("bldgNm", resultSet.getString("building_nm"));
				jsonObj.put("flatNm", resultSet.getString("flat_nm"));
				jsonObj.put("profileImgUrl", resultSet.getString("profile_img_url"));
								
				jsonArr.add(jsonObj);
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("User detail", jsonArr);
			
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