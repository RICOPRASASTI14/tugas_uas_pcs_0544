package com.rico.bola.mvp.detail

import com.rico.bola.model.TeamsItem

interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>)
}
