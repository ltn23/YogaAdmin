import React, { useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  TouchableOpacity,
  TextInput,
} from "react-native";
import { database } from "../firebaseConfig";
import { ref, onValue } from "firebase/database";

const HomeScreen = ({ navigation }) => {
  const [dataClass, setDataClass] = useState([]);
  const [filteredClasses, setFilteredClasses] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const classRef = ref(database, "classes");

    const unsubscribe = onValue(
      classRef,
      (snapshot) => {
        const data = snapshot.val();
        if (data) {
          const classArray = Object.keys(data).map((key) => ({
            id: key,
            ...data[key],
          }));
          setDataClass(classArray);
          setFilteredClasses(classArray); // Initialize filtered list
        }
        setLoading(false);
      },
      (error) => {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    );

    return () => unsubscribe();
  }, []);

  const handleSearch = (text) => {
    setSearchQuery(text);
    if (text.trim() === "") {
      setFilteredClasses(dataClass); // Show all classes if search is empty
    } else {
      const lowercasedQuery = text.toLowerCase();
      const filtered = dataClass.filter(
        (item) =>
          item.date.toLowerCase().includes(lowercasedQuery) || // Match by date
          (item.time && item.time.toLowerCase().includes(lowercasedQuery)) // Match by time if available
      );
      setFilteredClasses(filtered);
    }
  };

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
                time: item.time, // Assuming time is available
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

      {/* Search Bar */}
      <TextInput
        style={styles.searchBar}
        placeholder="Search by day or time..."
        value={searchQuery}
        onChangeText={handleSearch}
      />

      <FlatList
        data={filteredClasses}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderClassItem}
        ListEmptyComponent={<Text style={styles.noResults}>No classes found.</Text>}
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
  searchBar: {
    backgroundColor: "#f2f2f2",
    borderRadius: 8,
    padding: 10,
    fontSize: 16,
    color: "#333",
    marginBottom: 20,
    borderWidth: 1,
    borderColor: "#ddd",
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
    flex: 1,
    color: "#333",
  },
  button: {
    backgroundColor: "#28a745",
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
  noResults: {
    textAlign: "center",
    marginTop: 20,
    color: "#999",
  },
});

export default HomeScreen;
