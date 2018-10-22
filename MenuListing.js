'use strict';

import React, { Component } from 'react'
import {
  StyleSheet,
  Image,
  View,
  TouchableHighlight,
  FlatList,
  Text,
  TouchableOpacity,
  ScrollView
} from 'react-native';

import MenuDetails from './MenuDetails';
import Footer from './Footer';

class ListItem extends React.PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      minQuantity: 1,
      quantity: 1,
      maxQuantity: 10
    };
  }

  _truncateHeaderText = (text) => {
    return text.length > 16 ? (text.substr(0, 16)+'...') : text;
  }
  _incrementQuantity = () => {
    if (this.state.quantity < this.state.maxQuantity){
      this.setState({ quantity: this.state.quantity + 1 });
    }
  }

  _decrementQuantity = () => {
    if (this.state.quantity > this.state.minQuantity) {
      this.setState({ quantity: this.state.quantity - 1 });
    }
  }
  _addToCart = () => {

  }
  _onPress = () => {
    this.props.onPressItem(this.props.index);
  }

  render() {
    const item = this.props.item;
    
    return (
      <View style={styles.container}>
        <TouchableHighlight
          onPress={this._onPress}
          underlayColor='#dddddd'>
          <View style={{flex:1, flexDirection:'row'}}>
            {/* Left Image View */}
            <View style={{flex:1, flexDirection:'column'}}>
                <Image source={require('./Resources/masaladosa.jpg')} style={styles.image} />
            </View>
            {/* Right Content View */}
            <View style={{ flex:2, flexDirection: 'column'}}>
              {/* Content Header */}
              <View style={{ height: 30, flexDirection: 'row', backgroundColor: '#91C738', justifyContent: 'space-between', alignItems: 'center'}}>
                <View stlye={{flex: 1, flexDirection: 'column'}}>
                  <Text style={{ color: '#FFFFFF', fontSize: 18, marginLeft: 5 }}>{this._truncateHeaderText(item.Dishnm)}</Text> 
                </View>
                <View stlyw={{ flex: 2, flexDirection: 'column' }}>
                    <Text style={{ color: '#FFFFFF', fontSize: 18, marginRight: 5}}>Rs 50</Text> 
                </View>
              </View>
              {/* Content By and Time */}
              <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'space-between', marginTop: 10}}>
                <View stlye={{flex:1, flexDirection: 'column'}}>
                  <View style={{flex: 1, flexDirection: 'row'}}>
                    <Text style={{marginRight: 2, marginLeft: 5}}>By {item.usernm} </Text> 
                    <Image source={require('./Resources/callhomechef.png')} style={{ height: 20, width: 20}} />
                  </View>
                </View>
                <View stlye={{ flex: 1, flexDirection: 'column' }}>
                  <View style={{flex: 1, flexDirection: 'row'}}>
                    <Image source={require('./Resources/generic_time.png')} style={{ height: 20, width: 20, marginRight: 5, marginLeft: 5}} />
                    <Text style={{marginRight: 5}}>8 Am - 10 PM</Text> 
                  </View>
                </View>
              </View>
              {/* Content Description */}
              <View style={{ flex: 1, flexDirection: 'row'}}>
                <Text style={{marginLeft: 5}}>{item.Dishdesc}</Text>
              </View>
              {/* Content Type Icon */}
              <View style={{ flex: 1, flexDirection: 'row' }}>
                <Image source={require('./Resources/veg_tag.png')} style={{ height: 20, width: 20, marginLeft: 5}} />
              </View>
            </View>
          </View>
        </TouchableHighlight>
        {/*Quantity and Button*/}
        <View style={{ flex: 1, flexDirection: 'row', alignItems: 'center'}}>
          {/*Quantity Select*/}
          <View style={{ flex: 1, flexDirection: 'column' }}>
            <View style={{ width: 120, flexDirection: 'row', marginTop: 5, padding: 5, backgroundColor: '#91C738', justifyContent: 'center', borderRadius: 100, marginLeft: 5 }}>
              <TouchableOpacity style={{ width: 40, textAlign: 'center'}} onPress={this._incrementQuantity}>
                <Text style={{ textAlign: 'center', color: '#FFFFFF', fontWeight: 'bold', fontSize: 20}}>+</Text>
              </TouchableOpacity>
              <Text style={{ width: 40, textAlign: 'center', color: '#FFFFFF', fontWeight: 'bold', fontSize: 22}}>{this.state.quantity}</Text>
              <TouchableOpacity style={{ width: 40, textAlign: 'center' }} onPress={this._decrementQuantity}>
                <Text style={{ textAlign: 'center', color: '#FFFFFF', fontWeight: 'bold', fontSize: 20}}>-</Text>
              </TouchableOpacity>
            </View>
          </View>
          {/*Add Button*/}
          <View stlye={{ flex: 1, flexDirection: 'column' }}>
            <TouchableOpacity style={{ width: 100, padding: 5, backgroundColor: '#91C738', marginTop: 5, alignItems: 'center', borderRadius: 100, marginRight: 5, justifyContent: 'center' }} onPress={this._addToCart}>
              <Text style={{ color: '#FFFFFF', fontWeight: 'bold', fontSize: 20, textAlign: 'center' }}> Add</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}

//Next, add the component:
export default class MoviesPlaying extends Component {
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
      <View style={{flex: 1}}>
        <ScrollView>
          <FlatList data={this.props.listings} keyExtractor={this._keyExtractor} renderItem={this._renderItem} />
        </ScrollView>
        <Footer activePage='MenuListing'/>
      </View>
    );
  }

}

const styles = StyleSheet.create({
  container: {
    marginTop: 20,
    flex: 1
  },
  image: {
    width: undefined,
    height: 150
  },
});
