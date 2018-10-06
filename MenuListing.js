'use strict';

import React, { Component } from 'react'
import {
  StyleSheet,
  Image,
  View,
  TouchableHighlight,
  FlatList,
  Text,
} from 'react-native';

import MenuDetails from './MenuDetails';

class ListItem extends React.PureComponent {
  _onPress = () => {
    this.props.onPressItem(this.props.index);
  }

  render() {
    const item = this.props.item;
    
    return (
     
     <TouchableHighlight
        onPress={this._onPress}
        underlayColor='#dddddd' style={{flex:100,flexDirection:'row'}}>

        <View style={styles.container}>
          <View style={[{flexDirection:'row'}, styles.elementsContainer]}>
            <Image source={require('./Resources/masaladosa.jpg')} style={styles.image}/>

            <View style={{ flexDirection:'column', width: 200, height: 170}}> 

                  <View style={{ flexDirection:'row', width: 300, height: 30, backgroundColor: '#91C738'}}>
                         <Text>{item.Dishnm}</Text> 
                    </View>
                   <View style={{ flexDirection:'row', width: 300, height: 30}}>
                       <Text>By {item.usernm} </Text> 
                       <Image source={require('./Resources/callhomechef.png')} style={{height:20, width:20}}/>
                  </View>
                  
                  
                  <View style={{ flexDirection:'row'}}>
                      <Text style={{flex: 1, flexWrap: 'wrap'}}> {item.Dishdesc}</Text> 
                  </View>
                  <View style={{ flexDirection:'row', width: 300, height: 30}}>
                      <Image source={require('./Resources/Ellipse.png')} style={{height:20, width:20}}/>
                 </View>
                 <View style={{ flexDirection:'row', width: 300, height: 30}}>
                      <Image source={require('./Resources/1083479.png')} style={{height:20, width:20}}/>
                      <Text>8 Am - 10 PM</Text> 
                 </View>
                  
            </View>

          <View style={{ flexDirection:'column', width: 100, height: 100}}> 
           <View style={{flexDirection:'row', width: 100, height: 30}}> 
             <View style={{ backgroundColor:'#91C738'}}>
                      <Text>Rs 50</Text> 
              </View>
             
              
          </View>
          </View>
        </View>
      </View>

 </TouchableHighlight>
      
    );
  }
}

//Next, add the component:

export default class MoviesPlaying extends Component<{}> {
 _keyExtractor = (item, index) => index.toString();

  _renderItem = ({item, index}) => (
  <ListItem
    item={item}
    index={index}
    onPressItem={this._onPressItem}
  />
  );

  _onPressItem = (index) => {   
    console.log("Pressed row: "+index);

    this.props.navigator.push({
      title: 'Food Details',
      component: MenuDetails,
      passProps: {movie: this.props.listings[index]}
      });
   
  };
 
  render() {
    return (
      <FlatList
        data={this.props.listings}
        keyExtractor={this._keyExtractor}
        renderItem={this._renderItem}
      />
    );
  }

}

const styles = StyleSheet.create({

  testContainer:{
    flex:1,
  },

  navBar:{
    height:55,
    backgroundColor:'white',
    elevation:3,

  },

  tabBar: {
    backgroundColor:'red',
    height: 60
  },

  newcontainer: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'flex-start'
  },
  item :{
    flex: 0.25,
    // width: 150, //using fixed item width instead of flex: 0.5 works
    height: 100,
    // flexGrow: 1,
    // flexShrink: 0,
    padding: 10,
  },

  description: {
    marginBottom: 20,
    fontSize: 24,
    textAlign: 'center',
    color: '#656565'
  },
  containerold: {
    flexDirection: 'row',
    //flex:1,
   // flexWrap: 'wrap',
    padding: 5,
    marginTop: 2,
    width:'50%',
    height: '25%'
  },
 thumb: {
    width: 80,
    height: 100,
    marginLeft: 5,
    marginRight: 10
  },
  textContainer: {
    flex: 1,
  },
  separator: {
    height: 1,
    backgroundColor: '#dddddd'
  },
  textdescriptor: {
    fontSize: 12,
    fontWeight: 'bold',
    color: '#000000'
  },
  title: {
    fontSize: 18,
    color: '#F9F9F9',
    fontFamily: 'Arial',
    backgroundColor: '#91C738',
    textAlign: 'left',
    width: '130%'
  },
  title2: {
    fontSize: 18,
    textAlign: 'right'
  },
  rowContainer: {
    flexDirection: 'row',
    padding: 5
  },

  container: {
    marginTop: 15,
    flex: 1
  },
  headerStyle: {
    fontSize: 24,
    textAlign: 'center',
    fontWeight: '100',
    marginBottom: 24
  },
  elementsContainer: {
    flex: 1,
    backgroundColor: '#ecf5fd',
    marginLeft: 4,
    marginRight: 4,
    marginBottom: 1
  },
   image: {
  width: 130,
  height: 150
},
  
});
