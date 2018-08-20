'use strict';

import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  Button,
  ActivityIndicator,
  Image,
  TouchableOpacity
} from 'react-native';

import MenuListing from './MenuListing';

function urlForQueryAndPage(key, value, pageNumber) {
 const data = {
    api_key: '33546cde72549b76aec722c9b3e80943',

  }
  data[key] = value;

  const querystring = Object.keys(data)
    .map(key => key + '=' + encodeURIComponent(data[key]))
    .join('&');
    console.log("The Query String..."+querystring)
    return 'https://j6pbwt4xd7.execute-api.us-east-1.amazonaws.com/default/FoodieList';
}

export default class WelcomePage extends Component<{}> {

constructor(props) {
  super(props);
  this.state = {
  isLoading: false,
  message: '',
};

}

_handleResponse = (response) => {
	console.log("json from result.....")
  	this.setState({ isLoading: false , message: '' });
  
    console.log('Movies Playing: ' +response.length);

    this.props.navigator.push({
      title: 'Today Menu at Olympia Oapline',
      component: MenuListing,
      passProps: {listings: response}
      });

   
  };
  
  render() {
   const spinner = this.state.isLoading ?
  <ActivityIndicator size='large'/> : null;

    return (
      <View style={styles.container}>
        <Text style={styles.description}>
          Welcome to the FoodConnect App
        </Text>

        <Image source={require('./Resources/tix.jpg')} style={styles.image}/>
      
      <TouchableOpacity
          style={styles.searchButton}
          onPress={this._onSearchPressed}
          underlayColor='#fff'>
          <Text style={styles.searchButtonText}>Now Serving</Text>
      </TouchableOpacity> 
    {spinner}
    <Text style={styles.description}>{this.state.message}</Text>
      </View>
      
    );
  }


_executeQuery = (query) => {
  console.log("Printing Query..."+query);
  this.setState({ isLoading: true });
  
       fetch(query)
	  .then(r => r.json())
	  .then(json => this._handleResponse(json))
	  
  .catch(error =>
     this.setState({
      isLoading: false,
      message: 'Something bad happened ' + error
    }));
  console.log('Done');

};

_onSearchPressed = () => {
	console.log("in the search pressed....")
  const query = urlForQueryAndPage('region', 'US', 1);
  this._executeQuery(query);
  };
}




const styles = StyleSheet.create({
  description: {
    marginBottom: 20,
    fontSize: 18,
    textAlign: 'center',
    color: '#656565'
  },
  container: {
    padding: 30,
    marginTop: 65,
    alignItems: 'center'
  },
  flowRight: {
  flexDirection: 'row',
  alignItems: 'center',
  alignSelf: 'stretch',
},
searchInput: {
  height: 36,
  padding: 4,
  marginRight: 5,
  flexGrow: 1,
  fontSize: 18,
  borderWidth: 1,
  borderColor: '#48BBEC',
  borderRadius: 8,
  color: '#48BBEC',
},
searchButton:{
    marginRight:40,
    marginLeft:40,
    marginTop:50,
    paddingTop:10,
    paddingBottom:10,
    backgroundColor:'#669ae0',
    borderRadius:0,
    borderWidth: 1,
    borderColor: '#fff',
  },
  searchButtonText:{
      color:'#fff',
      textAlign:'center',
      paddingLeft : 10,
      paddingRight : 10,
      fontSize: 24,
  },
  image: {
  width: 217,
  height: 138,
},
});
