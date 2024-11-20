import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';

const ClassDetailsScreen = ({ route }) => {
  const { teacher, date, comments } = route.params;

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Class Details</Text>

      {/* Teacher Field */}
      <Text style={styles.label}>
        Teacher <Text style={styles.required}>*</Text>
      </Text>
      <TextInput
        style={styles.input}
        value={teacher}
        editable={false} // Read-only field
      />

      {/* Date Field */}
      <Text style={styles.label}>
        Date <Text style={styles.required}>*</Text>
      </Text>
      <TextInput
        style={styles.input}
        value={date}
        editable={false} // Read-only field
      />

      {/* Comments Field */}
      <Text style={styles.label}>Comments</Text>
      <TextInput
        style={[styles.input, styles.textArea]}
        value={comments}
        editable={false} // Read-only field
        multiline={true} // Allow multiline content
        textAlignVertical="top" // Align text to the top
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
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 30,
    textAlign: 'center',
  },
  label: {
    fontSize: 16,
    marginBottom: 5,
    fontWeight: '600',
    color: '#333',
  },
  required: {
    color: 'red',
  },
  input: {
    backgroundColor: '#f2f2f2',
    borderRadius: 8,
    padding: 10,
    fontSize: 16,
    color: '#555',
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#ddd',
  },
  textArea: {
    height: 100, // Height for multiline input
    textAlignVertical: 'top', // Align text to top-left corner
    padding: 10, // Padding to match the input style
  },
});

export default ClassDetailsScreen;
