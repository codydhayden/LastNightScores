package com.example.codyd.lastnightscores

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import com.example.codyd.lastnightscores.Utils.EndPointTypes
import com.example.codyd.lastnightscores.Utils.NetworkUtils
import com.example.codyd.lastnightscores.Utils.jsonToPlayerStats
import org.w3c.dom.Text
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var mDisplayText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDisplayText = findViewById(R.id.MainText) as TextView

    }

    fun makeNbaApiCall() {
        val gameIdMap = hashMapOf("GameID" to "0021800429")
        val nbaApiUrl = NetworkUtils.buildURL(EndPointTypes.BOX_TRADITIONAL, gameIdMap)
        NbaApiTask(this@MainActivity).execute(nbaApiUrl)
    }


    class NbaApiTask(val ctx : MainActivity) : AsyncTask<URL, Void, String>() {
        override fun doInBackground(vararg params: URL?): String {
            val searchUrl : URL? = params[0]
            var nbaApiResults : String = ""

            try {
                nbaApiResults = NetworkUtils.getResponseFromHttpURL(searchUrl)
            } catch (e : IOException) {
                e.printStackTrace()
            }

            return nbaApiResults



        }

        override fun onPostExecute(result: String?) {
            if (result != null && !result.equals("")) {
                jsonToPlayerStats(result)
                ctx.mDisplayText.setText(result)

            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemThatWasClickedId = item.itemId
        if (itemThatWasClickedId == R.id.action_search) {
            makeNbaApiCall()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
