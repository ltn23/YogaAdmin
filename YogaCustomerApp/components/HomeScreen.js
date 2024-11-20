import React, { useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  TouchableOpacity,
} from "react-native";
import { database } from "../firebaseConfig"; // Import your Firebase config
import { ref, onValue } from "firebase/database";

const HomeScreen = ({ navigation }) => {

  const [dataClass, setDataClass] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Reference to your database path
    const classRef = ref(database, "classes");

    // Fetch data from Firebase Realtime Database
    const unsubscribe = onValue(
      classRef,
      (snapshot) => {
        const data = snapshot.val();
        if (data) {
          // Convert object to an array and set to state
          const classArray = Object.keys(data).map((key) => ({
            id: key,
            ...data[key],
          }));
          setDataClass(classArray);
        }
        setLoading(false); // Stop loading
      },
      (error) => {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    );

    // Cleanup subscription on unmount
    return () => unsubscribe();
  }, []);
  const renderClassItem = ({ item }) => {
    return (
      <View style={styles.card}>
        <View style={styles.row}>
          <Text style={styles.cardTitle}>{item.name}</Text>
          <TouchableOpacity
            style={styles.button}
            onPress={() =>
              navigation.navigate("ClassDetailsScreen", {
                id: item.id,
                name: item.name,
                teacher: item.teacher,
                date: item.date,
                comments: item.comments,
              })
            }
          >
            <Text style={styles.buttonText}>More</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Classes Information</Text>
      <FlatList
        data={dataClass}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderClassItem}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#fff",
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
  },
  card: {
    backgroundColor: "#f1f1f1",
    borderRadius: 8,
    paddingVertical: 15,
    paddingHorizontal: 20,
    marginBottom: 10,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    borderWidth: 1,
    borderColor: "#ccc",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "100%",
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: "500",
    flex: 1, // Ensures text occupies available space
    color: "#333",
  },
  button: {
    backgroundColor: "#28a745", // Green color as shown in the image
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    color: "#fff",
    fontSize: 14,
    fontWeight: "bold",
  },
});

export default HomeScreen;
