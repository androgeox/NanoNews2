package com.example.bga_s.nanonews

import io.reactivex.Observable
import java.net.HttpURLConnection

fun createRequest(url:String)= Observable.create<String>(
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        try{
            urlConnection.connect()
            if (urlConnection.responseCode != HttpUrlConnection.HTTP_OK)
                it.onError(RuntimeException(urlConnection.resposeMessage))
            else{
                val str = urlConnection.inputStream.bufferedReader().readText()
                it.onNext(str)
            }
            finally{
                urlConnection.disconnect()
            }
}
)