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
import com.amazonaws.services.lambda.runtime.events.CognitoEvent;
import com.engine.foodie.OrderManagement;

public class LambdaLoginTriggerHandler implements RequestStreamHandler{

	/* Input Request Format
	 * 
	 *  {
    "request": {
        "userAttributes": {
            "sub": "10d2f0d6-81e5-4b1e-a858-30f9a4a368ab",
            "cognito:user_status": "CONFIRMED",
            "email_verified": "true",
            "email": "Meenas@gmail.com"
        }
    },
    "callerContext": {
        "clientId": "2dv4e6r1gsdgufenai3clm5ic6",
        "awsSdkVersion": "aws-sdk-unknown-unknown"
    },
    "response": {},
    "region": "us-east-1",
    "userName": "Test891",
    "triggerSource": "PostConfirmation_ConfirmSignUp",
    "version": "1",
    "userPoolId": "us-east-1_4X7ty24eC"
} 
	 * 
	 * (non-Javadoc)
	 * @see com.amazonaws.services.lambda.runtime.RequestStreamHandler#handleRequest(java.io.InputStream, java.io.OutputStream, com.amazonaws.services.lambda.runtime.Context)
	 */
	
	
	
    @SuppressWarnings("unchecked")
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        
        
    	LambdaLogger logger = context.getLogger();
    	
    	InputStreamReader isr = new InputStreamReader(input, Charset.forName("UTF-8"));
    	BufferedReader br = new BufferedReader(isr);
    	String in = ""; 
    	String line1 = "";
    	while ((line1 = br.readLine()) != null) {

             in += line1;
    		logger.log(" new user output "+ in);
    	}  
    	
        
        logger.log("Invoked Foodie Login function " + in);
        
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
        
        System.out.println(inObj.toJSONString());
    	
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
          
          
                     
           
        } catch (Exception e) {
          e.printStackTrace();
          logger.log("Caught exception: " + e.getMessage());
        }

        
        logger.log("Successfully executed login query.  Result for LokMeer Project " );
        
        
        return ;
    }
    
   
}
