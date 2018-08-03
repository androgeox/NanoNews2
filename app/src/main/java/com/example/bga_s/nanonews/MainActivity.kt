package com.example.bga_s.nanonews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity_fragment)

        if (savedInstanceState == null) {
            //val bundle = Bundle()
            //bundle.putString("param", "value")
            //val f = MainFragment()
            // f.arguments = bundle
            fragmentManager.beginTransaction().replace(R.id.fragment_place, MainFragment()).commitAllowingStateLoss()
        }
    }


    class RecAdapter(val items: RealmList<FeedItem>) : RecyclerView.Adapter<RecHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.list_item, parent, false)

            return RecHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
            val item = items[position]!!
            holder.bind(item)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

    }

    class RecHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: FeedItem) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
            vTitle.text = item.title
            vDesc.text = item.description

            itemView.setOnClickListener {
                //val i = Intent(Intent.ACTION_VIEW)
                //i.data = Uri.parse(item.link)
                (vDesc.context as MainActivity).showArticle(item.link)

            }
        }
    }

    private fun showArticle(url: String) {
        val bundle = Bundle()
        bundle.putString("url", url)
        val f = WebFragment()
        f.arguments = bundle

        val frame2 = findViewById<View>(R.id.fragment_place2)
        if (frame2 != null) {
            frame2.visibility = View.VISIBLE
            fragmentManager.beginTransaction().replace(R.id.fragment_place2,f).commitAllowingStateLoss()
        } else
            fragmentManager.beginTransaction().add(R.id.fragment_place, f).addToBackStack("main").commitAllowingStateLoss()
    }
}

