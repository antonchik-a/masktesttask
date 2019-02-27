package com.regextest.android.presentation

import android.content.Context
import com.regextest.android.entity.ParseResult

interface MainView{
    fun showError(error: Int)
    fun showData(resultsPage: MutableList<ParseResult>)
    fun clearAdapter()
    fun showProgress(b: Boolean)
    fun setButtonEnabled(b: Boolean)
    fun showOutputRegex(parseMask: String?)
    fun setCopyButtonVisible(b: Boolean)
    fun appendData(results: MutableList<ParseResult>)
    fun appendValue(results: ParseResult)
    fun showMessage(main_activity_parse_finished: Int)
    fun setClipboard(text: String)
}