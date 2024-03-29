# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Added release date on Movie Details Activity
* [x] Converted the app to dark mode with a black background and white text
* [x] Added a landscape layout for Movie Details Activity
    - Used horizontal movie backdrop instead of vertical movie poster
    - Rearranged the layout of the rating bar and release date
* [x] Made the overview scrollable in portrait and landscape mode in Movie Details Activity
* [x] Added a play button over movie posters and backdrops in Movie Details Activity to
      show that it could be clicked to watch the movie trailer


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='./VerticalWalkthrough.gif' title='Verical Walkthrough' width='' alt='Vertical Walkthrough' />
<img src='./HorizontalWalkthrough.gif' title='Horizontal Walkthrough' width='' alt='Horizontal Walkthrough' />

GIF created with [Kap](https://getkap.co/).

## Notes

Describe any challenges encountered while building the app:

I had difficulty with adding the YouTube trailers and uploading my project to github. Getting the videoId was confusing at first, but once I was able to figure that out my code was complete. However, I was still having trouble playing the video and that was due to YouTube not being updated and the emulator not being able to display the video. I then updated YouTube and switched to testing on my phone and everything worked perfectly. My trouble with uploading my project to github came from git making multiple branches and the gif files being too large. I did a lot of troubleshooting and was eventually able to make my gif files smaller and upload everything to the main branch and delete any extra branches.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 2021 Fatema Abidali Neemuchwala

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
