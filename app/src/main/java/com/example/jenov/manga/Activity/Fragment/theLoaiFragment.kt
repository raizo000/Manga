package com.example.jenov.manga.Activity.Fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jenov.manga.Adapter.AdapterTheLoai
import com.example.jenov.manga.Layout.FragmentLayout.theLoaiLayout
import com.example.jenov.manga.Model.TheLoai
import com.example.jenov.manga.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup

/**
 * Created by jenov on 11/21/2017.
 */
class theLoaiFragment : Fragment() {
    var theLoaiList: MutableList<TheLoai> = mutableListOf()
    var adapterTheLoai: AdapterTheLoai? = null
    var recyclerTheLoai: RecyclerView? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = theLoaiLayout().createView(AnkoContext.Companion.create(activity, container, false))
        recyclerTheLoai = view.find(R.id.theLoaiRecycler)
        adapterTheLoai = AdapterTheLoai(context, theLoaiList)

        recyclerTheLoai?.adapter = adapterTheLoai
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (theLoaiList.size == 0) {
            getTheLoai()
        } else {

        }
    }

    private fun getTheLoai() {
        doAsync {
            val document = Jsoup.connect("http://truyentranh.net/").get()
            val element = document.select(" div[class=category] div[class=col-xs-6] a")
            for (values in element) {
                val linkTheLoai = values.attr("href")
                val tenTheLoai = values.text()
                val theloai = TheLoai(tenTheLoai, linkTheLoai)
                theLoaiList.add(theloai)
                Log.d("Ten the loai", "$tenTheLoai")
            }
            uiThread {
                adapterTheLoai?.notifyDataSetChanged()
            }
        }
    }

}