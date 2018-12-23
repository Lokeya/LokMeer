package com.engine.foodie;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.utils.foodie.*;

public class KitchenManagement {
	
	public JSONObject getChefDishInstances(Map func_params)
	{
		int userId = 0;
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("user_id"))
            {
            	userId = Integer.parseInt((String)pair.getValue());
            }
            
            System.out.println("Kitchen management User ID"+ userId);
        }
			
		JSONObject jsonResponse = new JSONObject();
		
		try {
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
			String query = "SELECT" +
					" u.user_nm user_nm,"	+
					" d.dish_nm dish_nm,"	+
					" di.dish_instance_status dish_instance_status,"	+
					" di.menu_dt menu_dt,"	+
					" di.price_amt price_amt,"	+
					" d.dish_img_url dish_img_url,"	+
					" d.dish_desc dish_desc,"	+
					" di.pickup_start_tm pickup_start_tm,"	+
					" di.pickup_end_tm pickup_end_tm,"	+
					" d.qty qty,"	+
					" di.order_cutoff_Datetime order_cutoff_datetime,"	+
					" di.unit_cutoff_cnt unit_cutoff_cnt,"	+
					" d.diet_type_id diet_type_id,"	+
					" di.packing_charges_flg packing_charges_flg,"	+
					" di.packing_charges packing_charges"	+
					" FROM"	+
				    " FoodieDB.tb_dish d"	+
				    " JOIN FoodieDB.tb_dish_instance di"	+
					" ON di.dish_id = d.dish_id"	+
				    " JOIN FoodieDB.tb_user u"	+
					" ON u.user_id = d.user_id"	+
				    " WHERE"	+
					" d.user_id= " + userId +
				    " ORDER BY di.menu_dt desc, di.dish_instance_status";
			
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("userNm", resultSet.getInt("user_nm"));
				jsonObj.put("dishNm", resultSet.getInt("dish_nm"));
				jsonObj.put("dishInstanceStatus", resultSet.getInt("dish_instance_status"));
				jsonObj.put("menuDt", resultSet.getDate("menu_dt"));
				jsonObj.put("priceAmt", resultSet.getFloat("price_amt"));
				jsonObj.put("dishImgUrl", resultSet.getString("dish_img_url"));
				jsonObj.put("dishDesc", resultSet.getString("dish_desc"));
				jsonObj.put("pickupStartTm", resultSet.getTime("pickup_start_tm"));
				jsonObj.put("pickupEndTm", resultSet.getTime("pickup_end_tm"));
				jsonObj.put("qty", resultSet.getInt("qty"));				
				jsonObj.put("orderDtTm", resultSet.getTime("order_cutoff_Datetime"));
				jsonObj.put("unitCutoffCnt", resultSet.getInt("unit_cutoff_cnt"));
				jsonObj.put("dietTypeId", resultSet.getInt("diet_type_id"));
				jsonObj.put("packingChargesFlg", resultSet.getInt("packing_charges_flg"));
				jsonObj.put("packingCharges", resultSet.getInt("packing_charges"));
				
				jsonArr.add(jsonObj);
			}
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Chef Dish Instance Details", jsonArr);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		//return response
		return jsonResponse;
	}
	
	
	public JSONObject getDishNamesForChef(Map func_params)
	{
		int userId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("user_id"))
            {
            	userId = Integer.parseInt((String)pair.getValue());
            }
            System.out.println("Kitchen management User ID"+ userId);

        }    
        
        JSONObject jsonResponse = new JSONObject();
		
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
	    
			String query = "SELECT dish_nm dish_nm "
	        		+ "FROM FoodieDB.tb_dish "  
	        		+ "WHERE user_id = " + userId; 

			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("dishNm", resultSet.getString(1));
				jsonArr.add(jsonObj);
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Dish Names", jsonArr);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}

	
	public JSONObject getDefaultDishDetails(Map func_params)
	{
		int dishId = 0;
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("dish_id"))
            {
            	dishId = Integer.parseInt((String)pair.getValue());
            }
            
            System.out.println("Kitchen management Dish ID"+ dishId);
        }
		JSONObject jsonResponse = new JSONObject();
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
			String query = "SELECT  " + 
					"		d.dish_id dish_id,  " + 
					"    	di.dish_instance_id dish_instance_id,  " + 
					"		d.dish_desc dish_desc,  " + 
					"    	d.qty qty,  " + 
					"    	d.diet_type_id diet_type_id,  " + 
					"    	di.price_amt price_amt,  " + 
					"    	di.packing_charges_flg packing_charges_flg,  " + 
					"    	di.packing_charges packing_charges  " + 
					"FROM  " + 
					"	(SELECT  " + 
					"		dish_id,  " + 
					"		MAX(dish_instance_id) max_di_id  " + 
					"	FROM  " + 
					"		FoodieDB.tb_dish_instance  " + 
					"	WHERE  " + 
					"		dish_id =   " + dishId + 
					"	GROUP BY dish_id  " + 
					"    )maxdi  " + 
					"    JOIN FoodieDB.tb_dish_instance di  " + 
					"		ON maxdi.max_di_id = di.dish_instance_id  " + 
					"	JOIN FoodieDB.tb_dish d  " + 
					"		ON d.dish_id = di.dish_id";
	        		
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONObject jsonObj = new JSONObject();
			while (resultSet.next())
			{				
				jsonObj.put("dishId", resultSet.getInt("dish_id"));
				jsonObj.put("dishInstanceId", resultSet.getInt("dish_instance_id"));
				jsonObj.put("dishDesc", resultSet.getString("dish_desc"));
				jsonObj.put("qty", resultSet.getInt("qty"));
				jsonObj.put("dietTypeId", resultSet.getInt("diet_type_id"));
				jsonObj.put("priceAmt", resultSet.getFloat("price_amt"));
				jsonObj.put("packingChargesFlg", resultSet.getInt("packing_charges_flg"));
				jsonObj.put("packingCharges", resultSet.getInt("packing_charges"));
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonObj.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Dish Instance Details", jsonObj);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		//Return response
		return jsonResponse;
	}
	

	public JSONObject getDishInstanceInfo(Map func_params)
	{
		int dishInstanceId = 0;
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("dish_instance_id"))
            {
            	dishInstanceId = Integer.parseInt((String)pair.getValue());
            }
            
            System.out.println("Kitchen management Dish Instance ID"+ dishInstanceId);
        }
		JSONObject jsonResponse = new JSONObject();
		try
		{
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			
			//Create DB query
			Statement stmt = conn.createStatement();
			String query = "SELECT" + 
					"	d.dish_id dish_id," + 
					"	di.dish_instance_id dish_instance_id," + 
					"	d.dish_nm dish_nm," + 
					"	di.dish_instance_status dish_instance_status," + 
					"	di.menu_dt menu_dt," + 
					"	di.price_amt price_amt," + 
					"	d.dish_img_url dish_img_url," + 
					"	d.dish_desc dish_desc," + 
					"	di.pickup_start_tm pickup_start_tm," + 
					"	di.pickup_end_tm pickup_end_tm," + 
					"	d.qty qty," + 
					"	di.order_cutoff_Datetime order_cutoff_datetime," + 
					"	di.unit_cutoff_cnt unit_cutoff_cnt," + 
					"	d.diet_type_id diet_type_id," + 
					"	di.packing_charges_flg packing_charges_flg," + 
					"	di.packing_charges packing_charges" + 
					"	FROM" + 
					"   FoodieDB.tb_dish d" + 
					"   JOIN FoodieDB.tb_dish_instance di" + 
					"	ON di.dish_id = d.dish_id" + 
					"   WHERE" + 
					"	di.dish_instance_id= " + dishInstanceId;
					
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONObject jsonObj = new JSONObject();
			while (resultSet.next())
			{				
				jsonObj.put("dishId", resultSet.getInt("dish_id"));
				jsonObj.put("dishInstanceId", resultSet.getInt("dish_instance_id"));
				jsonObj.put("dishNm", resultSet.getString("dish_nm"));
				jsonObj.put("dishInstanceStatus", resultSet.getInt("dish_instance_status"));				
				jsonObj.put("menuDt", resultSet.getDate("menu_dt"));				
				jsonObj.put("priceAmt", resultSet.getFloat("price_amt"));
				jsonObj.put("dishImgUrl", resultSet.getString("dish_img_url"));
				jsonObj.put("dishDesc", resultSet.getString("dish_desc"));
				jsonObj.put("pickupStartTm", resultSet.getString("pickup_start_tm"));
				jsonObj.put("pickupEndTm", resultSet.getString("pickup_end_tm"));
				jsonObj.put("qty", resultSet.getInt("qty"));
				jsonObj.put("orderCutoffDatetime", resultSet.getString("order_cutoff_Datetime"));
				jsonObj.put("unitCutoffCnt", resultSet.getInt("unit_cutoff_cnt"));
				jsonObj.put("dietTypeId", resultSet.getInt("diet_type_id"));
				jsonObj.put("packingChargesFlg", resultSet.getInt("packing_charges_flg"));
				jsonObj.put("packingCharges", resultSet.getInt("packing_charges"));
			}			
			
			System.out.println("Body JSON Output");
			System.out.println(jsonObj.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Dish Instance Details", jsonObj);
			
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
