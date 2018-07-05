import React, { Component } from 'react';
import {
    View,
    Text,
    Button,
    TouchableOpacity,
    AppRegistry,
    StyleSheet,
    NativeModules,
    ToastAndroid
} from 'react-native';

class HelloWorld extends Component {

    callNatShowToast() {
        NativeModules.mHelloWorldModule.showToast('红鲤鱼与绿鲤鱼与驴');
     }

     callNatSumCallback() {
        NativeModules.mHelloWorldModule.sumCallback(12, 22, (result) => {
            ToastAndroid.show(`rn:${result}`, ToastAndroid.SHORT);
       })
     }

     callNatSumPromise() {
        NativeModules.mHelloWorldModule.sumPromise(99, 1)
            .then((result) =>{
                ToastAndroid.show(`promise:${result}`, ToastAndroid.SHORT)
            })
            .catch((error) =>{
                console.log(error)
            });
     }

    render() {
        return (
            <View style={{flex: 1, backgroundColor: '#eee'}}>
                <TouchableOpacity 
                    onPress={this.callNatShowToast}
                    activeOpacity={0.8} 
                    style={{padding: 10,backgroundColor: '#fff'}}>
                    <Text style={[styles.button, styles.primary]}>
                        调用Native方法
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity 
                    onPress={this.callNatSumCallback}
                    activeOpacity={0.8} 
                    style={{padding: 10,backgroundColor: '#fff'}}>
                    <Text style={[styles.button, styles.danger]}>
                        调用Native方法(callback)
                    </Text>
                </TouchableOpacity>


                <TouchableOpacity 
                    onPress={this.callNatSumPromise}
                    activeOpacity={0.8} 
                    style={{padding: 10,backgroundColor: '#fff'}}>
                    <Text style={[styles.button, styles.warning]}>
                        调用Native方法(promise)
                    </Text>
                </TouchableOpacity>
            </View>
        );
    }
}

var styles = StyleSheet.create({
    button: {
        height: 45,
        lineHeight: 45,
        fontSize: 16,
        color: '#fff',
        backgroundColor: '#333',
        textAlign: 'center',
    },
    danger: {
        backgroundColor: '#D95040',
    },
    warning: {
        backgroundColor: '#F2BD42',
    },
    default: {
        backgroundColor: '#F2F2F2',
    },
    primary: {
        backgroundColor: '#4F87EC',
    }
})

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);