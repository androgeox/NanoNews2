package com.example.bga_s.nanonews

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList

class MainFragment: Fragment() {

    lateinit var vRecView: RecyclerView
    var request: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val param = arguments.getString("param")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.activity_main, container, false)
        vRecView = view.findViewById<RecyclerView>(R.id.recView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val o = createRequest(
                "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fwww.nanonewsnet.ru%2Frss.xml")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            val feed = Feed(it.items.mapTo(RealmList<FeedItem>(), { feed ->
                FeedItem(feed.title,
                        feed.link, feed.description, feed.thumbnail)
            }))

            Realm.getDefaultInstance().executeTransaction { realm ->
                val oldlist = realm.where(Feed::class.java).findAll()
                if (oldlist.size > 0)
                    for (item in oldlist)
                        item.deleteFromRealm()
                realm.copyToRealm(feed)
            }
            showRecView()
        }, {
            Log.e("recycler", "it")
            showRecView()
        })
    }

    fun showRecView() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            if (!isVisible)
                return@executeTransaction
            val feed = realm.where(Feed::class.java).findAll()
            if (feed.size > 0) {
                vRecView.adapter = MainActivity.RecAdapter(feed[0]!!.items)
                vRecView.layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        request?.dispose()
    }
}