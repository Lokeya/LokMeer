import React from 'react';
import { TabNavigator } from "react-navigation";

import Login from '../Login';
import WelcomePage from '../WelcomePage';
import MenuListing from '../MenuListing';

export const Tab = TabNavigator({

	Login: 
	{
		Screen:Login,
	},

	WelcomePage: 
	{
		Screen:WelcomePage,
	},

	MenuListing: 
	{
		Screen:MenuListing,
	}
},

{
	tabBarPostion: "bottom",
	swipeEnabled: true,
	tabBarOptions: 
	{
		activeTintColor: '#f2f2f2',
		activeBackgroundColor: '#2EC4B6',
		inactiveTintColor: '#666',
		labelStyle:
		{
			fontSize: 22,
			padding: 12
		}
	}
}

)
