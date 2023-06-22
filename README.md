# StoryApp
This is an exercise repository for Android application development using Kotlin. 


## About GitHubUserSearch
StoryApp is an android application to post a picture, put picture description, and put the location when user upload it. There will be authentication page (login and register), Map Activity, RecyclerView to list stories, language settings (English and Indonesian). This project is using MVVM design pattern, and fetching data from a RestAPI. I also make an UnitTest for login and logout, and addingStory for this android project.


## UI 
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/ad3696ee-7f26-4aca-9c75-a018e75313f1" alt="SplashScreen" width="200" margin=""/>
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/a16e1c42-a2d4-4ccf-bc1d-3c447fdc5231" alt="Login" width="200" margin=""/>
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/59b98833-6aa5-46b2-ba28-779f76846968" alt="Register" width="200" />
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/445238da-59d3-4f66-9ccf-245317a4f241" alt="Home" width="200" />
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/d19384cc-3424-4512-886c-64edf11b6a23" alt="Story Detail" width="200" margin=""/>
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/7f86ed05-c48a-4272-a4f9-1d77f0566000" alt="Map Acticity" width="200" />
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/eacbbd6a-bc48-4a9e-b854-d2e2a4c1e864" alt="Add New Story" width="200" />
  <img src="https://github.com/drewjd27/StoryApp_AndroidKotlin/assets/26517220/1f7b8c19-53c5-4879-8ac1-f01c457a57a3" alt="Language Setting" width="200" />
</div>


## Feature
- Camera Intent
- Gallery Intent
- Share Location


## Tech Stack
- Kotlin programming language
- [Retrofit2](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview)
- [Lottie](https://github.com/airbnb/lottie-android)
- CustomView
- Paging3
- RemoteMediator
- GoogleMaps API


## RestAPI
[Dicoding Story RestAPI](https://story-api.dicoding.dev/v1/#/)


## Project Insalation
1. Clone the repository


    ```bash
    git clone https://github.com/drewjd27/StoryApp_AndroidKotlin.git
    cd StoryApp
    ```

2. Add your API_TOKEN in build.gradle(Module:app)

   ```bash
    <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="INPUT_YOUR_API_KEY_HERE" />
    ```

   
3. Run the app in Android Studio from emulator or physical device.
