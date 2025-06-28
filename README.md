# TaskApp - Android Task Manager App

TaskApp is an Android application developed as part of a final college assignment. It allows users to manage their personal tasks by adding, completing, and organizing them using Firebase as the backend.

---

## Features

- User authentication using Firebase Authentication (Login and Registration)
- Add tasks with title, description, and due date
- Mark tasks as complete or move them back to active
- View active tasks in the Home tab
- View completed tasks in the Profile tab
- Delete tasks with confirmation prompt
- Dark mode enabled by default
- Real-time data sync using Cloud Firestore
- Clean UI built with Material Design 3

---

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern and uses:

- ViewModel and LiveData for reactive data handling
- Firebase Authentication for secure user login
- Cloud Firestore for real-time NoSQL database operations
- Navigation Component with NavHostFragment and BottomNavigationView
- Material Components for consistent styling and UI behavior
- ViewBinding for safe and efficient view access

---

## Tech Stack

- Java (Android SDK)
- Firebase Authentication
- Firebase Firestore
- Android Jetpack (ViewModel, LiveData, Navigation)
- Material Components

---

## Folder Structure

com.example.taskapp/
├── model/ # Data models (e.g., Task.java)
├── repository/ # Business logic and Firestore integration
├── ui/ # Fragments and Activities (Login, Home, AddTask, Profile)
├── viewmodel/ # ViewModels used to separate logic from UI


---

## Required Dependencies

- implementation 'com.google.firebase:firebase-auth'
- implementation 'com.google.firebase:firebase-firestore'
- implementation 'com.google.android.material:material'
- implementation 'androidx.navigation:navigation-fragment'
- implementation 'androidx.navigation:navigation-ui'
- implementation 'androidx.lifecycle:lifecycle-viewmodel'


---

## Developer Credit

- Ariel Hallewa
- GitHub Profile: [GitHub Profile](https://github.com/ariel04h)