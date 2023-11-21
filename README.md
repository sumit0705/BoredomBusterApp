# BoredomBusterApp
The app will fetch activities from the Bored API (https://www.boredapi.com/api/activity) when clicking on a button. 
If the internet is connected, the app should make the API call and if offline, it should retrieve the last response saved in local storage on clicking on the fetch button.

Steps to run the application:

1. Open the code in Android Studio (recommended Android Studio Giraffe | 2022.3.1 Patch 4).
2. Open the Device Manager and Launch the AVD(Android Virtual device) or create a new device if not present (recommended Android API 31 i.e. Android 12 and minimum being API 24 - Android 7.0 Nougat)
3. Build and run the app in Android Studio.

Screenshot of the app: https://drive.google.com/file/d/17s7q29d7xcHzcKkwsfqICQTxUhnh2Ch-/view?usp=sharing
Screen recording of the app: https://drive.google.com/file/d/1vGoIMJXL9b70kymzAaeStQpn_xpN_pnJ

Basic features of the app:

1. Clean Architecture Implementation:
    - Used MVVM architecture along with repository and several other classes for better separation of concerns.
    - Used Retrofit libarary to make the API call.
    - Used SharedPreference for storing latest activity in database.

2. Internet Connectivity Check:
    - Shows a Snackbar whenever the internet connectivity status changes.
    - It displays "Internet Connected" when connected and "Internet Disconnected" when disconnected.

3. Local Storage:
    - Implemented local storage via SharedPreference to save the last API response.
    - When the app is offline, it retrieves the last saved response from local storage on clicking on the fetch button.

4. Comments:
    - Added comments explaining each major step of implementation.
    - Included comments for key classes, methods, and any complex logic to ensure easy understanding for future maintenance.

5. Error Handling:
    - Implemented error handling for network calls and local storage operations.
    - Used try/catch block , null safety operator and null statements.
    - Provided appropriate error messages and displayed it to the user.

6. Service Classes:
    - Created separate service classes for API calls and local storage operations in the Core layer.
    - This will ensure a modular and maintainable structure for handling external dependencies while following SOLID principles.

7. Beautiful UI:
    - Created an aesthetically pleasing user interface that enhances the user experience.
