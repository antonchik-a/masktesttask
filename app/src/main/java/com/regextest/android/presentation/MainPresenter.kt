package com.regextest.android.presentation

import android.util.Log
import android.webkit.URLUtil
import com.regextest.android.Const.LOG_TAG
import com.regextest.android.Const.PAGE_SIZE
import com.regextest.android.R
import com.regextest.android.data.ParserRepository
import com.regextest.android.entity.ParseResult
import com.regextest.android.util.MaskManager

object MainPresenter : ParserRepository.ParseResultListener {

    private var url = ""
    private var mask = ""
    private var selectedIndexes: MutableSet<Int> = mutableSetOf()
    var view: MainView? = null
    var page: Int = 0
    var resultCount = 0

    fun onMaskInput(value: String) {
        mask = value
        view?.showOutputRegex(MaskManager.parseMask(mask))
    }

    fun onUrlInput(value: String) {
        url = value
    }

    fun onParseClick() {
        if (!URLUtil.isNetworkUrl(url)) {
            view?.showError(R.string.main_activity_url_error)
            return
        }

        resultCount = 0
        page = 0
        selectedIndexes = mutableSetOf()
        view?.setCopyButtonVisible(false)
        view?.clearAdapter()
        view?.showProgress(true)
        view?.setButtonEnabled(false)

        ParserRepository.parseUrl(url, Regex(MaskManager.parseMask(mask)),this)
    }


    override fun findNewMatch(value: String) {
        Log.d(LOG_TAG, "findNewMatch")
        resultCount++
        if (resultCount <= PAGE_SIZE) {
            view?.appendValue(ParseResult(value, false))
        }
    }

    override fun finishParseFile() {
        view?.showProgress(false)
        view?.setButtonEnabled(true)
    }

    fun getResultsFromLogFile(start: Int, size: Int): MutableList<ParseResult>{
        var data = ParserRepository.getResults(start, size)
        var results = mutableListOf<ParseResult>()
        for ((index, value) in data.withIndex()) {
            results.add(ParseResult(value, selectedIndexes.contains(index)))
        }
        return results
    }

    fun showDataWithOffset(offset: Int){
        var results = getResultsFromLogFile(offset, PAGE_SIZE)
        view?.appendData(results)
    }

    fun finishParser() {
        ParserRepository.finishParsingProcess()
        view?.showMessage(R.string.main_activity_parse_finished)
    }

    fun checkedResult(index: Int, checked: Boolean) {
        if(checked) {
            selectedIndexes.add(index)
        }else{
            selectedIndexes.remove(index)
        }
        view?.setCopyButtonVisible(selectedIndexes.size != 0)
    }

    fun onCopyClick() {
        view?.setClipboard(ParserRepository.getStringForCopy(selectedIndexes))
    }

    fun loadMore(position: Int) {
        if(position >= PAGE_SIZE - 1) {
            Log.d(LOG_TAG, "load more")
            showDataWithOffset(position)
        }
    }

}