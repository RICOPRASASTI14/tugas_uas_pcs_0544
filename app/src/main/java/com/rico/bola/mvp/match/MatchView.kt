package com.rico.bola.mvp.match

import com.rico.bola.model.EventsItem
import com.rico.bola.model.LeagueResponse

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventList(data: List<EventsItem>)
}
