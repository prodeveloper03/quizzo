# quizzo

# ---------------- Screens -----------
### 🔹 Splash Screen
-  For lazy loading and a better UX  
-  Uses a Lottie animation in the splash screen
  
### 🔹 Splash Screen
- Animated transition from splash screen
- Start Button, to start the Quiz
- In case before loading the API, the internet is  switched off or the API gives an error, we have handled it with an error dialogue
- ### 🔹 Error Dialog
-   Try again button, which will call the api again
-   A close icon which will take you to the Home Screen with the start Quiz button disabled


### 🔹 Quiz Screen
-  Questions along with their options
-  Once the user selects the correct answer, the correct answer option turns green
-  Once the user selects the wrong option, we mark the correct answer with green and the wrong answer with red
-  Once selected, moves to the next question after 2 seconds
-  In case the user doesn't want to answer the question, a skip button is provided
-  Maintaining a linear progress bar so that the user is aware of the no of questions currently at
-  ### 🔹 Streak🚀
-  Showed the 🔥 icon on the top once the user starts giving the  correct answer
-  In case the user gives a  wrong answer, the streak becomes "0"
-  Once the user hits 3,5,7 consecutive streaks, the Lottie animation on the whole screen shows up - to make the user engaged
-  Below the streak 🔥, we are also showing the streak to the user no and dynamic messages
-   ### 🔹 Navigation
-   If the user does a swipe left gesture, then we show a dialogue "Do you want to exit?"
-   If the user presses "NO", then the user will resume the quiz
-   If the user presses "YES", we will take the user to the "Home Screen"


### 🔹 Result Screen
- A message to the user
- A correct answer card showing how many questions the user has answered correctly
- A highest streak card showing the user has maintained the highest streak
- A reset quiz button to reset the answers and start the quiz from the start
-  ### 🔹 Navigation
-  If the user clicks on the close icon, then we take the  user to the "Home Screen"
-  If the user does a swipe gesture, then we take the user to the "Home Screen"
-  If the user press the reset button then we take the user to the "Home Screen"

---------------------------------------------------------------------------------------------
### 🔹 Quizo App Demo
- APK : https://drive.google.com/file/d/1nhp8o3hrY7wpGCZdgv-pw1oJS3gt99z3/view?usp=sharing
- Video: https://drive.google.com/file/d/1vTYnXalXErFPlwGTxWDUE5fv6N1CVkCU/view?usp=sharing

---------------------------------------------------------------------------------------------
### 🔹 Tech Stack & tool & library
- Kotlin
- JetPack Compose
- Hilt for DI
- Retrofit for Network Call
- Compose Animation
- Live Data
- MVVM
- Lottie for Compose
- Repository Pattern
- Material Design Components






