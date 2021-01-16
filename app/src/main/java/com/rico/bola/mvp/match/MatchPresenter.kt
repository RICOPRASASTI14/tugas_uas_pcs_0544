package com.rico.bola.mvp.match

import android.content.Context
import com.google.gson.Gson
import com.rico.bola.helper.database
import com.rico.bola.model.EventResponse
import com.rico.bola.model.EventsItem
import com.rico.bola.model.LeagueResponse
import com.rico.bola.network.ApiRepository
import com.rico.bola.network.TheSportsDbApi
import com.rico.bola.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    var menu = 1

    fun getLeagueAll() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDbApi.getLeagueAll()),
                        LeagueResponse::class.java
                )
            }

            view.hideLoading()
            view.showLeagueList(data.await())
        }
    }

    fun getEventsPrev(id: String) {
        menu = 1
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDbApi.getLeaguePrev(id)),
                        EventResponse::class.java
                )
            }

            view.hideLoading()

            try {
                view.showEventList(data.await().events!!)
            } catch (e: NullPointerException) {
                view.showEmptyData()
            }
        }
    }

    fun getEventsNext(id: String) {
        menu = 2
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDbApi.getLeagueNext(id)),
                        EventResponse::class.java
                )
            }

            view.hideLoading()

            try {
                view.showEventList(data.await().events!!)
            } catch (e: NullPointerException) {
                view.showEmptyData()
            }
        }
    }

    fun getFavoritesAll(context: Context) {
        menu = 3
        view.showLoading()

        val data: MutableList<EventsItem> = mutableListOf()

        context.database.use {
            val favorites = select(EventsItem.TABLE_FAVORITES)
                    .parseList(classParser<EventsItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyData()
        }
    }
}
