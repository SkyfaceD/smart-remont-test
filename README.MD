[![wakatime](https://wakatime.com/badge/user/1a9f3ef6-846f-49d3-9216-c03b6d661710/project/d413ef3d-9878-42fe-8a33-f546fcb01893.svg)](https://wakatime.com/badge/user/1a9f3ef6-846f-49d3-9216-c03b6d661710/project/d413ef3d-9878-42fe-8a33-f546fcb01893)

## Download

Debug|Release
---|---
with leakcanary|with minify and shrink

Apks can be found in github [release](https://github.com/SkyfaceD/smart-remont-test/releases/) section.

You can install both versions on one device.

## Known issues
- Auto-login feature don't supported. Idk how to use refresh token, documentation doesn't explain it or I'm missing something :/

## Stack

- [Kotlin](https://kotlinlang.org/) - main language
- [Coil](https://github.com/coil-kt/coil) - an image loading library for Android backed by Kotlin
  Coroutines
- [Square Logcat](https://github.com/square/logcat) - a tiny Kotlin API for cheap logging on top of Android's normal `Log` class.
- [LeakCanary](https://github.com/square/leakcanary) - a memory leak detection library for Android
- [Material Components](https://material.io/) - an adaptable system of guidelines, components, and
  tools that support the best practices of user interface design
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - consists of a compiler
  plugin, that generates visitor code for serializable classes, runtime library with core
  serialization API and support libraries with various serialization formats
- [Koin](https://insert-koin.io/) - a smart Kotlin injection library to keep you focused on your
  app, not on your tools
- [Startup](https://developer.android.com/topic/libraries/app-startup) - provides a straightforward,
  performant way to initialize components at application startup
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://developer.android.com/kotlin/flow) - asynchronous world
- [Retrofit2](https://github.com/square/retrofit) + [OkHttp3](https://github.com/square/okhttp) - a
  type-safe HTTP client for Android and Java
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - perform actions
  in response to a change in the lifecycle status of another component, such as activities and
  fragments
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - class is
  designed to store and manage UI-related data in a lifecycle conscious way
- [Modo](https://github.com/terrakok/Modo) - Modo is navigation library based on UDF principles for developing Single Activity applications

## Screenshots

<p align="center">
<img src="/.screenshots/start.png"  width="260" title="Стартовый экран">&emsp;
</p>
<p align="center">
<img src="/.screenshots/sign-in-one.png" width="260" title="Регистрация">&emsp;<img src="/.screenshots/sign-in-two.png" width="260" title="Регистрация">&emsp;<img src="/.screenshots/sign-in-three.png" width="260" title="Регистрация">&emsp;<img src="/.screenshots/shop-one.png" width="260" title="Магазины">&emsp;<img src="/.screenshots/shop-two.png" width="260" title="Магазины">&emsp;<img src="/.screenshots/details-one.png" width="260" title="Детали магазина">&emsp;<img src="/.screenshots/web.png" width="260" title="Веб">&emsp;<img src="/.screenshots/gallery.png" width="260" title="Галлерея">
</p>

## Try it out

### Requirements

- Android SDK 21+
- Gradle 7.0.2+
- Java 11+

### Launch

- Clone repository (wrapper included):
    - via ssh `git@github.com:SkyfaceD/smart-remont-test.git`;
    - via https `https://github.com/SkyfaceD/smart-remont-test.git`;
- Build - `gradlew clean build`
- Run

## License

[License](https://github.com/SkyfaceD/smart-remont-test/blob/main/LICENSE) can be found in root directory of
repository
