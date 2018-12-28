package com.example.codyd.lastnightscores.Utils

import java.net.URL;
import android.net.Uri;
import android.util.Log
import com.example.codyd.lastnightscores.Utils.ParamBuilder;
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.util.*

object NetworkUtils {
    private const val nbaUrlBase = "https://stats.nba.com/stats/"
    private const val nbaUrlParam = "/?"
//https://stats.nba.com/stats/boxscoretraditionalv2/?GameID=0021800429&StartPeriod=0&EndPeriod=14&StartRange=0&EndRange=28800&RangeType=0


    fun buildURL(endPointType: EndPointTypes, params : HashMap<String, String>) : URL? {
        var urlBuilder : String = ""

        urlBuilder = nbaUrlBase + endPointType.getEndpoint() + nbaUrlParam + ParamBuilder.buildParams(endPointType, params)

        var url : URL? = null
        try {
            url = URL(urlBuilder);
        } catch(e : MalformedURLException) {
            e.printStackTrace();
        }

        return url;
    }

    fun getResponseFromHttpURL(url : URL?) : String {
        var connection : HttpURLConnection? = null
        val buffer = StringBuffer()
        try {
            Log.d("Response: ", "Trying to retrieve response from URL")
            connection  = url?.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream = connection.inputStream

            val reader = BufferedReader(InputStreamReader(inputStream))
            var line : String? = null
            do {
                line = reader.readLine()
                buffer.append(line + "\n")
                Log.d("Response: ", "> $line")
            } while (line != null)
            Log.i("Http: ", buffer.toString())
        } finally {
            connection?.disconnect()
            return buffer.toString()
        }
    }

}
