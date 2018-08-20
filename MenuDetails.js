'use strict';

import React, { Component } from 'react'
import {
  StyleSheet,
  Image,
  View,
  TouchableHighlight,
  FlatList,
  Text,
  ActivityIndicator,
  Linking,
} from 'react-native';
  
const zipcode = '10016';

function urlForQueryAndPage(key, value, pageNumber) {
  const data = {
      zip: zipcode,
      imageSize: 'Md',
      imageText: 'true',
      page: pageNumber,
      api_key: 'hkmmsu8mgj8te8hutxbff6nc',
  };

  data[key] = value;

  const querystring = Object.keys(data)
    .map(key => key + '=' + encodeURIComponent(data[key]))
    .join('&');

  return 'https://data.tmsapi.com/v1.1/movies/showings?' + querystring;
}

function formatISODate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function formatDate(date,short) {
    var d = new Date (date),
        
      year = d.getFullYear(),
      month = d.getMonth()+1,
      day = d.getDate(),
      min = d.getMinutes(),
      hour = d.getUTCHours();

      if (day   < 10) {day = '0' + day;}
      if (month < 10) {month = '0' + month;}
      if (min < 10) {min = '0' + min;}
      if (hour < 12) {min =  min + ' am';}
      if (hour > 12) {
          hour = hour -12;
          min =  min + ' pm';
      }
      if (hour ==12) {min = min + ' pm';}
      var formattedDate;
      if (short ==0) {formattedDate= month + '/' + day + '/' + year + ' ' + hour + ':' + min;}
      else {formattedDate=month + '/' + day + '/' + year;}
      
      return formattedDate;
}

export default class MovieDetails extends Component<{}> {
 constructor(props) {
  super(props);
  this.state = {
    isLoading: false,
    message: '',
    locations: '',
    rating: '',
    cast: '',
    };
    
  }

  componentDidMount() {
    const query = urlForQueryAndPage('startDate', formatISODate(new Date()), 1); 
    this._executeQuery(query);
    console.log('GrandChild did mount.');
  }

render() {
    var item = this.props.movie;
    const spinner = this.state.isLoading ? <ActivityIndicator size='large'/> : null;
    
    return (
          <View>
          <View style={styles.rowContainer}>
            <Image style={styles.thumb} source={{ uri: 'https://image.tmdb.org/t/p/w300' + item.poster_path }} /> 
            <View style={styles.textContainer}>
              <Text style={styles.title}
                numberOfLines={1}>{item.title}</Text>
                 <Text style={styles.votes}>Reviews: {item.vote_average} ({item.vote_count} votes)</Text>
                 <Text style={styles.votes}>Release Date: {formatDate(item.release_date,1)}</Text>
                 <Text style={styles.votes}>Rating: {this.state.rating} </Text>
                 <Text style={styles.votes}>Cast: {this.state.cast} </Text>
                 <Text style={{color: 'blue', marginTop: 10, }}
                    onPress={() => Linking.openURL('https://www.perksatwork.com/movies/index/uSource/TXTST')}>
                    Buy Tickets
                </Text>
            </View>
           </View>
          <View style={styles.textContainer}><Text style={styles.votes} numberOfLines={5}>OVERVIEW: {item.overview}</Text></View>
          {spinner}           
          <Text style={styles.locations}>{this.state.locations}</Text>       
        </View>
    );
  }

 _executeQuery = (query) => {
  console.log("second query:" + query);
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

  _handleResponse = (response) => {
    this.setState({ isLoading: false , message: '' });
    console.log('Gracenote Count: ' +response.length);
    console.log('First location: ' +response[0].showtimes[0].theatre.name); 
    
    var item = this.props.movie;

    for (var i=0; i<response.length; i++)
      if (response[i].title == item.title) 
      {
        console.log("Match: " + response[i].rootId + "," + response[i].title);
        var showtimes ='SHOWTIMES near: ' + zipcode + '\n';
        for (var j=0; j<response[i].showtimes.length; j++)
        {
        var moviedate = new Date (response[i].showtimes[j].dateTime);
        var formattedDate = formatDate(moviedate,0);
        
        if (j>0) {
          if (response[i].showtimes[j].theatre.name != response[i].showtimes[j-1].theatre.name) {showtimes=showtimes + "\n"}           

        }
        showtimes=showtimes+ eval(j+1) + ". " +response[i].showtimes[j].theatre.name + ": " + formattedDate + "\n";
        }
        this.setState({locations: showtimes});
        this.setState({rating: response[i].ratings[0].code});
        this.setState({cast: response[i].topCast.toString()});
       }
  };
}

const styles = StyleSheet.create({
  description: {
    marginBottom: 20,
    fontSize: 24,
    textAlign: 'center',
    color: '#656565'
  },
  locations: {
    marginTop: 100,
    marginLeft:10,
    fontSize: 14,
    textAlign: 'left',
    color: '#656565'
  },
  container: {
    padding: 30,
    marginTop: 65,
    alignItems: 'center'
  },
 thumb: {
    width: 180,
    height: 240,
    marginRight: 10,
    marginTop: 100
  },
  textContainer: {
    flex: 1,
    marginLeft:10,
    marginRight:10
  },
  separator: {
    height: 1,
    backgroundColor: '#dddddd'
  },
  votes: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#656565',
    marginTop: 7
  },
  title: {
    fontSize: 22,
    color: '#656565',
    marginTop: 100
  },
  rowContainer: {
    flexDirection: 'row',
    padding: 10
  },
});
