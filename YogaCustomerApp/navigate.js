import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import Icon from 'react-native-vector-icons/Ionicons';

// Import screens
import HomeScreen from './components/HomeScreen';
import CartScreen from './components/CartScreen';
import ClassDetailsScreen from './components/ClassDetailsScreen';

// Create navigators
const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

// Stack Navigator for Home
const HomeStack = () => {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Home Screen" component={HomeScreen} />
      <Stack.Screen name="ClassDetailsScreen" component={ClassDetailsScreen} />
    </Stack.Navigator>
  );
};

// Stack Navigator for Cart
const CartStack = () => {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Cart Screen" component={CartScreen} />
      <Stack.Screen name="ClassDetailsScreen" component={ClassDetailsScreen} />
    </Stack.Navigator>
  );
};

// Main App Navigation
const AppNavigation = () => {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarIcon: ({ color, size }) => {
            let iconName;

            if (route.name === 'Home') {
              iconName = 'home-outline';
            } else if (route.name === 'Cart') {
              iconName = 'cart-outline';
            }

            return <Icon name={iconName} size={size} color={color} />;
          },
          tabBarActiveTintColor: '#007bff',
          tabBarInactiveTintColor: 'gray',
        })}
      >
        <Tab.Screen
          name="Home"
          component={HomeStack}
          options={{ headerShown: false }}
        />
        <Tab.Screen
          name="Cart"
          component={CartStack}
          options={{ headerShown: false }}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
};

export default AppNavigation;
