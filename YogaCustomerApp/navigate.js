import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';


import HomeScreen from './components/HomeScreen';
import CartScreen from './components/CartScreen';
import ClassDetailsScreen from './components/ClassDetailsScreen';
const Stack = createStackNavigator();

const AppNavigation = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen
                    name="Home Screen"
                    component={HomeScreen}
                />
                <Stack.Screen
                    name="Cart Screen"
                    component={CartScreen}
                />         
                 <Stack.Screen
                    name="ClassDetailsScreen"
                    component={ClassDetailsScreen}
                />
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default AppNavigation;