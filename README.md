# Android Keyboard Visibility

[![](https://jitpack.io/v/sitharaj88/android-keyboard-visibility.svg)](https://jitpack.io/#sitharaj88/android-keyboard-visibility)

## Overview

`android-keyboard-visibility` is an Android library designed to help you detect the visibility of the keyboard in your Android applications. This library simplifies handling keyboard visibility events within your activities or fragments.

## Features

- Simple API to listen for keyboard visibility changes.
- Works seamlessly with activities and fragments.
- Lightweight and easy to integrate.

## Installation

### Gradle with Kotlin DSL

Add the JitPack repository to your root `build.gradle.kts` file:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

Add the dependency to your app's `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.github.sitharaj88:android-keyboard-visibility:1.0.5")
}
```

### Gradle with Groovy DSL

Add the JitPack repository to your root `build.gradle` file:

```groovy
allprojects {
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency to your app's `build.gradle` file:

```groovy
dependencies {
    implementation "com.github.sitharaj88:android-keyboard-visibility:1.0.5"
}
```

### Using `libs.versions.toml`

If you are using the `libs.versions.toml` file for version catalog management, add the dependency as follows:

1. **Add the repository** in your root `settings.gradle.kts` or `settings.gradle`:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

2. **Declare the dependency** in your `libs.versions.toml` file:

```toml
[versions]
android-keyboard-visibility = "1.0.5"

[libraries]
keyboard-visibility = { group = "com.github.sitharaj88", name = "android-keyboard-visibility", version.ref = "android-keyboard-visibility" }
```

3. **Add the dependency** in your module's `build.gradle.kts` or `build.gradle` file:

```kotlin
dependencies {
    implementation(libs.keyboard.visibility)
}
```

## Usage

### Basic Setup

To start listening for keyboard visibility events, you can use the following in your activity or fragment:

```kotlin
import com.sitharaj.keyboardvisibility.keyboardVisibility

// In your Activity or Fragment
keyboardVisibility {
    onVisible {
        Toast.makeText(this@MainActivity, "Keyboard is visible", Toast.LENGTH_SHORT).show()
    }

    onHidden {
        Toast.makeText(this@MainActivity, "Keyboard is hidden", Toast.LENGTH_SHORT).show()
    }
}
```

### XML Layout

There is no need to modify your XML layout to use this library. It works purely through code, making it easy to integrate without affecting your layout files.

## Contributing

Feel free to open issues or submit pull requests if you encounter any bugs or have feature requests.

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.
