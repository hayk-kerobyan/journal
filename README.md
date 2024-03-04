# Journal Android
This is a simple journal Android application built using the Model-View-Intent (MVI) architecture pattern along with Clean Architecture principles. The package structure is designed for easy migration to a multi-module setup.

## Overview
![Screenshot 2024-03-04 at 00.15.46.png](Screenshot%202024-03-04%20at%2000.15.46.png)
# Presenter Layer
- The UI consists of a single screen called `JournalScreen`, featuring a custom composable called `Journal`. `Journal` acts as a wrapper over `BasicTextField` and supports customization through a `TransformationConfig`, enabling different text styles through opening and closing tag characters. Upon saving a journal (if it wasn't previously saved), the text is being reset. All saved journals are displayed in the Navigation drawer. The list is fetched page-by-page.
- The `ViewModel` manages the view state and executes app business logic through `UseCases`. It handles data caching and retrieval.

# Domain Layer
- UseCases are responsible for executing business logic. 
- The repository acts as an intermediary between `UseCases` and the data source, utilizing domain models. This ensures the testability of the application.

# Data Layer
- The repository implementation resides in the data layer, responsible for interacting with the data source and mapping data models into domain models.
- The datasource uses its own models and stores the user inputs so that journals can be edited even if the app is restarted.

# Technologies Used
- Dependency Injection: Koin
- Navigation: Navigation library
- Pagination: Paging Library 3
- Database: Room

## Challenges
1. Creating an Easy-to-Use interface for interacting with the Journal custom composable, enabling developers to easily add custom opening and closing tags with related span styles such as italic, semibold, etc.
2. Responsibility Segregation: Ensuring correct responsibility segregation, such as moving HTML generation from the composable to the UseCases.

## TODO
In addition to UX improvements, two potential enhancements are identified:
1. Implement Paging for Long Journals: If journals are expected to be lengthy, implement paging to load content in paragraphs. Proper cursor management would be essential for this feature.
2. Add a debounce to Database Writes: Add debouncing to database writes to optimize performance, especially when users type rapidly, reducing unnecessary write operations.
