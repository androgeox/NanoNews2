package com.example.bga_s.nanonews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var vRecView: RecyclerView
    var request:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vRecView = findViewById<RecyclerView>(R.id.recView)

        val o = createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fwww.nanonewsnet.ru%2Frss.xml")
                .map{ Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({showRecView(it.items)},{ Log.e("recycler", "it")})
    }

    fun showRecView(feedlist: ArrayList<FeedItem>){
        vRecView.adapter = RecAdapter(feedlist)
        vRecView.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }
}

class RecAdapter(val items:ArrayList<FeedItem>):RecyclerView.Adapter<RecHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent!!.context)

        val view = inflater.inflate(R.layout.list_item,parent, false)

        return RecHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item =items[position]
        holder?.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}

class RecHolder(view:View):RecyclerView.ViewHolder(view) {

    fun bind(item:FeedItem){
        val vTitle = itemView.findViewById<TextView>(R.id.item_title)
        val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
        vTitle.text = item.title
        vDesc.text = item.description
    }
}

class Feed(
        val items: ArrayList<FeedItem>
)