package com.regextest.android.data

import android.content.Context
import android.os.Handler
import android.util.Log
import com.regextest.android.Const.LOG_TAG
import com.regextest.android.Const.RESULT_FILE_NAME
import com.regextest.android.util.FileManager
import java.io.File
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.atomic.AtomicBoolean


object ParserRepository {

    var context: Context? = null
    var file: File? = null
    private lateinit var handler: Handler

    private lateinit var downloadThread: Thread
    private lateinit var parseThread: Thread

    private var blockingQueue: BlockingQueue<Any>

    init {
        blockingQueue = ArrayBlockingQueue<Any>(1024)
    }

    fun parseUrl(url: String, regex: Regex, listener: ParseResultListener) {
        handler = Handler(context?.mainLooper)
        prepareFile()
        blockingQueue.clear()

        downloadThread = DownloadThread(blockingQueue, url)
        parseThread = ParseThread(blockingQueue, regex, file, listener, handler)

        downloadThread.start()
        parseThread.start()
    }

    private fun prepareFile() {
        try {
            val dirPath = context?.getFilesDir()?.getAbsolutePath() + File.separator + RESULT_FILE_NAME
            file = File(dirPath)
            if(file?.exists() ?: false && file?.isFile ?: true){
                file?.delete()
            }
            file?.createNewFile()
        } catch (ex: IOException) {
            Log.e(LOG_TAG, ex.message)
        }
    }

    fun finishParsingProcess() {
        if (!downloadThread?.isInterrupted) {
            downloadThread?.interrupt()
        }

        if (!parseThread?.isInterrupted) {
            parseThread?.interrupt()
        }
    }


    fun getResults(offset: Int, count: Int): MutableList<String> {
        return FileManager.readPageFromFile(file, offset, count)
    }

    fun getStringForCopy(set: Set<Int>): String{
        return FileManager.getStringByIndexes(file, set)
    }

    interface ParseResultListener {
        fun findNewMatch(value: String)
        fun finishParseFile()
    }


}