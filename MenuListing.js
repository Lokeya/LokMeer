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
        underlayColor='#dddddd'>
        <View>
          <View style={styles.rowContainer}>
            <Image style={styles.thumb} source={{ uri: 'https://1.bp.blogspot.com/-i8xx8e8ZcGU/U-xzv4NO0iI/AAAAAAAAJ0Y/PwuQA0vfyq4/s1600/South%2BIndian%2BDosa-Plain%2Bdosa.jpg'}} /> 
            <View style={styles.textContainer}>
              <Text style={styles.title}
                numberOfLines={1}>{item.title}</Text>
                <Text style={styles.votes}
                numberOfLines={1}>{item.username}</Text>
                 </View>
          </View>
          <View style={styles.separator}/>
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
  description: {
    marginBottom: 20,
    fontSize: 24,
    textAlign: 'center',
    color: '#656565'
  },
  container: {
    padding: 30,
    marginTop: 65,
    alignItems: 'center'
  },
 thumb: {
    width: 80,
    height: 80,
    marginRight: 10
  },
  textContainer: {
    flex: 1
  },
  separator: {
    height: 1,
    backgroundColor: '#dddddd'
  },
  votes: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#656565'
  },
  title: {
    fontSize: 22,
    color: '#F9F9F9',
    backgroundColor: '#5AE14B'
  },
  rowContainer: {
    flexDirection: 'row',
    padding: 10
  },
});
