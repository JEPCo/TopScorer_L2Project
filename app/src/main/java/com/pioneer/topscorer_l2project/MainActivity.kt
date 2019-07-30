package com.pioneer.topscorer_l2project

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    override fun onTabReselected(p0: TabLayout.Tab?) {
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab) {
        var web = AppInfo.url + "get_top.php?league=" + p0.text
        var pd = ProgressDialog(this)
        pd.setMessage("Please Wait ..")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()
        var rq= Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, web, null,
            Response.Listener { response ->
                pd.hide()
                var list= ArrayList<TopScorer>()
                for (x in 0..response.length()-1)
                    list.add(
                        TopScorer(response.getJSONObject(x).getInt("id"),
                        response.getJSONObject(x).getString("name"),
                        response.getJSONObject(x).getString("club"),
                        response.getJSONObject(x).getInt("goals"))
                    )
                var rvAdapter = TopRVAdapter(this, list)
                top_rv.adapter = rvAdapter
                top_rv.layoutManager = LinearLayoutManager(this)
            },
            Response.ErrorListener { error ->
                pd.hide()
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

            })
        rq.add(jar)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        top_tabs.addOnTabSelectedListener(this)

        var web = AppInfo.url + "get_league.php"
        var pd = ProgressDialog(this)
        pd.setMessage("Please Wait ..")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()

        var rq= Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, web, null,
            Response.Listener { response ->
                pd.hide()
                for (x in 0..response.length()-1) {
                    var logo_id : Int = getResources().getIdentifier(response.getJSONObject(x).getString("league_icon"), "drawable", getPackageName())
                    top_tabs.addTab(top_tabs.newTab().setText(response.getJSONObject(x).getString("league")).setIcon(logo_id))
                }
            },
            Response.ErrorListener { error ->
                pd.hide()
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

            })
        rq.add(jar)
    }
}
