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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.engine.foodie.OrderManagement;

public class LambdaFunctionHandler implements RequestStreamHandler {

    @SuppressWarnings("unchecked")
	@Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        // TODO: Implement your stream handler. See https://docs.aws.amazon.com/lambda/latest/dg/java-handler-io-type-stream.html for more information.
        // This demo implementation capitalizes the characters from the input stream.
        /*int letter = 0;
        while((letter = input.read()) >= 0) {
            output.write(Character.toUpperCase(letter));
        }*/
        
    	LambdaLogger logger = context.getLogger();
    	InputStreamReader isr = new InputStreamReader(input, Charset.forName("UTF-8"));
    	BufferedReader br = new BufferedReader(isr);
    	String line = ""; 
    	String line1 = "";
    	while ((line1 = br.readLine()) != null) {

             line = line1;
    		logger.log(" new output "+ line);
    	}  
    	
        
        logger.log("Invoked Foodie " );

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
 		
 		if(line.contains("getActiveDates"))
 		{
 		
 		/*********Get list of dates which have active or sold out dish instances************/
 			returnObj = om.getActiveDates(1);
 		System.out.println("Active Dates JSON:" + returnObj.toJSONString());
 		/***********************************************************************************/
 		
 		}
 		else if(line.contains("getDishInstanceCountforDate"))
 		{
 		/*********Get Dish instance count to display on Order food page*********************/
 			returnObj = om.getDishInstanceCountforDate("2018-08-29", 1);		
 		System.out.println("Dish Instance Count JSON:" + returnObj.toJSONString());
 		/***********************************************************************************/
 		
 		} else if(line.contains("getHomeChefCountforDate"))
 		{	
 		/*********Get Home chef count to display on Order food page*************************/
 		returnObj = om.getHomeChefCountforDate("2018-08-29", 1);		
 		System.out.println("Home chef Count JSON:" + returnObj.toJSONString());
 		/***********************************************************************************/
 		}
 		
 		else if(line.contains("getDishInstancesForDate"))
 		{
 		/*********Get dish instance information to display on Order food page***************/
 			returnObj = om.getDishInstancesForDate("2018-07-29", 1);		
 		System.out.println("Dish instance detail JSON:" + returnObj.toJSONString());
 		/***********************************************************************************/
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
