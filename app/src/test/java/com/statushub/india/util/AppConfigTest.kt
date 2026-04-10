package com.statushub.india.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class AppConfigTest {

    @Test
    fun `AppConfig should read from BuildConfig`() {
        // These values come from BuildConfig which is populated by local.properties
        // In debug builds, test AdMob IDs are used
        assertFalse("PEXELS_API_KEY should not be empty when configured", 
            AppConfig.PEXELS_API_KEY.isEmpty() || AppConfig.PEXELS_API_KEY == "null")
    }
}
