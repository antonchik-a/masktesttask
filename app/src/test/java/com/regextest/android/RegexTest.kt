package com.regextest.android

import com.regextest.android.util.MaskManager
import org.junit.Assert
import org.junit.Test

class RegexTest() {

    @Test
    fun correctMatchTEst() {

        var regex = Regex(MaskManager.parseMask("abc*"))

        var match = regex.matches("abcwsrfwertwertwert")
        var match2 = regex.matches("3abc1")

        Assert.assertEquals(match, true)
        Assert.assertEquals(match2, false)
    }

}