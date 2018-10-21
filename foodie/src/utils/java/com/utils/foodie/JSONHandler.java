package com.utils.foodie;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class JSONHandler {
	
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
