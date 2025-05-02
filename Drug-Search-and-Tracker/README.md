# Drug Search and Tracker

A modern Android application that allows users to search for drugs, view detailed information, and track their medication usage. The app integrates with the National Library of Medicine's RxNorm APIs for drug data.

## Features

### Authentication
- Email and password authentication using Firebase Authentication
- Secure login and signup flow
- Session management

### Drug Search
- Real-time search using RxNorm API
- Displays drug name and RxCUI ID
- Shows top 10 results for each search
- Clean and intuitive search interface

### Medication Management
- Add medications to personal list
- View detailed medication information
- Delete medications from the list
- Limit of 3 medications per user
- Local storage using SQLite

### User Interface
- Modern Material Design implementation
- Responsive and adaptive layouts
- Smooth navigation between screens
- Loading states and error handling
- Empty state handling

## Technical Implementation

### Architecture
- MVVM (Model-View-ViewModel) architecture
- Clean Architecture principles
- Dependency Injection using Hilt
- Repository pattern for data management

### Technologies Used
- Kotlin
- Android Jetpack Components
  - ViewModel
  - Flow
  - Navigation Component
  - Room Database
- Firebase Authentication
- RxNorm API Integration
- SQLite for local storage
- Material Design Components


## Requirements Covered

- [x] Authentication using Firebase
- [x] Drug search using RxNorm API
- [x] Medication list management
- [x] Local storage using SQLite
- [x] User interface implementation
- [x] Error handling
- [x] State management
- [x] Navigation between screens

## Technical Specifications

- Java Version: 17
- Android SDK Target: API 35 (Android 15)
- Minimum SDK: API 29 (Android 10.0)
- Build Tools: Android Gradle Plugin 8.9.2
- Kotlin Version: 2.1.0

## Future Improvements

If given more time, I would implemented the following:

• Accessibility and screen reader support
• Animations
• Reminder: Add a Calendar Event for Medication


## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Set up Firebase project and add google-services.json
4. Build and run the application


## License

This project is licensed under the MIT License - see the LICENSE file for details. 