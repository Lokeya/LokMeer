package com.engine.foodie;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	
	
	public void SaveDishInstance()
	{
		int dish_id;
		int user_id;
		int diet_type_id;
		int cuisine_id;
		String dish_nm;
		String dish_desc;
		int qty;
		//String dish_img_url;
		
		
		dish_id = 0;
		user_id = 1000003;
		diet_type_id = 1;
		cuisine_id = 0;
		dish_nm = "Aloo Paratha with Raitha";
		dish_desc = "2 Aloo Parathas with mixed veg raitha";
		qty = 1;
				
		
		try
		{
		//Get Connection to database
		DbHandler dbh = new DbHandler();
		Connection conn = dbh.getDBConnection();
		conn.setAutoCommit(false);
	
		
		if (dish_id == 0)
			{
			String sql = "INSERT INTO FoodieDB.tb_dish"
					+ "(user_id, diet_type_id, cuisine_id, dish_nm, dish_desc, qty, dish_img_url, "
					+ "create_ts, create_user, update_ts, update_user) VALUES "
					+ "(?,?,?,?,?,?,NULL,now(), current_user(), now(), current_user())";
			
			PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);		
			
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, diet_type_id);
			pstmt.setInt(3, cuisine_id);
			pstmt.setString(4, dish_nm);
			pstmt.setString(5, dish_desc);
			pstmt.setInt(6, qty);
						
			System.out.println("Dish insert query :" +sql);
			
			pstmt.execute();
			
			System.out.println("Inserted Dish");
						
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next())
				{
				dish_id = rs.getInt(1);
				}
			}
		else
			{
			String sql = "UPDATE FoodieDB.tb_dish SET " + 
					"diet_type_id = ?," + 
					"cuisine_id = ?," + 
					"dish_desc = ?," + 
					"qty = ?," + 
					//"dish_img_url = ?," + 
					"update_ts = now()," + 
					"update_user = current_user()" + 
					"WHERE dish_id = ?" ;
						
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			
			pstmt.setInt(1, diet_type_id);
			pstmt.setInt(2, cuisine_id);
			pstmt.setString(3, dish_desc);
			pstmt.setInt(4, qty);
			//pstmt.setString(5, dish_img_url);
			pstmt.setInt(6, dish_id);
						
			System.out.println("Dish update query :" +sql);
			
			pstmt.execute();
			
			System.out.println("Dish updated");
			}
		
		Date menu_dt;
		String pickup_start_tm_s;
		String pickup_end_tm_s;
		float price_amt;
		int delivery_flg;
		float delivery_charges;
		int packing_charges_flg;
		float packing_charges;
		Date order_cutoff_datetime;
		int unit_cutoff_cnt;
		String dish_instance_status;
		
		/////////////////////////
		//Set values for above variables
		//////////////////////////
		
		pickup_start_tm_s = "18:00:00";
		DateFormat df_s = new SimpleDateFormat("hh:mm:ss");
		java.util.Date pickup_start_date = df_s.parse(pickup_start_tm_s);
		Time pickup_start_tm = new Time(pickup_start_date.getTime());
		
		pickup_end_tm_s = "20:30:00";
		DateFormat df_e = new SimpleDateFormat("hh:mm:ss");
		java.util.Date pickup_end_date = df_e.parse(pickup_start_tm_s);
		Time pickup_end_tm = new Time(pickup_end_date.getTime());
		
		//Inserting dish instance record
		String di_sql = "	INSERT INTO FoodieDB.tb_dish_instance" + 
				"	(dish_id, menu_dt, pickup_start_tm, pickup_end_tm, price_amt," + 
				"	delivery_flg, delivery_charges, packing_charges_flg, packing_charges, order_cutoff_Datetime," + 
				"	unit_cutoff_cnt, dish_instance_status, create_ts, create_user, update_ts, update_user)" + 
				"	VALUES" + 
				"	(?,?,?,?,?,?,?,?,?,?,?,?, now(), current_user(), now(), current_user())" ;
					
		PreparedStatement di_pstmt = conn.prepareStatement(di_sql);	
		
		di_pstmt.setInt(1, dish_id);
		di_pstmt.setDate(2, menu_dt);
		di_pstmt.setTime(3, pickup_start_tm);
		di_pstmt.setTime(4, pickup_end_tm);
		di_pstmt.setFloat(5, price_amt);
		di_pstmt.setInt(6, delivery_flg);
		di_pstmt.setFloat(7, delivery_charges);
		di_pstmt.setInt(8, packing_charges_flg);
		di_pstmt.setFloat(9, packing_charges);
		di_pstmt.setDate(10, order_cutoff_datetime);
		di_pstmt.setInt(11, unit_cutoff_cnt);
		di_pstmt.setString(12, dish_instance_status);
					
		System.out.println("Dish instance insert query :" + di_sql);
		
		di_pstmt.execute();
		
		System.out.println("Dish instance updated");
		
		conn.commit();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
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
