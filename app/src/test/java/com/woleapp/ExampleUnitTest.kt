package com.woleapp

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Example local unit test, which will transferFunds on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //val jwt = JWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdG9ybUlkIjoiYWEyMTYzMWUtNWUzNS0xMWVhLTk1N2MtZjIzYzkyOWIwMDU3IiwidGVybWluYWxJZCI6IjIwNThHVzUzIiwiYnVzaW5lc3NOYW1lIjoiZGFwb0B3ZWJtYWxsbmcuY29tIiwiaXNNZXJjaGFudCI6ZmFsc2UsImlzQWdlbnQiOnRydWUsImlzQWRtaW4iOmZhbHNlLCJyb2xlcyI6WyJhZ2VudCJdLCJhcHBuYW1lIjoic3Rvcm1fYXBwIiwicGVybWlzc2lvbnMiOlsicm9sZTphZ2VudCJdLCJpYXQiOjE1ODQwMTM4MTEsImV4cCI6MTU4NDAyODIxMSwiaXNzIjoic3Rvcm06YWNjb3VudHMiLCJzdWIiOiJhdXRoIn0.veHYof3ZjzB7fu3m_k-Jb_-rWn2ceuzG2sZcpRYqAVk");
        //System.out.println(jwt.claims.get("stormId"))
        val s = "Duplicate entry 'facopa8477@ofdow.com' for key 'PRIMARY'"
        val pattern: Pattern = Pattern.compile("'(.*?)'")
        val matcher: Matcher = pattern.matcher(s)
        if (matcher.find()) {
            println(matcher.group(1))
        }
        assertEquals(4, 2 + 2)
    }

}
