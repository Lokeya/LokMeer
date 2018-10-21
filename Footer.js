'use strict';
import React, { Component } from 'react'
import {
    StyleSheet,
    Image,
    View,
    TouchableHighlight,
    FlatList,
    Text,
    TouchableOpacity
} from 'react-native';


export default class Footer extends Component {
    constructor(props){
        super(props);
    }
    _myOrder = () => {

    } 
    _myKitchen = () => {

    }
    _myAccount = () => {

    }
    _myNotifications = () => {

    }
    _orderFood = () => {

    }
    render() {
        const activePage = this.props.activePage;
        let orderImage = require('./Resources/footer_order.png'), 
            myOrderImage = require('./Resources/footer_myorder.png'), 
                myNotificationsImage = require('./Resources/footer_notification.png'),
                    myKitchenImage = require('./Resources/footer_mykitchen.png'),
                        myAccountImage = require('./Resources/footer_account.png');

        switch (activePage) {
            case 'MenuListing':
                orderImage = require('./Resources/footer_order_enabled.png');
                break;
            case 'MyOrderListing':
                myOrderImage = require('./Resources/footer_myorder_enabled.png');
                break;
            case 'MyNotifications':
                myNotificationsImage = require('./Resources/footer_notification_enabled.png');
                break;
            case 'MyKitchen':
                myKitchenImage = require('./Resources/footer_mykitchen_enabled.png');
                break;
            case 'MyAccount':
                myAccountImage = require('./Resources/footer_account_enabled.png');
                break;
        }

        return (
            <View style={styles.footContainer}>
                <View>
                    <TouchableHighlight onPress={this._orderFood}>
                        <Image style={styles.footerImage} source={orderImage}/>
                    </TouchableHighlight>
                    <Text style={styles.footerText}>Order Food</Text>
                </View>
                <View>
                    <TouchableHighlight onPress={this._myOrder}>
                        <Image style={styles.footerImage} source={myOrderImage} />
                    </TouchableHighlight>
                    <Text style={styles.footerText}>My Orders</Text>
                </View>
                <View>
                    <TouchableHighlight onPress={this._myNotifications}>
                        <Image style={styles.footerImage} source={myNotificationsImage}></Image>
                    </TouchableHighlight>
                    <Text style={styles.footerText}>Notifications</Text>
                </View>
                <View>
                    <TouchableHighlight onPress={this._myKitchen}>
                        <Image style={styles.footerImage} source={myKitchenImage}></Image>
                    </TouchableHighlight>
                    <Text style={styles.footerText}>My Kitchen</Text>
                </View>
                <View>
                    <TouchableHighlight onPress={this._myAccount}>
                        <Image style={styles.footerImage} source={myAccountImage}></Image>
                    </TouchableHighlight>
                    <Text style={styles.footerText}>My Account</Text>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    footContainer: {
        flexDirection: 'row', 
        justifyContent: 'space-evenly', 
        backgroundColor: '#F3F3F3', 
        paddingTop: 2, 
        paddingBottom: 2
    },
    footerImage: {
        width: 50,
        height: 50
    },
    footerText: {
        fontSize: 8,
        justifyContent: 'center',
        alignItems: 'center',
        textAlign: 'center'
    }
});