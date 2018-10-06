import React, { Component } from 'react';
import { Alert, Button, TextInput, Text, View, StyleSheet,ImageBackground, TouchableOpacity, Image } from 'react-native';
import WelcomePage from './WelcomePage'; 

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
      component: WelcomePage,
      });
  }

  onPress = () => {
    this.props.navigator.push({
      title: 'Today Menu at Knock Kitchen',
      component: WelcomePage,
      });
  }

  render() {
    return (
     
      <View style={styles.container}>
       <ImageBackground source={require('./Resources/bg_image.jpg')} style={styles.backgroundImage} >
       <View style={ styles.loginForm }>
        <TextInput
          value={this.state.username}
          onChangeText={(username) => this.setState({ username })}
          placeholder={'10-digit Phone Number or Email'}
          style={styles.input}
        />
        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'Password'}
          secureTextEntry={true}
          style={styles.input}
        />
        
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
  
  input: {
    width: 250,
    height: 44,
    padding: 10,
    borderWidth: 4,
    borderColor: '#A0A0A0',
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
    width: 250,
    marginBottom: 30,
  },
  
  buttonText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },
});
