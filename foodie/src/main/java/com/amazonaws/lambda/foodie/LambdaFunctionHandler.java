package com.amazonaws.lambda.foodie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.engine.foodie.OrderManagement;

public class LambdaFunctionHandler implements RequestStreamHandler {

    @SuppressWarnings("unchecked")
	@Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        
        
    	LambdaLogger logger = context.getLogger();
    	InputStreamReader isr = new InputStreamReader(input, Charset.forName("UTF-8"));
    	BufferedReader br = new BufferedReader(isr);
    	String in = ""; 
    	String line1 = "";
    	while ((line1 = br.readLine()) != null) {

             in += line1;
    		logger.log(" new output "+ in);
    	}  
    	
        
        logger.log("Invoked Foodie " + in);
        
        JSONParser parser = new JSONParser();
        
        Object objIn = null ;
        
        try {

             objIn = parser.parse(in);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
        JSONObject inObj =  (JSONObject)objIn;
        Collection inVal = inObj.values();
        
        
        logger.log("Invoked Foodie collection " + inVal);
        
        
        String name = (String) inObj.get("api");
        System.out.println(name);

        
        String func_name = (String) inObj.get("func_name");
        System.out.println(func_name);

        
     // getting address 
        Map func_params = ((Map)inObj.get("function_params")); 
          
        // iterating address Map 
        Iterator<Map.Entry> itr1 = func_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue()); 
        } 
          
        
        String currentTime = "unavailable";

        // Get time from DB server
        try {
/*          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
          String username = "Lokmeer";
          String password = "Welcome123";

          Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
          ResultSet resultSet = stmt.executeQuery("SELECT user_nm,Dish_nm,Dish_desc, Pickup_start_tm,Pickup_end_tm,Menu_dt FROM FoodieDB.tb_Dishold d  , FoodieDB.tb_User u   where     d.User_id = u.user_id ");
          JSONArray array = new JSONArray();
          
         while (resultSet.next()) {
          
        	 
        	JSONObject listObj = new JSONObject();
            listObj.put("usernm", resultSet.getObject(1).toString());
            listObj.put("Dishnm", resultSet.getObject(2).toString());
            listObj.put("Dishdesc", resultSet.getObject(3).toString());             
            listObj.put("Pickupstarttm", resultSet.getTime(4).getTime());             
            listObj.put("Pickupendtm", resultSet.getTime(5).getTime());             
//            listObj.put("Menudt", resultSet.getObject(6).toString());             
            array.add(listObj);
            
          }
*/         
         /**********Order Management Calls*************/
 		OrderManagement om = new OrderManagement();
 		JSONObject returnObj = new JSONObject();
 		
 		
 		if(func_name.equalsIgnoreCase("getActiveDates"))
 		{
 			System.out.println("Function name matched " + "getActiveDates");
 			returnObj = om.getActiveDates(func_params);
 		
 		}
 		
 		else if(func_name.equalsIgnoreCase("getDishInstanceCountforDate"))
 		{
 			//"2018-08-29", 1
 			returnObj = om.getDishInstanceCountforDate(func_params);		
 		
 		} else if(func_name.equalsIgnoreCase("getHomeChefCountforDate"))
 		{	
 		 //	"2018-08-29", 1
 		/*********Get Home chef count to display on Order food page*************************/
 		returnObj = om.getHomeChefCountforDate(func_params);		
 		System.out.println("Home chef Count JSON:" + returnObj.toJSONString());
 		}
 		
 		else if(func_name.equalsIgnoreCase("getDishInstancesForDate"))
 		{
 		/*********Get dish instance information to display on Order food page***************/
 			//"2018-07-29", 1
 			returnObj = om.getDishInstancesForDate(func_params);		
 		System.out.println("Dish instance detail JSON:" + returnObj.toJSONString());
 		
 		}
 		else if(func_name.equalsIgnoreCase("getDishInstancesForDate"))
 		{
 		/*********Get dish instance information to display on Order food page***************/
 			//"2018-07-29", 1
 			returnObj = om.getDishInstancesForDate(func_params);		
 		System.out.println("Dish instance detail JSON:" + returnObj.toJSONString());
 		
 		}
 		else if(func_name.equalsIgnoreCase("getOpenOrders"))
 		{
 		/*********Get dish instance information to display on Order food page***************/
 			// 1
 			returnObj = om.getOpenOrders(func_params);		
 		System.out.println("Dish instance detail JSON:" + returnObj.toJSONString());
 		
 		}
          
          /*StringBuilder wrapperObj = new StringBuilder();
          wrapperObj.append(" { \"statusCode\" : 200 , ");
          wrapperObj.append("\"headers\" : {  \"some\" : \"some\"  } ,");
          //wrapperObj.append("\"body\"  : " +  "{ \"uuuu\" : \" ppp \"}" + ", ");
          wrapperObj.append("\"isBase64Encoded\" : false" + " } ");
          */ 
          

        /* JSONObject headerJson = new JSONObject();
         headerJson.put("x-custom-header", "my custom header value");
         
         
          JSONObject wrapperObj = new JSONObject();
          wrapperObj.put("statusCode" ,200 );
          wrapperObj.put("headers", headerJson);
          wrapperObj.put("body"  ,array.toJSONString());
          wrapperObj.put("isBase64Encoded" ,false );
        */  
          
         
          OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
          writer.write(returnObj.toJSONString());  
          writer.close(); 
          
        } catch (Exception e) {
          e.printStackTrace();
          logger.log("Caught exception: " + e.getMessage());
        }

        
        logger.log("Successfully executed query.  Result for LokMeer Project " + currentTime);
        
    }
    
   
}
