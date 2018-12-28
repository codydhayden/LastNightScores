package com.example.codyd.lastnightscores.Utils
import android.util.Log
import com.example.codyd.lastnightscores.NbaStats.PlayerStats
import com.example.codyd.lastnightscores.NbaStats.PlayerStatsJSONHelper
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder

fun jsonToPlayerStats(jsonString : String) {
    val intHeaders = arrayOf("FGM","FGA","FG_PCT","FG3M","FG3A","FG3_PCT","FTM","FTA","FT_PCT","OREB","DREB","REB","AST","STL","BLK","TO","PF","PTS","PLUS_MINUS")
    /*val gson = GsonBuilder().setLenient().create()
    val playerStats = gson.fromJson(jsonString, PlayerStatsJSONHelper::class.java)

    Log.d("JSON Debug: ", playerStats.toString())*/
    val json = JSONObject(jsonString)

    val jsonStatsArray = json.getJSONArray("resultSets")
    val jsonPlayerStatsObject = jsonStatsArray.getJSONObject(0)
    val headers = jsonPlayerStatsObject.getJSONArray("headers")
    val rowSets = jsonPlayerStatsObject.getJSONArray("rowSet")

    for (i in 0 until rowSets.length()) {
        val jsonStringBuilder : MutableList<String> = ArrayList()
        val rowSet = JSONArray(rowSets.get(i).toString())
        for (j in 0 until headers.length()) {
            val currentHeader = headers.get(j).toString()
            val useQuotes = (intHeaders.indexOf(currentHeader) < 0)
            jsonStringBuilder.add("\"$currentHeader\":" + (if(useQuotes) "\"" else "") + rowSet.get(j).toString() + (if(useQuotes) "\"" else ""))
        }
        val playerStatsString = "{ ${jsonStringBuilder.joinToString(",") } }"
        Log.d("JSON Debug: ", playerStatsString)
    }
    //name : String, team : String, min : String, fgm: Int, fga: Int, fgPct: Float, fg3m : Int, fg3a: Int, fg3Pct: Float, ftm: Int, fta: Int, ftPct: Float, orb: Int, drb: Int, reb: Int, ast: Int, stl: Int, blk: Int, turnovers: Int, foul: Int, points: Int
    //var playerStats = PlayerStats(rowSets.getString(6), rowSets.getString(3), rowSets.getString(9))
    Log.d("JSON debug: ", jsonPlayerStatsObject.toString())
}