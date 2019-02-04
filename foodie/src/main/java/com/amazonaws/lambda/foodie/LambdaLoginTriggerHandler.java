package com.amazonaws.lambda.foodie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaLoginTriggerHandler implements RequestStreamHandler{

	/*
	 * (non-Javadoc)
	 * @see com.amazonaws.services.lambda.runtime.RequestStreamHandler#handleRequest(java.io.InputStream, java.io.OutputStream, com.amazonaws.services.lambda.runtime.Context)
	 */
	
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	
	
    @SuppressWarnings("unchecked")
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

    	//InputStream stream = input;
        //JsonNode eventNode = OBJECT_MAPPER.readTree(input);  // root node

        
    	LambdaLogger logger = context.getLogger();
    	logger.log("New Login code eee");
    	
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
        logger.log(inObj.toJSONString());
        
        /**
         * Steps to check:
         * 	1. Make sure the User-Status is CONFIRMED from cognito
         *  2. Note that userID is auto-increment
         */
        String user_status = (String) ((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("cognito:user_status");
        
        if(user_status.equalsIgnoreCase("CONFIRMED"))
        {
	        String user_nm = (String) inObj.get("userName");
	        logger.log("user_nm " + user_nm);
	
	        
	        int userid = getUserId(100000,1000000) ;
	        
	       
	        String email = (String) ((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("email");
	        String mobile = (String) ((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("custom:mobile");
	        //int building = Integer.parseInt(((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("custom:building"));
	        int building = Integer.parseInt((String)((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("custom:building"));
	        int flat = Integer.parseInt((String)((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("custom:flat"));
	        String city = (String) ((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("custom:city");
	        logger.log("email  " + email + " building "+ building + " flat "+ flat);
	
	        logger.log(" User info  " + userid + " " + user_nm + " " + email);
	        
		        
		    	try {
		          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
		          String username = "Lokmeer";
		          String password = "Welcome123";
		
		          Connection conn = DriverManager.getConnection(url, username, password);
		          Statement stmt = conn.createStatement();
		          //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
		          stmt.executeUpdate("INSERT INTO FoodieDB.tb_User "
		          		+ "(User_id, "
		          		+ "Community_id,  "
		          		+ "Building_id, "
		          		+ "Flat_id, "
		          		+ "Diet_type_id, "
		          		+ "User_nm, "
		          		+ "Email_addr, "
		          		+ "Encrypted_pwd, "
		          		+ "phone_nbr, "
		          		+ "Seller_flg) "  
		                + "VALUES (" + userid  +  ", 1, "+ building +", "+flat +", 1, \" " +  user_nm  + "\", \" "+ email + " \",  \"kdhskjfhskdjfhskhfskjfhsdsdkjfskldfjdslf\",\"" +  mobile  + "\",0)");
		
		         /**********Order Management Calls*************/
		          
		           
		        } catch (Exception e) {
		          e.printStackTrace();
		          logger.log("Caught exception: " + e.getMessage());
		        }

        }
        //Inserted user successfully by this point into DB
        logger.log("Successfully executed user-db persistence trigger.  Result for LokMeer Project " );
        
        
        //output.write(in.getBytes()); ;
        
        //OBJECT_MAPPER.writeValue(output,eventNode);
    
     // Success Condition, send back same JSON
        try (Writer w = new OutputStreamWriter(output, "UTF-8")) {
        w.write(inObj.toString());
        }
        
    
    }
    
    
    public int getUserId(int maximum, int minimum)
    {
    	Random rn = new Random();
    	int n = maximum - minimum + 1;
    	int i = rn.nextInt() % n;
    	int randomNum =  minimum + i;
    	
    	return randomNum;
    }
   
}
