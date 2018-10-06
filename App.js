/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */
 'use strict';

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,NavigatorIOS} from 'react-native';
import BottomNavigation from 'react-native-material-bottom-navigation'

import WelcomePage from './WelcomePage';
import Login from './Login';
import Signup from './Signup';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};


  export default class App extends Component<{}> {

    tabs = [
    {
      key: 'games',
      icon: 'gamepad-variant',
      label: 'Games',
      barColor: '#388E3C',
      pressColor: 'rgba(255, 255, 255, 0.16)'
    },
    {
      key: 'movies-tv',
      icon: 'movie',
      label: 'Movies & TV',
      barColor: '#B71C1C',
      pressColor: 'rgba(255, 255, 255, 0.16)'
    },
    {
      key: 'music',
      icon: 'music-note',
      label: 'Music',
      barColor: '#E64A19',
      pressColor: 'rgba(255, 255, 255, 0.16)'
    }
  ]
  
  render() {
    return (
     <NavigatorIOS
        style={styles.container}
        initialRoute={{
          title: 'Welcome to Knock Kitchen',
          component: Signup,
        }}/>

       // <Tab/>;
    );
  }
}



const styles = StyleSheet.create({
  description: {
    fontSize: 18,
    textAlign: 'center',
    color: '#656565',
    marginTop: 65,
  },
  container: {
  flex: 1,
},
});
