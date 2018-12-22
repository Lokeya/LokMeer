package com.utils.foodie;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader; 
import java.io.IOException;
import java.io.File;

public class JSONHandler {
	
	public void parsePlaceOrderJSON()
	{
		String filePath = new File("").getAbsolutePath();
		System.out.println("Filepath is "+filePath);
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader(filePath+"\\placeOrder.json"));
		} catch (ParseException e) {
			System.out.println("Parse Exception "+e.toString());
		} catch (IOException ioe) {
			System.out.println("IO Exception "+ioe.toString());
		}
		
		// typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj;
      
        // getting user details 
        Map address = ((Map)jo.get("user"));
         
        // iterating address Map 
        Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            System.out.println(pair.getKey() + " : " + pair.getValue()); 
        } 
          
        // getting cart items 
        JSONArray ja = (JSONArray) jo.get("cart_items"); 
          
        // iterating cart items 
        Iterator itr2 = ja.iterator(); 
          
        while (itr2.hasNext())  
        { 
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            while (itr1.hasNext()) { 
                Map.Entry pair = itr1.next(); 
                System.out.println(pair.getKey() + " : " + pair.getValue()); 
            } 
        } 
    } 
	
	public JSONObject createJSONResponse(String headerVal, JSONObject bodyJson)
	{
		JSONObject wrapperObj = new JSONObject();   
		try
		{
		 JSONObject headerJson = new JSONObject();
         headerJson.put("x-custom-header", headerVal);
         
         wrapperObj.put("statusCode" ,200 );
         wrapperObj.put("headers", headerJson);
         wrapperObj.put("body"  ,bodyJson.toJSONString());
         wrapperObj.put("isBase64Encoded" ,false );
         
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return wrapperObj;	
	}
	
	public JSONObject createJSONResponse(String headerVal, JSONArray bodyJson)
	{
		JSONObject wrapperObj = new JSONObject();   
		try
		{
		 JSONObject headerJson = new JSONObject();
         headerJson.put("x-custom-header", headerVal);
         
         wrapperObj.put("statusCode" ,200 );
         wrapperObj.put("headers", headerJson);
         wrapperObj.put("body"  ,bodyJson.toJSONString());
         wrapperObj.put("isBase64Encoded" ,false );
         
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return wrapperObj;	
	}
}
