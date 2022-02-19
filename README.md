# CatFact 😺 Android app

A simple Android app displaying cat facts along with cat pictures. The app is compatible down to Android 5 Lollipop 🍭, API level 21. 
- Cat facts provided by [cat-facts API](https://alexwohlbruck.github.io/cat-facts/)
- Cat pictures provided by [TheCatApi](https://docs.thecatapi.com/)

## How to run the app
1. Install the latest version of [Android Studio](https://developer.android.com/studio), and make sure you have the latest SDK installed
2. Clone this repository
3. Open the project with Android Studio from the location you cloned the repository to
4. Create a `local.properties` file in the root of the project
5. Obtain an API key for the [TheCatApi](https://docs.thecatapi.com/) if you don't own one yet: [Instructions](https://thecatapi.com/signup) 
6. Add your API key for the [TheCatApi](https://docs.thecatapi.com/) to the `local.properties` file in the following way:
```
theCatApi.key=yourApiKey
```
7. Run the project

## Known issues / room for improvement
- The layout and iconography of the app could be optimized
- Error handling is implemented in a very basic way, not stating specific error reasons
- On Android versions requiring storage permissions the user needs to tap on the share / save buttons another time, if the permission was just granted
- Images are saved to the gallery when trying to share for simplicity reasons