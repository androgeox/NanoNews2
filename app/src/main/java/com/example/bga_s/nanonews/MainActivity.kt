package com.example.bga_s.nanonews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var vRecView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vRecView = findViewById<RecyclerView>(R.id.recView)
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
        holder.bind(item)
    }
}

class RecHolder(view:View):RecyclerView.ViewHolder(view) {

    fun bind(item:FeedItem){
        val vTitle = itemView.findViewById<TextView>(R.id.item_title)
        vTitle.text = item.title
    }
}
