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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.events.CognitoEvent;
import com.engine.foodie.OrderManagement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        
        
        
        String user_nm = (String) inObj.get("userName");
        logger.log("user_nm " + user_nm);

        
        int userid = getUserId(100000,1000000) ;
        
        
        String email = (String) ((JSONObject) ((JSONObject)inObj.get("request")).get("userAttributes")).get("email");
        logger.log("email  " + email);

        
/*        JSONArray arr = ((Object) inObj).getJSONArray("request");
        for (int i = 0; i < arr.length(); i++)
        {
            String post_id = arr.getJSONObject(i).getString("post_id");
            ......
        }

        Map req_params = ((Map)inObj.get()); 
        
        // iterating address Map 
        Iterator<Map.Entry> itr1 = req_params.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            logger.log(pair.getKey() + " : " + pair.getValue()); 
        }
        
  */  	
    	/*
    	
    	//---- top-level items in event like userName & request
        String reqPoolId = eventNode.path("userPoolId").asText();
        String user_nm = eventNode.path("userName").asText();  // camel-case
        String userid = eventNode.path("userid").asText();  // camel-case
        
        
//        JsonNode callerContextNode = eventNode.path("callerContext");
        JsonNode requestNode = eventNode.path("request");
 
        //---- items under event.request like userAttrs
        JsonNode userAttrsNode = requestNode.path("userAttributes");
 
        //---- items under event.request.userAttributes like email
        String email = userAttrsNode.path("email").asText();
        String phone = userAttrsNode.path("phone").asText();
        // Get time from DB server
    	logger.log("Invoked Foodie LOGIN  " );
    	*/

        logger.log(" User info  " + userid + " " + user_nm + " " + email);
        
        
    	try {
          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
          String username = "Lokmeer";
          String password = "Welcome123";

          Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
          stmt.executeUpdate("INSERT INTO FoodieDB.tb_User "
          		+ "(User_id, Community_id,  Building_id, Flat_id, Diet_type_id, User_nm, Email_addr, Phone_nbr, Encrypted_pwd, Seller_flg) "  
  + "VALUES (" + userid  +  ", 1, 5, 36, 1, \" " +  user_nm  + "\", \" "+ email + " \", \"8778454872\", \"kdhskjfhskdjfhskhfskjfhsdsdkjfskldfjdslf\", 0)");

         /**********Order Management Calls*************/
          
           
        } catch (Exception e) {
          e.printStackTrace();
          logger.log("Caught exception: " + e.getMessage());
        }

        
        logger.log("Successfully executed login query.  Result for LokMeer Project " );
        
        
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
