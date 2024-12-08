# Polar Shuttle App 🚍

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.8.0-orange.svg)
![Firebase](https://img.shields.io/badge/Firebase-Authentication%20%26%20Database-green.svg)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [For Students](#for-students)
  - [For Drivers](#for-drivers)
  - [Available Actions](#available-actions)
- [Future Enhancements](#future-enhancements)
- [Authors](#authors)
- [License](#license)

---

## Overview

**Polar Shuttle** is a mobile application designed to streamline Bowdoin College's campus shuttle service, providing an efficient, user-friendly experience for students and drivers alike. Operating from **6 PM to 2 AM**, the shuttle system is a vital service on campus, but its current manual process is prone to inefficiencies such as missed rides and long wait times. 

The Polar Shuttle app modernizes this system by offering:
- A centralized platform for students to request rides.
- A driver dashboard to manage and optimize pickups and drop-offs.
- Real-time tracking and notifications to keep users informed.

---

## Features

### For Students:
- **Secure Login**: Bowdoin-specific authentication using Firebase Authentication.
- **Ride Requests**: Specify pickup and drop-off locations directly through the app.
- **Notifications**: Receive alerts when the driver is en route.
- **Real-Time Tracking**: View the driver's location on a live map powered by Google Maps API.

### For Drivers:
- **Driver Dashboard**: Manage ride requests efficiently with a clear interface.
- **Multi-Ride Optimization**: Smart algorithms group pickups and drop-offs.
- **Route Mapping**: Use Google Maps for optimized navigation.
- **Shift Statistics**: End-of-shift summaries including completed rides.

### Security Features:
- **Firebase Authentication**: Ensures secure access for students and drivers.
- **Firebase Realtime Database**: Supports real-time updates for ride requests and tracking.

---

## Project Structure

```plaintext
PolarShuttle/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.polarshuttle/
│   │   │   │   │   ├── auth/       # Login and authentication logic
│   │   │   │   │   ├── driver/     # Driver dashboard logic
│   │   │   │   │   ├── student/    # Student ride request features
│   │   │   │   │   ├── utils/      # Shared utility classes
│   │   │   ├── res/                # UI resources (layouts, drawables, etc.)
│   │   │   ├── AndroidManifest.xml
├── firebase/
│   ├── firebase_rules.json         # Database security rules
│   ├── google-services.json        # Firebase configuration file
├── README.md                       # Project documentation
├── LICENSE                         # Project license
└── build.gradle                    # Project dependencies and build settings

```
## Getting Started 

### Prerequisites 
- Android Studio 4.1.3 or later
- Android SDK 30
- Kotlin 1.8.0 or higher 
- Firebase Account (to set up Authentication and Realtime Database)
- Google Maps API Key (for map and location services)

### Installation
1. Clone the repository:
```bash
    git clone https://github.com/mkang817415/PolarShuttle.git
    cd PolarShuttle
```
2. Open the project in Android Studio:
- Open the project directory in Android Studio.
- Sync the Gradle files and ensure dependencies are installed. 

3. Set up Firebase: 
- Place the `google-services.json` file in the `app/` directory.
- Enable Firebase Authentication and Realtime Database in your Firebase project. 

4. Build and Run the App: 
- Connect an Android device or set up an emulator
- Run the app from Android Studio

## Usage

### For Students: 
1. **Login**: Use your Bowdoin email to sign in.
2. **Request a Ride**: Enter your pickup and drop-off locations.
3. **Submit a ride**: Confirm your request and wait for the driver to arrive.
4. **Notifications**: Receive notifications when the driver is nearby. 

### For Drivers:
1. **Login**: Use your Bowdoin email to sign in.
2. **View Requests**: See incoming ride requests and accept them. 
3. **Manage Rides**: Use the dashboard to manage active rides. 
4. **Navigate**: Navigate with real-time route mapping. 

### Available Actions: 

#### For Students:
- Submit ride requests. 
- View real-time shuttle tracking. 
- Receive notifications for ride status.
- Cancel ride requests.

#### For Drivers:
- Accept or reject ride requests. 
- Group pickups and drop-offs for efficiency. 
- End shift and view statistics. 

## Future Enhancements
- Advanced Scheduling: Allow students to schedule rides in advance. 
- In-App Chat: Enable communication between students and drivers.
- Ride Analytics: Provide insights for campus administration. 


## Authors 
- [Mingi Kang](https://github.com/mkang817415) - Lead Developer - Class of 2026

## License 
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



