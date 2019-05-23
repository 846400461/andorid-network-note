package com.example.connectweather

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),DownloadCallback<String>  {

    private var networkFragment:NetworkFragment?=null

    override fun updateFromDownload(result: String?) {
        textView.text=result?:"读取出错"
    }

    override fun getActiveNetworkInfo(): NetworkInfo {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }

    override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {
        when(progressCode){
            ERROR ->{
            }
            CONNECT_SUCCESS->{
            }
            GET_INPUT_STREAM_SUCCESS->{
            }
            PROCESS_INPUT_STREAM_IN_PROGRESS->{
            }
            PROCESS_INPUT_STREAM_SUCCESS->{
            }
        }
    }

    override fun finishDownloading() {
        Log.i("testNet","downloading finish")
        downloading = false
        networkFragment?.cancelDownload()
    }



    private var downloading=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            textView.text=""
            startDownload()
        }

        networkFragment = NetworkFragment.getInstance(supportFragmentManager,"https://www.baidu.com")
    }

    private fun startDownload(){
        if (!downloading){
            // Execute the async download.
            Log.i("testNet","start downloading")
            networkFragment?.apply{
                startDownload()
                downloading=true
            }
        }
    }
}
