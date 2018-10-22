import React, { Component } from 'react';
import { Alert, Text, Button, TextInput, TouchableOpacity, View, StyleSheet, ImageBackground, Image, ScrollView } from 'react-native';
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
        <ImageBackground source={require('./Resources/bg_image.jpg')} style={styles.backgroundImage}>
          <ScrollView>
            <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'center', marginBottom: 20, marginTop: 20}}>
              <Text style={styles.headerText}>Sign Up</Text>
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.username}
                onChangeText={(username) => this.setState({ username })}
                placeholder={'Name'}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.password}
                onChangeText={(password) => this.setState({ password })}
                placeholder={'yourname@gmail.com'}
                secureTextEntry={true}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.password}
                onChangeText={(password) => this.setState({ password })}
                placeholder={'password'}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.password}
                onChangeText={(password) => this.setState({ password })}
                placeholder={'Mobile Number'}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.password}
                onChangeText={(password) => this.setState({ password })}
                placeholder={'City'}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <TextInput
                value={this.state.password}
                onChangeText={(password) => this.setState({ password })}
                placeholder={'Apartment/Community'}
                style={styles.input}
              />
            </View>
            <View style={styles.loginForm}>
              <View style={{ marginRight: 10 }}>
                <TextInput
                  value={this.state.password}
                  onChangeText={(password) => this.setState({ password })}
                  placeholder={'Building/Block'}
                  style={styles.inputBuilding}
                />
              </View>
              <View>
                <TextInput
                  value={this.state.password}
                  onChangeText={(password) => this.setState({ password })}
                  placeholder={'Flat Number'}
                  style={styles.inputFlat}
                />
              </View>
            </View>
            <View style={styles.loginForm}>
              <TouchableOpacity
                style={styles.button}
                onPress={this.onPress}
              >
                <Text style={styles.buttonText}> Sign Up </Text>
              </TouchableOpacity>
            </View>
          </ScrollView>
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
    backgroundColor: 'rgba(0,0,0,0.1)', // 40% opaque
  },
  inputBuilding: {
    width: 160,
    height: 44,
    padding: 10,
    borderWidth: 2,
    borderColor: '#91C738',
    marginBottom: 20,
    backgroundColor: 'rgba(0,0,0,0.1)', // 40% opaque
  },
  inputFlat: {
    width: 108,
    height: 44,
    padding: 10,
    borderWidth: 2,
    borderColor: '#91C738',
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
    flexDirection: 'column'
  },

  loginForm: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: 'transparent',
    alignItems: 'center',
    justifyContent: 'center'
  },

  inputView: {
    top: 0,
    left: 5,
    right: 5,
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
    marginTop: 25,
    marginBottom: 10,
    borderRadius: 10
  },

  buttonText: {
    fontSize: 20,
    color: 'white',
  },
});
