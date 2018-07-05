import React, { Component } from 'react';
import {
    View,
    Text,
    AppRegistry
} from 'react-native';

class HelloWorld extends Component {
    render() {
        return (
            <View>
                <Text>
                    HelloWorld
                </Text>
            </View>
        );
    }
}

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);