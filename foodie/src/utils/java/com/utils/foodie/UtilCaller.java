package com.utils.foodie;

import org.json.simple.JSONObject;

import com.engine.foodie.*;

public class UtilCaller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**********Order Management Calls*************/
		OrderManagement om = new OrderManagement();
		
		/*********Get list of dates which have active or sold out dish instances************/
		//JSONObject activeDates = om.getActiveDates(1);
		//System.out.println("Active Dates JSON:" + activeDates.toJSONString());
		/***********************************************************************************/
				
		/*********Get Dish instance count to display on Order food page*********************/
		//JSONObject dishInstCnt = om.getDishInstanceCountforDate("2018-08-29", 1);		
		//System.out.println("Dish Instance Count JSON:" + dishInstCnt.toJSONString());
		/***********************************************************************************/
		
		/*********Get Home chef count to display on Order food page*************************/
		//JSONObject homeChefCnt = om.getHomeChefCountforDate("2018-08-29", 1);		
		//System.out.println("Home chef Count JSON:" + homeChefCnt.toJSONString());
		/***********************************************************************************/
		
		/*********Get dish instance information to display on Order food page***************/
		JSONObject dishInstanceDtl = om.getDishInstancesForDate("2018-07-29", 1);		
		System.out.println("Dish instance detail JSON:" + dishInstanceDtl.toJSONString());
		/***********************************************************************************/
		
	}

}
