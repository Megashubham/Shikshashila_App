# Shikshashila Native Android App 🚀

Welcome to the native Android application for the **DPS School Management System**! 
This project has been fully custom-built in **Kotlin** and **Jetpack Compose**, connecting seamlessly to your existing PHP backend.

---

## 🏗 Project Structure

The project is divided into two main layers:

### 1. Android App (`/android`)
*   **100% Native:** Built with modern Android tools, not a WebView or PWA.
*   **Architecture:** MVVM (Model-View-ViewModel).
*   **Dependency Injection:** Dagger Hilt (`di/` package).
*   **Network Layer:** Retrofit + OkHttp (`data/api/`).
*   **Offline Caching:** Room Database (`data/local/`).
*   **UI Toolkit:** Jetpack Compose (`ui/` package).

### 2. PHP API Endpoints (`/api` in your `school_vel` folder)
We added custom API endpoints to your existing PHP server to allow the app to communicate with your database securely.
*   `api/login.php`
*   `api/student/` (Dashboard, Routine, Results, Homework)
*   `api/teacher/` (Dashboard, Classes, Attendance, Assignments)
*   `api/admin/` (Dashboard Stats, Fee Reports)

---

## 🛠 How to Run the App

1. **Open Android Studio:**
   * Open Android Studio on your Mac.
   * Click **Open** and select the `/Users/sunnysharma/Shikshashila_App/android` folder.
   * Let Gradle sync and download all dependencies (this may take a few minutes).

2. **Configure the Base URL:**
   * If you want to test on the **Android Emulator**, it cannot connect to `localhost` directly.
   * Open `android/app/src/main/java/com/shikshashila/app/util/Constants.kt`.
   * Change `BASE_URL` to `"http://10.0.2.2/school_vel/api/"` (10.0.2.2 is how the emulator talks to your Mac's localhost).
   * If testing on a **physical device** over Wi-Fi, change `BASE_URL` to your Mac's local IP address (e.g., `"http://192.168.1.5/school_vel/api/"`).
   * For the **Live Site**, change it to `"https://erp.shikshashila.com/api/"`.

3. **Build and Run:**
   * Click the green **Play (Run)** button at the top of Android Studio.

---

## 🏆 Key Features Implemented

*   ✅ **Role-Based Navigation:** Automatically routes Students (Role 4), Teachers (Role 3), and Admins (Roles 1 & 2) to their respective dashboards upon login.
*   ✅ **Offline Mode:** The app uses Room Database to cache all API responses. Users can view their routines and dashboards even without internet!
*   ✅ **Teacher Module:** Native attendance taking using fast toggle switches instead of slow web forms.
*   ✅ **Admin Module:** Native tracking of Fee collections and Staff/Student statistics.

## 🔒 Security Notes
This folder was created outside of your main GitHub repository to ensure your app source code remains completely private, as per your request!
