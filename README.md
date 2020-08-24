
Youtube App
---
This is a sample Android app. The main goal of this project is to provide a full implementation for a
No-Framework Dependency Injection in Kotlin. Also there are some new ideas about how to use coroutines in MVVM,
where you should see ;)

Technologies
---
The followings are the libraries and technologies that are used in this sample.
 - No-Framework Dependency Injection in Kotlin
 - Modular
 - Clean Architecture
 - Koltin Coroutines
 - AndroidX
 - Jetpack Paging 3
 - Jetpack Room
 - Constraint Layout
 - Coroutine Lifecycle Handler
 - Google API
 - Youtube API
 - Coil
 - Mockk
 - JUnit 5

Enjoy!

No-Framework Dependency Injection
---
The idea is to avoid using any code generation for dependency injection and use Kotlin potentials to
make our code scoped and concise. Despite that I'm a big fan of code generation, generating code for almost
every class in the code base is a wrong decision as your focus would be lost in the build times. So it looks
like the best practice is to keep the code generation as small part of the code as possible.

In case of dependency injection, the solution is to not using any frameworks, where we actually don't
need to! You can clone this project and try to add dependencies to see how Kotlin can help us to do it almost as
simple as adding dependencies to Dagger. Let's dive into details.

This sample is not the first place that people are talking about it, but I tried to provide its best
picture.

## Pros/Cons
### Pros respect to the standard solution which is Dagger 2
 - Much faster build
 - More flexibility. For instance, you can inject functions.
 - The classes don't need to know who would provide them and how. Not even an annotation is needed.
 - Mocking and testing is supper easy.
 - Less memory consumption in embedded devices.
 - Easy extendable to Kotlin Multiplatform modules as you can see `domain` and `presentation` modules in 
    this project are multiplatform modules.

### Cons respect to the standard solution which is Dagger 2
 - There is some boiler trap codes, especially in the app module.
 - No constructor injection! I'm not sure if it's a cone but I heard people like constructor injection because
    it scoped the usage of the instances that a class needs, but if you double check this statement, you would
    notice that the scope of any class is defined in its module, where is responsible to provide the needed
    instances. It's just what scope means in this context. So I don't think we lose anything by avoiding constructor
    injection. All and all constructors are not real methods!
 - You're in trouble if you don't know the concept of dependency injection and scopes, because basically you can
    do anything. Also if you don't know Kotlin itself enough. For instance, what is the difference of these two
    property?
    ```kotlin
      val provideInstanceOf: SomeType
        get() = instance
    ```
    and
    ```kotlin
      val provideInstanceOf: SomeType = instance
    ```

Run/Test
---
To run tests and checks just use standard `gradle` commands as IDEA/AS would not help you a lot!
```kotlin
./gradlew clean check
```

Questions/Problems
---
Please feel free to fill an issue to ask questions or report issues. Also creating PRs are welcomed.
