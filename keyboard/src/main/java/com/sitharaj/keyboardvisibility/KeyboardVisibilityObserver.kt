
/*
 * Copyright 2024 Sitharaj Seenivasan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sitharaj.keyboardvisibility

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.appcompat.app.AppCompatActivity

/**
 * A lifecycle-aware observer that detects changes in keyboard visibility.
 *
 * This observer listens to layout changes in the provided [view] and determines whether the
 * software keyboard is visible or hidden based on the height difference between the root view
 * and the currently visible area. It triggers the provided callback when a change in keyboard
 * visibility is detected.
 *
 * @param lifecycleOwner The lifecycle owner (Activity or Fragment) that controls this observer.
 * @param view The view to observe for global layout changes, typically the root view of the activity or fragment.
 * @param onKeyboardVisibilityChanged A callback function that is invoked with a boolean indicating
 * whether the keyboard is visible (`true`) or hidden (`false`).
 */
class KeyboardVisibilityObserver(
    private val lifecycleOwner: LifecycleOwner,
    private val view: View,
    private val onKeyboardVisibilityChanged: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    private var isKeyboardVisible: Boolean = false

    // Listener that checks for changes in the global layout and determines keyboard visibility.
    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect()
        view.getWindowVisibleDisplayFrame(rect)
        val screenHeight = view.rootView.height
        val keypadHeight = screenHeight - rect.bottom

        // A threshold to determine whether the keyboard is visible (15% of the screen height).
        val isVisible = keypadHeight > screenHeight * 0.15
        if (isKeyboardVisible != isVisible) {
            isKeyboardVisible = isVisible
            onKeyboardVisibilityChanged(isVisible)
        }
    }

    init {
        // Add this observer to the lifecycle owner, so it registers and unregisters automatically.
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        // Register the global layout listener when the lifecycle owner is in the STARTED state.
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onStop(owner: LifecycleOwner) {
        // Unregister the global layout listener when the lifecycle owner moves to the STOPPED state.
        view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}

/**
 * Marker annotation to define the DSL scope for the KeyboardVisibilityObserver.
 */
@DslMarker
annotation class KeyboardVisibilityDsl

/**
 * A builder class to simplify the creation of a [KeyboardVisibilityObserver].
 *
 * Use this class in a DSL style to define actions when the keyboard is visible or hidden.
 */
@KeyboardVisibilityDsl
class KeyboardVisibilityDSLBuilder(
    private val lifecycleOwner: LifecycleOwner,
    private val view: View
) {
    private var onVisible: (() -> Unit)? = null
    private var onHidden: (() -> Unit)? = null

    /**
     * Define an action to be performed when the keyboard becomes visible.
     */
    fun onVisible(action: () -> Unit) {
        onVisible = action
    }

    /**
     * Define an action to be performed when the keyboard becomes hidden.
     */
    fun onHidden(action: () -> Unit) {
        onHidden = action
    }

    /**
     * Build and return a [KeyboardVisibilityObserver] based on the provided actions.
     */
    fun build(): KeyboardVisibilityObserver {
        return KeyboardVisibilityObserver(lifecycleOwner, view) { isVisible ->
            if (isVisible) {
                onVisible?.invoke()
            } else {
                onHidden?.invoke()
            }
        }
    }
}

/**
 * Extension function to simplify the use of [KeyboardVisibilityObserver] in Activities.
 *
 * Use this function to easily observe keyboard visibility changes in an Activity using DSL syntax.
 */
fun AppCompatActivity.keyboardVisibility(block: KeyboardVisibilityDSLBuilder.() -> Unit): KeyboardVisibilityObserver {
    val builder = KeyboardVisibilityDSLBuilder(this, findViewById(android.R.id.content))
    builder.block()
    return builder.build()
}

/**
 * Extension function to simplify the use of [KeyboardVisibilityObserver] in Fragments.
 *
 * Use this function to easily observe keyboard visibility changes in a Fragment using DSL syntax.
 */
fun Fragment.keyboardVisibility(block: KeyboardVisibilityDSLBuilder.() -> Unit): KeyboardVisibilityObserver {
    val builder = KeyboardVisibilityDSLBuilder(viewLifecycleOwner, requireView())
    builder.block()
    return builder.build()
}
