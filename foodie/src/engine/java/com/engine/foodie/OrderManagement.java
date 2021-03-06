package com.engine.foodie;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.utils.foodie.*;

public class OrderManagement {
	
	public JSONObject getActiveDates(Map func_params)
	{
		
		int commId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("comm_id"))
            {
            	commId = Integer.parseInt((String)pair.getValue());
            }
            
            System.out.println("Order management Community ID"+ commId);
        }
		
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

	public JSONObject getDishInstanceCountforDate(Map func_params)
	{
		String dt = null;
		int commId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("comm_id"))
            {
            	commId = Integer.parseInt((String)pair.getValue());
            }
            //	To check if "menu_dt" is correct
            if(((String)pair.getKey()).equalsIgnoreCase("menu_dt"))
            {
            	dt = (String)pair.getValue();
            }
            System.out.println("Order management Community ID"+ commId);
            System.out.println("Order management Menu date"+ dt);
        }
	
		
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
	
	public JSONObject getHomeChefCountforDate(Map func_params)
	{
		
		String dt = null;
		int commId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("comm_id"))
            {
            	commId = Integer.parseInt((String)pair.getValue());
            }
            //	To check if "menu_dt" is correct
            if(((String)pair.getKey()).equalsIgnoreCase("menu_dt"))
            {
            	dt = (String)pair.getValue();
            }
            System.out.println("Order management Community ID"+ commId);
            System.out.println("Order management Menu date"+ dt);
        }
        
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
	
	public JSONObject getDishInstancesForDate(Map func_params)
	{
		String dt = null;
		int commId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("comm_id"))
            {
            	commId = Integer.parseInt((String)pair.getValue());
            }
            //	To check if "menu_dt" is correct
            if(((String)pair.getKey()).equalsIgnoreCase("menu_dt"))
            {
            	dt = (String)pair.getValue();
            }
            System.out.println("Order management Community ID"+ commId);
            System.out.println("Order management Menu date"+ dt);
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
	
	public JSONObject getHomeChefsForDate(Map func_params)
	{
		String dt = null;
		int commId = 0; 
		
		// iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue());
            if(((String)pair.getKey()).equalsIgnoreCase("comm_id"))
            {
            	commId = Integer.parseInt((String)pair.getValue());
            }
            //	To check if "menu_dt" is correct
            if(((String)pair.getKey()).equalsIgnoreCase("menu_dt"))
            {
            	dt = (String)pair.getValue();
            }
            System.out.println("Order management Community ID"+ commId);
            System.out.println("Order management Menu date"+ dt);
        }

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
	
	public JSONObject getOpenOrders(Map func_params)
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
            
            System.out.println("Order management User ID"+ userId);
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
				    "	o.order_id order_id," +
				    "	o.order_dt_time order_dt_time," +
				    "	o.order_sts order_status," +
				    "	u.user_nm user_nm," +
				    "	d.user_id user_id," +
				    "	b.building_nm building_nm," +
				    "	f.flat_nm flat_nm," +
				    "	u.phone_nbr phone_nbr," +
				    "	di.menu_dt menu_dt," +
				    "	di.pickup_start_tm pickup_start_tm," +
				    "	di.pickup_end_tm pickup_end_tm," +
				    "	d.dish_nm dish_nm," +
				    "	od.unit_cnt unit_cnt," +
				    "	od.order_di_amt order_di_amt," +
				    "	o.order_amt order_amt," +
				    "	di.packing_charges packing_charges" +
				    "	FROM" +
				    "	FoodieDB.tb_order o" +
				    "	JOIN FoodieDB.tb_order_details od" +
				    "	ON o.order_id = od.order_id" +
				    "	JOIN FoodieDB.tb_dish_instance di" +
				    "	ON od.dish_instance_id = di.dish_instance_id" + 
				    "	JOIN FoodieDB.tb_dish d"  +
				    "	ON di.dish_id = d.dish_id"	+
				    "	JOIN FoodieDB.tb_user u"	+
				    "	ON u.user_id = d.user_id"	+
				    "	JOIN FoodieDB.tb_building b"	+
				    "	ON u.building_id = b.building_id"	+
				    "	JOIN FoodieDB.tb_flat f"	+
				    "	ON u.building_id = f.building_id"	+
				    "	AND u.flat_id = f.flat_id"	+
				    "	WHERE"	+
				    "	o.user_id = " + userId +
				    "	AND"	+
				    "	o.order_sts IN ('O', 'C')"	+
				    "	ORDER BY o.order_dt_time DESC";
	        		
			System.out.println(query);
			
			//Execute query
			ResultSet resultSet = stmt.executeQuery(query);
			
			//Create bodyJSON
			JSONArray jsonArr = new JSONArray();
			while (resultSet.next())
			{
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("orderId", resultSet.getInt("order_id"));
				jsonObj.put("orderDtTm", resultSet.getTime("order_dt_time"));
				jsonObj.put("orderStatus", resultSet.getString("order_status"));
				jsonObj.put("userNm", resultSet.getString("user_nm"));
				jsonObj.put("userId", resultSet.getInt("user_id"));
				jsonObj.put("buildingNm", resultSet.getString("building_nm"));
				jsonObj.put("flatNm", resultSet.getString("flat_nm"));
				jsonObj.put("phoneNbr", resultSet.getString("phone_nbr"));
				jsonObj.put("menuDt", resultSet.getDate("menu_dt"));
				jsonObj.put("pickupStartTm", resultSet.getTime("pickup_start_tm"));
				jsonObj.put("pickupEndTm", resultSet.getTime("pickup_end_tm"));
				jsonObj.put("dishNm", resultSet.getString("dish_nm"));
				jsonObj.put("unitCnt", resultSet.getInt("unit_cnt"));
				jsonObj.put("orderDiAmt", resultSet.getFloat("order_di_amt"));
				jsonObj.put("orderAmt", resultSet.getFloat("order_amt"));
				jsonObj.put("packingCharges", resultSet.getInt("packing_charges"));
				
				jsonArr.add(jsonObj);
			}
			
			System.out.println("Body JSON Output");
			System.out.println(jsonArr.toString());
			
			//Create JSON response object
			JSONHandler jsonh = new JSONHandler();
			jsonResponse = jsonh.createJSONResponse("Open Order Details", jsonArr);
			
			//Cleanup
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Return response
		return jsonResponse;
	}

	public void placeOrder() {
		
		try
		{
			
			int user_id;
			int dish_cnt;
			float order_amt;
			String comments;
			String order_sts;
			
			user_id = 1000003;
			dish_cnt = 2;
			order_amt = 300;
			comments = "Add ghee instead of oil";
			order_sts = "O";
			
			//Get Connection to database
			DbHandler dbh = new DbHandler();
			Connection conn = dbh.getDBConnection();
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO FoodieDB.tb_order(order_dt_time,user_id,dish_cnt,order_amt,comments,order_sts,create_ts,create_user,"
					+ "update_ts, update_user)" + 
					"	VALUES(now(),?,?,?,?,?,now(),current_user(),now(),current_user())";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);		
			
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, dish_cnt);
			pstmt.setFloat(3, order_amt);
			pstmt.setString(4, comments);
			pstmt.setString(5, order_sts);
			
			System.out.println("First query :" +sql);
		
			pstmt.execute();
			
			System.out.println("Inserted Order");
						
			ResultSet rs = pstmt.getGeneratedKeys();
			
			int orderId = 0;
			if (rs.next()) 
			 {
		            // Value of ID (Index 1 by table design).
		            orderId = rs.getInt(1);
		     }
			
			System.out.println("OrderID :" + orderId);
			
			String diSql = "INSERT INTO FoodieDB.tb_order_details(order_id, order_ln_id, dish_instance_id, unit_cnt, "
					+ "order_di_amt, create_ts, create_user, update_ts, update_user) VALUES("
					+ "?,?,?,?,?,now(),current_user(),now(),current_user())";
			
			PreparedStatement pstmtod = conn.prepareStatement(diSql);
			
			int order_ln_id;
			int dish_instance_id;
			int unit_cnt;
			float order_di_amt;
			
			order_ln_id = 1;
			dish_instance_id = 1;
			unit_cnt = 1;
			order_di_amt = 150;
			
			pstmtod.setInt(1, orderId);
			pstmtod.setInt(2, order_ln_id);
			pstmtod.setInt(3, dish_instance_id);
			pstmtod.setInt(4, unit_cnt);
			pstmtod.setFloat(5, order_di_amt);
			
			System.out.println("Second query :" + diSql);
						
			pstmtod.execute();
			
			System.out.println("Executed second query");
			
			
			String diSql2 = "INSERT INTO FoodieDB.tb_order_details(order_id, order_ln_id, dish_instance_id, unit_cnt, "
					+ "order_di_amt, create_ts, create_user, update_ts, update_user) VALUES("
					+ "?,?,?,?,?,now(),current_user(),now(),current_user())";
			
			PreparedStatement pstmtod2 = conn.prepareStatement(diSql2);
			
			order_ln_id = 2;
			dish_instance_id = 2;
			unit_cnt = 1;
			order_di_amt = 150;
			
			pstmtod2.setInt(1, orderId);
			pstmtod2.setInt(2, order_ln_id);
			pstmtod2.setInt(3, dish_instance_id);
			pstmtod2.setInt(4, unit_cnt);
			pstmtod2.setFloat(5, order_di_amt);
			
			System.out.println("Third query :" + diSql2);
			
			pstmtod2.execute();
			
			System.out.println("Executed third query");
			
			conn.commit();
			
			System.out.println("Commit complete");
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}