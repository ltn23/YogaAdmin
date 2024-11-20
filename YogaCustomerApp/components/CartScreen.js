import React, { useState } from 'react';
import { StyleSheet, Text, View, FlatList, TouchableOpacity } from 'react-native';

const CartScreen = ({ navigation }) => {
  const [dataClass, setDataClass] = useState([
    {
      id: 1,
      name: 'class1',
      teacher: 'teacher1',
      date: '09/10/2024',
      comments: 'Class 1 comments',
    },
    {
      id: 2,
      name: 'class2',
      teacher: 'teacher2',
      date: '10/10/2024',
      comments: 'Class 2 comments',
    },
    {
      id: 3,
      name: 'class3',
      teacher: 'teacher3',
      date: '11/10/2024',
      comments: 'Class 3 comments',
    },
  ]);

  const renderClassItem = (item) => {
    return (
      <TouchableOpacity
        style={styles.card}
        onPress={() =>
          navigation.navigate('ClassDetails', {
            id: item.id,
            name: item.name,
            teacher: item.teacher,
            date: item.date,
            comments: item.comments,
          })
        }
      >
        <View>
          <Text style={styles.cardTitle}>{item.name}</Text>
          <Text style={styles.cardDetail}>Teacher: {item.teacher}</Text>
          <Text style={styles.cardDetail}>Date: {item.date}</Text>
          <Text style={styles.cardDetail}>Comments: {item.comments}</Text>
        </View>
      </TouchableOpacity>
    );
  };

  return (
    <View style={styles.container}>
      <FlatList
        data={dataClass}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => renderClassItem(item)}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  card: {
    backgroundColor: '#f9f9f9',
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 8,
    padding: 15,
    marginBottom: 10,
  },
  cardTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  cardDetail: {
    fontSize: 16,
    color: '#555',
    marginBottom: 3,
  },
});

export default CartScreen;
