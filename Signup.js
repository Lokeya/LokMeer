import React, { Component } from 'react';
import { Alert, Text, Button, TextInput, TouchableOpacity, View, StyleSheet,ImageBackground, Image } from 'react-native';
import Signupform from './Signupform'; 

export default class App extends Component {
  constructor(props) {
    super(props);
    
    this.state = {
      username: '',
      password: '',
    };
  }
  
  onLogin() {
    const { username, password } = this.state;

    //Alert.alert('Credentials', `${username} + ${password}`);
    this.props.navigator.push({
      title: 'Today Menu at Olympia Oapline',
      component: Login,
      });
  }

  onPress = () => {
    this.props.navigator.push({
      title: 'Welcome to Knock Kitchen',
      component: Signupform,
      });
  }

  render() {
    return (
     
      <View style={styles.container}>
       <ImageBackground source={require('./Resources/bg_image.jpg')} style={styles.backgroundImage} >
           <View style={ styles.loginForm }>
          
            <TouchableOpacity
               style={styles.button}
               onPress={this.onPress}
             >
               <Text style={styles.buttonText}> Sign Up </Text>
             </TouchableOpacity>

             <TouchableOpacity
               style={styles.button}
               onPress={this.onPress}
             >
               <Text style={styles.buttonText}> Sign In </Text>
             </TouchableOpacity>

            </View>
         </ImageBackground>
      </View>
     
    );
  }
}

const styles = StyleSheet.create({
  /*container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#ecf0f1',
  },*/
  input: {
    width: 200,
    height: 44,
    padding: 10,
    borderWidth: 4,
    borderColor: 'grey',
    marginBottom: 20,
  },
  container: {
    flex: 1,
  },

  backgroundImage: {
    flex: 1,
    resizeMode: 'cover', // or 'stretch',
    justifyContent: 'center',
  },

  loginForm: {
    backgroundColor: 'transparent',
    alignItems: 'center',
  },

  text: {
    fontSize: 40,
    fontWeight: 'bold',
    backgroundColor: 'black'
  },

button: {
    alignItems: 'center',
    backgroundColor: '#91C738',
    padding: 10,
    width: 180,
    marginBottom: 30,
  },

  buttonText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },
});
