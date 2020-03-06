package com.example.tmdb.UI.WebViewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import kotlinx.android.synthetic.main.fragment_web_view.*


class WebViewFragment : Fragment(){
    lateinit var url: String
    lateinit var title: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_web_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webview_backarrow.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        webview_text.text = title
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
        webview.loadUrl(url)

    }

    companion object{
        fun newInstance(url: String, title: String): WebViewFragment = WebViewFragment().apply {
            this.url = url
            this.title = title
        }
    }
}