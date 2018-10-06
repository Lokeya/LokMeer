import React, { Component } from 'react';
import { Alert, Text, Button, TextInput, TouchableOpacity, View, StyleSheet,ImageBackground, Image } from 'react-native';
import Login from './Login'; 

export default class App extends Component {
  constructor(props) {
    super(props);

     this.state = {
      username: '',
      password: '',
    };
  }

  onPress = () => {
    this.props.navigator.push({
      title: 'Sign up for Knock Kitchen',
      component: Login,
      });
  }

  render() {
    return (
     
      <View style={styles.container}>
       <ImageBackground source={require('./Resources/bg_image.jpg')} style={styles.backgroundImage} >
           <View style={ styles.loginForm }>
           <View style={styles.inputView}>
          
          <TextInput
          value={this.state.username}
          onChangeText={(username) => this.setState({ username })}
          placeholder={'Name'}
          style={styles.input}
        />
        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'yourname@gmail.com'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'password'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'Mobile Number'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'City'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'Apartment/Community'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'Building/Block'}
          secureTextEntry={true}
          style={styles.input}
        />

        <TextInput
          value={this.state.password}
          onChangeText={(password) => this.setState({ password })}
          placeholder={'Flat Number'}
          secureTextEntry={true}
          style={styles.input}
        />

             <TouchableOpacity
               style={styles.button}
               onPress={this.onPress}
             >
               <Text style={styles.buttonText}> Sign Up </Text>
             </TouchableOpacity>

            </View>
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
    borderWidth: 2,
    borderColor: 'grey',
    marginBottom: 20,
    backgroundColor: 'rgba(0,0,0,0.1)', // 40% opaque
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

  inputView: {
           // backgroundColor: 'rgba(0,0,0,0)',
           // position: 'absolute', 
            top: 0,
            left: 5,
            right: 5,
           // backgroundColor: 'rgba(0,0,0,0.2)', // 40% opaque
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
