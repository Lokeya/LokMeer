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
          <View style={{alignItems: 'center', marginBottom: 20, marginTop: 20 }}>
            <Text style={styles.headerText}>Sign Up</Text>
          </View>
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
  headerText: {
    fontSize: 28,
    color: '#91C738',
    lineHeight: 29
  },
  input: {
    width: 275,
    height: 44,
    padding: 10,
    borderWidth: 2,
    borderColor: '#91C738',
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
    padding: 18,
    width: 222,
    marginBottom: 30,
    borderRadius: 10
  },
  
  buttonText: {
    fontSize: 22,
    color: 'white',
  },
});
