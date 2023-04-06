package com.woleapp

import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.auth0.android.jwt.JWT
import com.woleapp.util.JWTHelper
import io.jsonwebtoken.Jwts

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will transferFunds on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        val jwt = JWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdG9ybUlkIjoiYWEyMTYzMWUtNWUzNS0xMWVhLTk1N2MtZjIzYzkyOWIwMDU3IiwidGVybWluYWxJZCI6IjIwNThHVzUzIiwiYnVzaW5lc3NOYW1lIjoiZGFwb0B3ZWJtYWxsbmcuY29tIiwiaXNNZXJjaGFudCI6ZmFsc2UsImlzQWdlbnQiOnRydWUsImlzQWRtaW4iOmZhbHNlLCJyb2xlcyI6WyJhZ2VudCJdLCJhcHBuYW1lIjoic3Rvcm1fYXBwIiwicGVybWlzc2lvbnMiOlsicm9sZTphZ2VudCJdLCJpYXQiOjE1ODQwMTM4MTEsImV4cCI6MTU4NDAyODIxMSwiaXNzIjoic3Rvcm06YWNjb3VudHMiLCJzdWIiOiJhdXRoIn0.veHYof3ZjzB7fu3m_k-Jb_-rWn2ceuzG2sZcpRYqAVk");
        val stormId = jwt.claims.get("stormId")?.asString();
        Log.e("test", stormId)
        assertNotNull(stormId);
        assertEquals("com.woleapp", appContext.packageName)
    }
}
