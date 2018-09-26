package com.dbutils.foodie;

import org.json.simple.JSONObject;

import com.engine.foodie.*;

public class UtilCaller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**********GetDishInstanceCountForDate*************/
		OrderManagement om = new OrderManagement();
		
		//JSONObject dishInstCnt = om.getDishInstanceCountforDate("2018-08-29", 1);		
		//System.out.println("Dish Instance Count JSON:" + dishInstCnt.toJSONString());
		
		JSONObject activeDates = om.getActiveDates(1);
		System.out.println("Active Dates JSON:" + activeDates.toJSONString());
		
	}

}
