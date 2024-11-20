import { initializeApp } from 'firebase/app';
import { getDatabase } from 'firebase/database';

const firebaseConfig = {
    apiKey: "AIzaSyBD1BOiRFaoz_dRhXEJ_qQuYX8bfu9v67Q",
    authDomain: "yogaadminapp-f41e8.firebaseapp.com", 
    databaseURL: "https://yogaadminapp-f41e8-default-rtdb.europe-west1.firebasedatabase.app", 
    projectId: "yogaadminapp-f41e8",
    storageBucket: "yogaadminapp-f41e8.firebasestorage.app",
    messagingSenderId: "641764453522",
    appId: "1:641764453522:android:4452d144633cb966c60a4b",
  };

// Khởi tạo Firebase App
const app = initializeApp(firebaseConfig);

// Khởi tạo Realtime Database
const database = getDatabase(app);

export { database };
