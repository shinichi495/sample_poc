
# Weather Forecaste

This one will be supplied for Interview Process of NAB 

# The-Force

An Android app consuming [Weather API](https://api.openweathermap.org/) to display information of weather in specific city in the world
it has been built with clean architecture principles, Repository Pattern and MVVM
pattern as well as Architecture Components.

Min Api Level : 21 [Supports Over 87% Devices ](https://developer.android.com/about/dashboards)

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Architecture](#architecture)
- [Testing](#testing)
- [Libraries](#libraries)
- [What we done ](#whatwedone)

## Architecture

NB: Work in Progress - [Feature Modularisation](https://github.com/shinichi495/sample_poc) 🚧 

The Application is split into a three layer architecture:
- Application/Presentation
- Common
- Domain
- Data

[Architecture Flow Diagram](https://github.com/shinichi495/sample_poc/blob/main/pic/arch_flow.png)

This provides better abstractions between framework implementations 
and the underlying business logic.It requires a number of classes to get 
things running but the pros outweigh the cons in terms of building an app 
that should scale.

The 4 layered architectural approach is majorly guided by clean architecture which provides
a clear separation of concerns with its Abstraction Principle.

#### Presentation / Application

```app``` contains the UI files and handles binding of Server Locator components from other modules.
Binding of data is facilitated by jetpacks data binding by serving data from the viewmodel
to the UI.The data being received is part of a viewstate class that has properties contained in the
relevant state.

#### Domain

The ```domain``` module contains domain model classes which represent the
data we will be handling across presentation and data layer.

Use cases are also provided in the domain layer and orchestrate the flow 
of data from the data layer onto the presentation layer and a split into
modular pieces serving one particular purpose.

The UseCases use a ```UseCase``` interface that defines the parameters its taking in and 
output this helps in creating fakes using in testing.

#### Data

- ```remote```

Handles data interacting with the network and is later serverd up to the presentation layer through 
domain object

- ```cache```

Handles persistence of object with Room ORM from.This module is responsible for handling all local related
logic and serves up data to and from the presentation layer through domain objects.

With this separation we can easily swap in new or replace the database being used without causeing
major ripples across the codebase.

## Testing

Each module has its own tests except for the ```domain``` module which is catered for since its
part of the behavior under test.

All server responses in the tests are served by fake data on fakes package

In the ``data`` module the responses are mocked using the mockwebserver and verified that they
are what we expect.


In the ```app``` module there are unit tests for the viewmodels and util classes 
and connected tests for the UI Screens.


View models testing on live data were guided by this [article](https://proandroiddev.com/how-to-easily-test-a-viewmodel-with-livedata-and-coroutines-230c74416047)
 
 ## Libraries

Libraries used in the whole application are:

- [Jetpack](https://developer.android.com/jetpack)🚀
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
  and act as a channel between use cases and ui
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - support library that allows binding of UI components in  layouts to data sources,binds character details and search results to UI
  - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [Retrofit](https://square.github.io/retrofit/) - type safe http client 
and supports coroutines out of the box.  

- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.🔥🔥
- [Firebase](https://firebase.google.com/) - Backend As A Service for faster mobile development.
  - [Crashylitics](https://firebase.google.com/docs/crashlytics) - Provide Realtime crash reports from users end.
- [Koin](https://github.com/InsertKoinIO/koin) - A pragmatic lightweight dependency injection framework for Kotlin
- [Robolectric](http://robolectric.org/) - Unit test on android framework.
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators) - Recycler View Animations


 ## What we done 

 - Programminglanguage:Kotlin is required,Javaisoptional. -> done
 - Designapp'sarchitecture(suggestMVVM) -> done . Project is based on MVVM. Please check on package application / preseter
 - ApplyLiveDatamechanism -> done
 - UI should belooks like in attachment. -> done
 - Secure Android app from :
    - Decompile APK -> done. I use Proguard for this one 
    - Rooted device -> done. I have a function to check this one , if rooting application detected, application will be exited
    - Data transmission via network -> done. I set pin certificatation on network-security-config
    - Encryption for sensitive information -> done. I saw that appID when call API which need to secure , so I mocked a situation , that will be like : application will call to server and get appID , when get appID from server succesfully , I will encrypt this apiKey and save encrypt data to local , when we want to use it we need to decrypt data. Both step ( encrypt and decrypt) , I use Keystore

- Entity relationship diagram for the database and solution diagrams for the components, infrastructure design if any -> done.
    - Entity relationship diagram for the database -> I just have only one table : WeatherHistory, it was be configed in WeatherHistory.kt
    - Infrastructure design -> please refer above .
