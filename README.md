
## About
This project is an experimental web application built using Kotlin Multiplatform and WebAssembly. The app is a flexible and nice online timer with countdown and chronometer features. It provides a smooth and fast experience.

## Key Features

- **Countdown Timer:** Set a specific time and watch it count down to zero, with smooth animations and precise timekeeping.
- **Chronometer:** Utilize the app as a stopwatch to track elapsed time with options to start, pause, and reset.
- **Customizable Titles and Descriptions:** Add meaningful context to your timer by setting a title and description for each session.
- **Customizable Background Images:** Personalize the timer with your own images or choose from a predefined selection to match your style.
- **Customizable Timer Styles:** Tailor the appearance of the timer, including fonts, colors, and layouts, to create a unique aesthetic.

## Technology Stack

[![Kotlin][kotlin-image]][kotlin-url]
[![Wasm][wasm-image]][wasm-url]
[![coroutine][coroutines-image]][coroutines-url]

- **Kotlin:** The primary programming language used for this project.
- **Compose Multiplatform:** for UI
- **ViewModel:** for managing UI-related data in a lifecycle-conscious way.
- **kotlinx.coroutines:** for managing asynchronous programming and coroutines in Kotlin.
- **Kamel:** to load images from url.

## Screenshot

## Motivation
This project was created as a learning exercise to explore and understand the capabilities of KMM/WASM. The goal was to gain hands-on experience with modern web and cross-platform development technologies while building a functional and beautiful application.
## Live Demo
Check out the live demo here: 

## Getting Started

#### 1. Clone the repository

```shell
git clone https://github.com/Cyrus243/Timer.git
```

#### 2. open in the latest version of intelli J IDEA ultimate

#### 3. Run the app on your device or emulator
You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.


## Contributing 
Contributions are welcome! If you have ideas or improvements, feel free to open an issue, provide valuable feedback or submit a pull request.






[kotlin-image]: https://img.shields.io/badge/kotlin-multiplatform?style=for-the-badge&logo=kotlin&color=%2326282c
[wasm-image]: https://img.shields.io/badge/wasm-2?style=for-the-badge&logo=webassembly&color=%23eaecf0
[coroutines-image]: https://img.shields.io/badge/kotlinx.Coroutines-3?style=for-the-badge&logo=Coroutines&color=%2326282c


[kotlin-url]: https://kotlinlang.org/docs/wasm-overview.html
[wasm-url]: https://webassembly.org/
[coroutines-url]: https://kotlinlang.org/docs/coroutines-overview.html