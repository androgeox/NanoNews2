package com.example.bga_s.nanonews

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class WebFragment: Fragment() {
    lateinit var url:String
    lateinit var vBrowser: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        url =arguments.getString("url")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.web_frag, container, false)

        vBrowser = view.findViewById<WebView>(R.id.frag2_web)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vBrowser.loadUrl(url)
    }
}