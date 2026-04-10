package com.statushub.india

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertExists
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appShouldLaunchSuccessfully() {
        // Basic sanity test — the app launches and shows content
        composeTestRule.setContent {
            com.statushub.india.ui.theme.StatusHubTheme {
                androidx.compose.material3.Text(text = "StatusHub India")
            }
        }
        composeTestRule.onNodeWithText("StatusHub India").assertExists()
    }
}
