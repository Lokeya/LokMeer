package com.amazonaws.lambda.foodie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

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
        logger.log("Invoked Foodie ");

        String currentTime = "unavailable";

        // Get time from DB server
        try {
          String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
          String username = "Lokmeer";
          String password = "Welcome123";

          Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
          ResultSet resultSet = stmt.executeQuery("SELECT user_id,Dish_nm,Dish_desc FROM FoodieDB.tb_Dishold;");
          JSONArray array = new JSONArray();
          
         while (resultSet.next()) {
          
        	 
        	JSONObject listObj = new JSONObject();
            listObj.put("username", resultSet.getObject(1).toString());
            listObj.put("title", resultSet.getObject(2).toString());
             
            array.add(listObj);
            
          }
          
          /*StringBuilder wrapperObj = new StringBuilder();
          wrapperObj.append(" { \"statusCode\" : 200 , ");
          wrapperObj.append("\"headers\" : {  \"some\" : \"some\"  } ,");
          //wrapperObj.append("\"body\"  : " +  "{ \"uuuu\" : \" ppp \"}" + ", ");
          wrapperObj.append("\"isBase64Encoded\" : false" + " } ");
          */ 
          

         JSONObject headerJson = new JSONObject();
         headerJson.put("x-custom-header", "my custom header value");
         
         
          JSONObject wrapperObj = new JSONObject();
          wrapperObj.put("statusCode" ,200 );
          wrapperObj.put("headers", headerJson);
          wrapperObj.put("body"  ,array.toJSONString());
          wrapperObj.put("isBase64Encoded" ,false );
          
          
         
          OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
          writer.write(wrapperObj.toJSONString());  
          writer.close(); 
          
        } catch (Exception e) {
          e.printStackTrace();
          logger.log("Caught exception: " + e.getMessage());
        }

        
        logger.log("Successfully executed query.  Result for LokMeer Project " + currentTime);
        
    }
    
   
}
