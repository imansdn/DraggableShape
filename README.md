# DraggableShape

A simple android application to draw some draggable shapes according to the API data and based on  **Repository Pattern**,  **Clean architecture**,  **MVVM**, **Compose** and using  **unit, instrumented, and UI test**.

<img src="https://github.com/imansdn/DraggableShape/blob/master/readmeImages/Screenshot_App.png" width="200"/>

# Scenario
the flow of showing rectangles follows these steps:
1. get the data from the API
2. store the data in the Database
3. after each rectangle movement(drag), update the new position in the database
4. get the fresh data from the API only one week after the last fetch

# Project structure
there is no real API in this project, so the data is mocked.
The mock response is located in the asset folder, and each endpoint that wants to use the mock data must have the `@MockUP` annotation and point out the response path.
So this project has a `MockUp Interceptor` to replace the mock data with the real one.


# Clean Architecture
It uses concepts of the notorious Uncle Bob's architecture called Clean Architecture.
The following diagram shows the structure of this project with 3 layers:
#### Presentation layer
This layer is closest to what the user sees on the screen. The `presentation` layer is a mix of `MVVM` (Jetpack `ViewModel` used to preserve data across activity restart)

Components:
- **View (Fragment/Activity)** - presents data on the screen and passes user interactions to View Model. Views are hard to test, so they should be as simple as possible.
- **ViewModel** - dispatches (through reactive patterns like `LiveData`) state changes to the view and deals with user interactions (these view models are not simply [POJO classes](https://en.wikipedia.org/wiki/Plain_old_Java_object)).
- **ViewState** - common state for a single view

#### Domain layer
This is the core layer of the application. Notice that the `domain` layer is independent of any other layers. This allows making domain models and business logic independent from other layers.
In other words, changes in other layers will not affect the `domain` layer e.g., changing database (`data` layer) or screen UI (`presentation` layer) ideally will not result in any code change within the `domain` layer.

Components:
- **UseCase** - contains business logic
- **DomainModel** - defines the core structure of the data that will be used within the application. This is the source of truth for application data.
- **Repository interface** - required to keep the `domain` layer independent from the `data layer` ([Dependency inversion](https://en.wikipedia.org/wiki/Dependency_inversion_principle)).


#### Data layer
Manages application data and exposes these data sources as repositories to the `domain` layer. Typical responsibilities of this layer would be to retrieve data from the internet and optionally cache this data locally.

Components:
- **Repository** exposes data to the `domain` layer. Depending on application structure and quality, the external APIs repository can also merge, filter, and transform the data. The intention of
these operations are to create a high-quality data source for the `domain` layer, not to perform any business logic (`domain` layer `use case` responsibility).
- **Mapper** - maps `data model` to `domain model` (to keep `domain` layer independent from the `data` layer).
- **RetrofitService** - defines a set of API endpoints.
- **DataModel** - defines the structure of the data retrieved from the network and contains annotations, so Retrofit (Moshi, Gson, etc..) understands how to parse this network data (XML, JSON, Binary...) this data into objects.

<img src="https://github.com/imansdn/DraggableShape/blob/master/readmeImages/Clean-Architecture-in-Android.png" width="450"/>


# RepositoryPattern
Repository operations are delegated to a relevant data source. Datasources can be remote or local. The repository operation has logic that determines the relevance of a given data source.
The app has a repository that checks the Database for local data. The repository checks with the remote Source if the local data isn't present.

The following illustration shows what a simple repository implementation looks like:
<img src="https://github.com/imansdn/DraggableShape/blob/master/readmeImages/repositoryPattern.png" width="450"/>


## Library references ##
1.    Retrofit
2.    Dagger Hilt
3.    Room
4.    Coroutine/Flow
5.    Compose

# PR
the doors are open for any pull requests.

## License
```
MIT License
Copyright (c) 2019 Igor Wojda
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation, the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS, " WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NON-INFRINGEMENT. IN
NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY,
WHETHER IN AN ACTION OF  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
