package com.woleapp.util

import com.auth0.jwt.JWT
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


class JWTHelperTest {

    private lateinit var sampleToken: String

    @Before
    fun initializeSampleToken() {
        sampleToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdG9ybUlkIjoiYjQxNmM3N2YtYTY5Ny00MWY5LWI2YmQtYmE0MGI4MDc2N2M2IiwidGVybWluYWxJZCI6IlRZMjM0NTYiLCJidXNpbmVzc05hbWUiOiJDYW1lbENhc2UiLCJpc01lcmNoYW50IjpmYWxzZSwiaXNBZ2VudCI6dHJ1ZSwiaXNBZG1pbiI6ZmFsc2UsInJvbGVzIjpbImFnZW50Il0sImFwcG5hbWUiOiJzdG9ybV9hcHAiLCJwZXJtaXNzaW9ucyI6WyJyb2xlOmFnZW50Il0sImlhdCI6MTU5MzY3NzQ5NiwiZXhwIjoxNTkzNjkxODk2LCJpc3MiOiJzdG9ybTphY2NvdW50cyIsInN1YiI6ImF1dGgifQ._3oVIlSC1uvg6KxS-zyEiL-mjItvmk-lpOLGxt8EEb0"
    }

    @Test
    fun decodeJwtToken_toGetStormId_whenDataComesFromServer() {
        val stormId = JWTHelper.getStormId(sampleToken)
        assertThat(stormId, CoreMatchers.`is`("b416c77f-a697-41f9-b6bd-ba40b80767c6"))
    }

    @Test
    fun decodeJwtToken_checkIfTokenIsAgent(){
        val isAgent = JWTHelper.isAgent(sampleToken)
        assertThat(isAgent, CoreMatchers.`is`(true))
    }

    @Test
    fun decodeJwtToken_checkIfTokenIsMerchant(){
        val isAgent = JWTHelper.isMerchant(sampleToken)
        assertThat(isAgent, CoreMatchers.`is`(false))
    }
}