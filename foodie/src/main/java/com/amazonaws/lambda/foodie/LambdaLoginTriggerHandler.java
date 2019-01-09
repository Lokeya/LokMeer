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

public class LambdaLoginTriggerHandler implements RequestStreamHandler {

    @SuppressWarnings("unchecked")
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        
        
    	LambdaLogger logger = context.getLogger();
    	
        // Get time from DB server
    	logger.log("Invoked Foodie LOGIN  " );
    	
    	try {
          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
          String username = "Lokmeer";
          String password = "Welcome123";

          Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
          stmt.executeUpdate("INSERT INTO FoodieDB.tb_User "
          		+ "(User_id, Community_id,  Building_id, Flat_id, Diet_type_id, User_nm, Email_addr, Phone_nbr, Encrypted_pwd, Seller_flg) " 
  + "VALUES (1000006, 1, 5, 36, 1, \"Kalai\", \"lokeya@gmail.com\", \"8778454872\", \"kdhskjfhskdjfhskhfskjfhsdsdkjfskldfjdslf\", 0)");

         /**********Order Management Calls*************/
          
          
          JSONObject headerJson = new JSONObject();
          headerJson.put("x-custom-header", "my custom header value");
          
          
           JSONObject wrapperObj = new JSONObject();
           wrapperObj.put("statusCode" ,200 );
           wrapperObj.put("headers", headerJson);
           wrapperObj.put("body"  ,"");
           wrapperObj.put("isBase64Encoded" ,false );
           
           
          
           OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
           writer.write(wrapperObj.toJSONString());  
           writer.close(); 

           
           
        } catch (Exception e) {
          e.printStackTrace();
          logger.log("Caught exception: " + e.getMessage());
        }

        
        logger.log("Successfully executed login query.  Result for LokMeer Project " );
        
    }
    
   
}
