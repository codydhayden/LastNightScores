package com.example.codyd.lastnightscores.Utils

const val boxscoreTraditionalEndPoint = "boxscoretraditionalv2"
const val scoreboardEndPoint = "scoreboardV2"

enum class EndPointTypes {
    BOX_TRADITIONAL {
        override fun getParamMap() = nbaTraditionalBoxscoreV2
        override fun getEndpoint() = boxscoreTraditionalEndPoint
    },
    SCOREBOARD {
        override fun getParamMap() = scoreboardV2
        override fun getEndpoint() = scoreboardEndPoint
    };

    abstract fun getParamMap() : HashMap<String, String>
    abstract fun getEndpoint() : String

    private fun defaultParams() = object {
        val dayOffset : String = "0"
        val endPeriod : String = "14"
        val endRange : String = "28800"
        val gameDate : String = "12/23/2018"
        val gameId : String = ""
        val leagueId : String = "00"
        val rangeType : String = "0"
        val startPeriod : String = "1"
        val startRange : String = "0"
    }

    val nbaTraditionalBoxscoreV2 = hashMapOf(
            "EndRange" to defaultParams().endRange,
            "EndPeriod" to defaultParams().endPeriod,
            "GameID" to defaultParams().gameId,
            "RangeType" to defaultParams().rangeType,
            "StartPeriod" to defaultParams().startPeriod,
            "StartRange" to defaultParams().startRange
    )

    val scoreboardV2 = hashMapOf(
            "GameDate" to defaultParams().gameDate,
            "LeagueID" to defaultParams().leagueId,
            "DayOffset" to defaultParams().dayOffset
    )


}

object ParamBuilder {

    /*
     * ParamBuilder will create a hash map using the parameters for both the default and passed in map
     * and create a string for parameter portion of the URL for web calls
     * @param: paramType -> defines what endpoint we are building parameters for
     *             params -> hash map of the endpoint parameters that shouldn't use the default value
     * @return: A string in the format of K=V&K=V&K=V for use in web calls
     */

    fun buildParams(paramType : EndPointTypes, params : HashMap<String, String>) : String {

        var paramHashMap = params

        paramType.getParamMap().forEach{ (key, value) ->
            run {
                if (!paramHashMap.contains(key)) {
                    paramHashMap[key] = value
                }
            }
        }

        return buildParamString(paramHashMap)
    }



    /*
     * Builds the parameter string for the URL using the parameters defined in the hash map.
     * @param: paramHashMap<K, V> -> HashMap of parameter name to parameter value
     * @return: string  of the keys and values in the hash map in the form of K=V&K=V&K=V
     */

    private fun buildParamString(paramHashMap : HashMap<String, String>) : String {
        val paramList : MutableList<String> = ArrayList()
        paramHashMap.forEach{(key, value) -> paramList.add("$key=$value") }
        return paramList.joinToString("&")
    }


}
