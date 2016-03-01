# Game of Thrones for Android Challenge
Android developers face challenges almost everyday during development: performance, security, backwards compatibility, testing... And mainly refactoring for it's own or legacy code. 
This repository contains a project to face an small challenge where the developer should add some new features, detect (and implement) patterns, add tests, re-think the architecture and do a clean code.

Game of Thrones for Android Challenge offers an app using an API to get data for [Game of Thrones][GameOfThronesLink] tv show. It's ready to run, it's working, but the code need to be improved. That's your challenge!

## Getting started

This repository contains an Android app that retrieve a list of some [Game of Thrones][GameOfThronesLink]' characters from an API. The app shows a list of the houses of the characters, the characters themselves and a detail description of each one.

This behaviour it's done in two diffent [Activities][ActivityLink], one for the two lists and other for details of the character:

![ScreenshotListCharacters][ScreenshotListCharacters]![ScreenshotListHouses][ScreenshotListHouses]![ScreenshotDetail][ScreenshotDetail]  
* ``HomeActivity`` contains two [Fragments][FragmentLink] in a [ViewPager][ViewPagerLink]
  * `GoTListFragment` shows a list of some the this tv show's characters.
  * `GoTHousesListFragment` shows a list of the noble houses of the characters 

* ``DetailActivity`` shows the name, photo and description of a character

## Tasks 

Your task as Android Developer is **clone** or **fork** this repository into one yours, **add some functionalities** and **refactor** the code before you give access to your repository.

**The code in this application it's ready to be imported into your Android Studio and ready to run it (and see it working) without any change**

###### New functionalities to add

1. Search characters by name in the characters list
2. Create a list of a characters by house, accessing to it by clicking a house image in the list of houses
3. Capability to work offline
4. Refactor the code

###### Some optional tasks to do:

1. Tests the main logic and a high level flows.
2. Add transitions between list and detail
3. Add parallax effect into detail page

###### Once you've finished
1. Notify by email to rrhh@idealista.com
2. Have a rest after this "beautiful" code

#License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[ScreenshotListCharacters]: ./art/ScreenshotListCharacters.png
[ScreenshotListHouses]: ./art/ScreenshotListHouses.png
[ScreenshotDetail]: ./art/ScreenshotDetail.png
[ActivityLink]: http://developer.android.com/intl/es/guide/components/activities.html
[FragmentLink]: http://developer.android.com/intl/es/guide/components/fragments.html
[GameOfThronesLink]: http://www.imdb.com/title/tt0944947/
[ViewPagerLink]: http://developer.android.com/intl/es/training/animation/screen-slide.html
[npatarino]: https://github.com/npatarino
