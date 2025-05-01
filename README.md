# Drug Search and Tracker

A modern Android application for searching and tracking medications, built with Kotlin and following MVVM architecture.

## Features

### Authentication
- Secure user authentication using Firebase Authentication
- Email and password-based signup and login
- User session management
- Secure logout functionality

### Medication Search
- Real-time medication search using RxNav API
- Search results display with medication details:
  - Medication name
  - Generic name
  - RxCUI (RxNorm Concept Unique Identifier)
  - Term Type (TTY)
- Filtered results for specific medication types (SBD)

### Medication Management
- Add medications to personal list
- View list of tracked medications
- Delete medications from the list
- Medications are user-specific and private
- Automatic timestamp tracking for added medications

### User Interface
- Modern Material Design UI
- Responsive layouts
- Loading states and error handling
- Empty state handling
- Smooth navigation with animations
- Keyboard-aware scrolling

### Data Management
- Local storage using Room database
- User-specific data isolation
- Automatic data synchronization
- Offline capability for tracked medications

## Technical Stack

### Architecture
- MVVM (Model-View-ViewModel) architecture
- Clean Architecture principles
- Repository pattern
- Dependency Injection with Hilt

### Libraries
- AndroidX
- Room for local database
- Firebase Authentication
- Kotlin Coroutines and Flow
- Material Design Components
- Navigation Component
- ViewBinding

### Data Layer
- Local: Room Database
- Remote: RxNav API
- Authentication: Firebase Auth

## Security Features
- User authentication required for all operations
- Data isolation between users
- Secure credential management
- Protected API calls

## Getting Started

### Prerequisites
- Android Studio (latest version)
- Kotlin 1.8+
- Android SDK 21+
- Firebase project setup

### Installation
1. Clone the repository
2. Open the project in Android Studio
3. Add your Firebase configuration file
4. Build and run the app

## Project Structure
```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.drugsearchandtracker/
│   │   │       ├── data/
│   │   │       │   ├── local/      # Room database and entities
│   │   │       │   └── remote/     # API calls and models
│   │   │       ├── domain/         # Business logic and use cases
│   │   │       ├── presentation/   # ViewModels and UI states
│   │   │       └── ui/             # Activities, Fragments, and UI components
│   │   └── res/                    # Resources and layouts
```

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- RxNav API for medication data
- Firebase for authentication
- Android Jetpack components
- Material Design guidelines 
