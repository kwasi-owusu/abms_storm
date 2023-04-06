package com.woleapp.util

import com.google.gson.JsonObject
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class UtilsAndExtensionsKtTest {

    @Test
    fun getSixDigitRandomNumberTest() {
        for (i in 1..100) {
            val randomSixDigit = getSixDigitRandomNumber()
            println(randomSixDigit)
            assertThat(randomSixDigit.length, CoreMatchers.`is`(6))
        }
    }

    @Test
    fun encode() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("ref", "SC${getSixDigitRandomNumber()}")
        //jsonObject.addProperty("id", "11")
        jsonObject.addProperty("a_id", "b416c77f-a697-41f9-b6bd-ba40b80767c6")
        jsonObject.addProperty("amount", "30")
        //jsonObject.addProperty("p_name","Food")
        jsonObject.addProperty("type","card")
        //jsonObject.addProperty("")
        val data = jsonObject.toString()
        val encodedString =
            "https://paylink.netpluspay.com?p=${encodeQueryValue(
                Base64.getEncoder().encodeToString(data.toByteArray())
            )}"
        println(data)
        println("--------------------------------------------------------------------")
        println(encodedString)
        assertThat(encodedString, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }

    @Test
    fun decodeBase64Img(){
        val d = "data:image/png;base64,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"
        val decoded = Base64.getDecoder().decode(d.split("data:image/png;base64,")[1])
        println(String(decoded))
        assertThat(decoded, CoreMatchers.notNullValue())
    }

}