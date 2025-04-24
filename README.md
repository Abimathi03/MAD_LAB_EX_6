# 📍 Android Location App

An Android application that fetches and displays the **latitude** and **longitude** of the device’s current location using the **Fused Location Provider API** from Google Play Services. This app demonstrates the use of location services in Android, with runtime permission handling and basic error handling.

---

## 🚀 Features

- **Location Permissions**: Requests and checks for location permissions at runtime (for devices running Android 6.0 (API level 23) and higher).
- **Fetch Current Location**: Retrieves the device’s **latitude** and **longitude** using the **Fused Location Provider**.
- **Error Handling**: Handles scenarios where location is not available, permission is denied, or GPS is disabled.
- **Simple UI**: Displays the current latitude and longitude on a basic UI screen.
- **Lightweight**: Designed to be a simple, easy-to-understand example of how to use location services in Android.

---

## 🖼️ Screenshots

Here are some screenshots showing the **Android Location App** in action:

![WhatsApp Image 2025-04-24 at 8 05 39 PM](https://github.com/user-attachments/assets/b47c22a9-3663-400a-8e7b-76e858c0b781)

---

## 🛠️ Technologies Used

This app is built using the following technologies and tools:

- **Android SDK**: The core software development kit for Android apps.
- **Kotlin**: The programming language used to build the app.
- **FusedLocationProviderClient**: Part of the **Google Play Services Location API** that provides a simplified interface for obtaining the device's location.
- **Android Permissions**: Runtime permissions for location access (`ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION`).
- **Google Play Services**: For accessing location services.

---

## 📋 Prerequisites

Before running the app locally, you will need the following:

- **Android Studio** (latest stable version).
- **Java Development Kit (JDK)** installed on your machine.
- **Android device or emulator** to test the application.
- An **active internet connection** (for downloading dependencies and running on a physical device).
