# opemus

Kotlin Multiplatform Mobile Sample
This is a Kotlin Multiplatform Mobile (KMM) Project. It includes iOS and Android applications with a native UI and a module with code shared on iOS and Android.

Features
This sample demonstrates basic KMM features:

- Compose jetpack components used in android
- SwiftUI components used in iOS
- Using platform-specific APIs with the expect/actual mechanism (see Platform.kt)
- Tests for Shared Module (iosTest.kt, androidTest.kt)

How to use
- With the KMM plugin for Android Studio you can run, test, and debug shared code on both platforms without switching IDEs. Run and debug the application by selecting the corresponding configuration in the Run configuration menu. Run and debug shared module tests by pressing the gutter icon on a test class or method.

Related links
- Visit Kotlin Multiplatform Mobile Developer Portal to learn more about the technology
- Check out the Networking and data storage with KMM hands-on lab to learn how to create a mobile application for Android and iOS with a shared codebase with Ktor and SQLDelight.
