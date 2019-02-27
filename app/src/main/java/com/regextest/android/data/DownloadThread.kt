package com.regextest.android.data

import android.util.Log
import com.regextest.android.Const.LOG_TAG
import java.util.concurrent.BlockingQueue
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean
import javax.net.ssl.HttpsURLConnection


class DownloadThread(var queue: BlockingQueue<Any>, var url: String) : Thread() {

    override fun run() {
        val url = URL(url)
        val urlConnection = url.openConnection()

        try {
            val reader = BufferedReader(InputStreamReader(urlConnection.getInputStream()))
            while (true) {
                val line = reader.readLine() ?: break
                queue.put(line)
            }
            reader.close()
        }catch (ex: IOException) {
            Log.e(LOG_TAG, ex.message)
            queue.put(true)
        } catch (ex: InterruptedException) {
            Log.e(LOG_TAG, ex.message)
            queue.put(true)
        } finally {
            queue.put(true)
        }
    }
}