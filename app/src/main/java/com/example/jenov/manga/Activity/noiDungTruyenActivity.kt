package com.example.jenov.manga.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.jenov.manga.Adapter.AdapterNoiDungTruyen
import com.example.jenov.manga.DB.DatabaseHelper
import com.example.jenov.manga.Layout.noiDungTruyenLayout
import com.example.jenov.manga.Model.LinkModel
import com.example.jenov.manga.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup

class noiDungTruyenActivity : AppCompatActivity(), View.OnClickListener {

    var toolBarText: TextView? = null
    var prevButton: Button? = null
    var nextButton: Button? = null

    var adapterNoiDungTruyen: AdapterNoiDungTruyen? = null
    var recyclerNoiDungTruyen: RecyclerView? = null
    var linknoidungtruyen: String? = null
    var linkChapPrev: String? = null
    var linkChapNext: String? = null

    var txtTenChap: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noiDungTruyenLayout().setContentView(this)

        prevButton = find(R.id.PreButton)
        nextButton = find(R.id.NextButton)

        prevButton?.setOnClickListener(this)
        nextButton?.setOnClickListener(this)

        txtTenChap = find(R.id.tenChapterNoiDung)
        recyclerNoiDungTruyen = find(R.id.recyclerNoiDungTruyen)
        linknoidungtruyen = intent.getStringExtra("linkNoiDungTruyen")
        getNoiDungTruyen(this, linknoidungtruyen)

        val db = DatabaseHelper(this)
       val allData:MutableList<LinkModel>
        allData=db.getAllData()
        for (values in allData){
            Log.d("Data","${values.link}")
        }
        
    }


    override fun onClick(v: View?) {
        when (v) {
            prevButton -> {
                linknoidungtruyen = linkChapPrev
                getNoiDungTruyen(this, linkChapPrev)
            }
            nextButton -> {
                linknoidungtruyen = linkChapNext
                getNoiDungTruyen(this, linkChapNext)
            }
        }
    }

    private fun getNoiDungTruyen(context: Context, link: String?) {
        doAsync {
            val doc = Jsoup.connect(link).get()
            val elementHinhNoiDungTruyen = doc.select("div.each-page img")

            val listHinhTruyen = mutableListOf<String>()
            for (value in elementHinhNoiDungTruyen) {
                val hinhtruyen = value.attr("src")

                listHinhTruyen.add(hinhtruyen)
            }
            val tenChapter = doc.select("h1.chapter-title").text()
            val elementPrev = doc.select("div.chapter-control a.LeftArrow")
            val elementNext = doc.select("div.chapter-control a.RightArrow")
            linkChapPrev = elementPrev.get(0).attr("href")
            linkChapNext = elementNext.get(0).attr("href")

            uiThread {
                adapterNoiDungTruyen = AdapterNoiDungTruyen(context, listHinhTruyen)
                recyclerNoiDungTruyen?.adapter = adapterNoiDungTruyen
                txtTenChap?.text = tenChapter
            }
        }

    }
}
