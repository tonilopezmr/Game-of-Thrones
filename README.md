# Game of Thrones for Android [![Build Status](https://travis-ci.org/tonilopezmr/Game-of-Thrones.svg?branch=master)](https://travis-ci.org/tonilopezmr/Game-of-Thrones)


Cloned from [https://github.com/idealista-tech/got-challenge-for-android](https://github.com/idealista-tech/got-challenge-for-android) and refactored.

![gif]

I have participated in challenge and I will continue improving!

You can follow the commits flow to see how I refactoring.

Android developers face challenges almost everyday during development: performance, security, backwards compatibility, testing... And mainly refactoring for it's own or legacy code.
This repository contains a project to face an small challenge where the developer should add some new features, detect (and implement) patterns, add tests, re-think the architecture and do a clean code.

Game of Thrones for Android Challenge offers an app using an API to get data for [Game of Thrones][GameOfThronesLink] tv show. It's ready to run, it's working, but the code need to be improved. That's your challenge!

## Getting started

This repository contains an Android app that retrieve a list of some [Game of Thrones][GameOfThronesLink]' characters from an API. The app shows a list of the houses of the characters, the characters themselves and a detail description of each one.

This behaviour it's done in two different Activities, one for the two lists and other for details of the character:

![ScreenshotListCharacters][ScreenshotListCharacters]![ScreenshotListHouses][ScreenshotListHouses]![ScreenshotDetail][ScreenshotDetail]
* ``HomeActivity`` contains two Fragments in a ViewPager
  * `CharacterListFragment` shows a list of some the this tv show's characters.
  * `HouseListFragment` shows a list of the noble houses of the characters

* ``DetailActivity`` shows the name, photo and description of a character

## Goals

1. **Refactoring and practice TDD**
2. **Clean Architecture with MVP**
3. **Package by feature, not layer**
4. **Reactive with RxJava1**
5. **Play with Dagger2**
6. **Capability to work offline**
7. **Testing with Espresso and Mockito**
8. **Animations**
9. **Ui Chat** 

Thanks to [Guillermo López][0] for giving me the GoT Icons

# License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[ScreenshotListCharacters]: ./art/ScreenshotListCharacters.png
[ScreenshotListHouses]: ./art/ScreenshotListHouses.png
[ScreenshotDetail]: ./art/ScreenshotDetail.png
[GameOfThronesLink]: http://www.imdb.com/title/tt0944947/
[gif]: ./art/gotgif.gif
[0]: https://github.com/lopermo