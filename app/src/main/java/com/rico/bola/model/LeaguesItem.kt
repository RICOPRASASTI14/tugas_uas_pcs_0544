package com.rico.bola.model

data class LeaguesItem(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
