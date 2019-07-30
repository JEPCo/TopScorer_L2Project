package com.pioneer.topscorer_l2project

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_layout.view.*

class TopRVAdapter(var con: Context, var list: ArrayList<TopScorer>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(con).inflate(R.layout.item_layout, p0, false)
        return Top(v)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as Top).show(list[p1].name, list[p1].club, list[p1].goals)
    }

    class Top(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun show(nm: String, cl: String, gl: Int)
        {
            itemView.name_tv.text = nm
            itemView.club_tv.text = cl
            itemView.goals_tv.text = gl.toString() + " Goals"
        }
    }
}