package com.regextest.android.data

import android.os.Handler
import android.util.Log
import com.regextest.android.Const.LOG_TAG
import com.regextest.android.util.FileManager
import java.io.File
import java.util.concurrent.BlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

class ParseThread(
    var queue: BlockingQueue<Any>,
    var regex: Regex,
    var file: File?,
    var delegate: ParserRepository.ParseResultListener,
    var handler: Handler
) : Thread() {

    override fun run() {

        while (true) {
            val str = queue.take()

            if(str is String) {
                if (regex.matches(str)) {
                    Log.d(LOG_TAG, "match string: $str")
                    if (FileManager.writeToFile(file, str)) {
                        Log.d(LOG_TAG, "append result.log")
                        handler.post(Runnable {
                            delegate.findNewMatch(str)
                        })
                    }
                }
            }else{
                break
            }
        }
        handler.post(Runnable {
            delegate.finishParseFile()
        })


    }

}