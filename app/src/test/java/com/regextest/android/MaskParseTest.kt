package com.regextest.android

import com.regextest.android.util.MaskManager
import org.junit.Assert
import org.junit.Test


class MaskParseTest {
    @Test
    fun testCorrectEscape() {
        var mask: String = "*mask1(^abc)[]?"

        var result = MaskManager.parseMask(mask)

        Assert.assertEquals(result, "^.*mask1\\(\\^abc\\)\\[\\].?\$")
    }
}