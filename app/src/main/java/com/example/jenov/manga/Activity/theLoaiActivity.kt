package com.example.jenov.manga.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.example.jenov.manga.Adapter.AdapterTruyenFullHayNhat
import com.example.jenov.manga.Layout.theLoaiLayout
import com.example.jenov.manga.Model.ChapterModel
import com.example.jenov.manga.Model.TruyenModel
import com.example.jenov.manga.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup

class theLoaiActivity : AppCompatActivity() {

    var listChapter: MutableList<ChapterModel> = mutableListOf()
    var listTruyen: MutableList<TruyenModel> = mutableListOf()
    var adapterTheLoai: AdapterTruyenFullHayNhat? = null
    var recyclerViewTheLoai: RecyclerView? = null
    var toolBarText: TextView? = null
    var maxPage = 1
    var itemDangHienThi = 9
    var linkPage = ""
    var oldPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theLoaiLayout().setContentView(this)
        recyclerViewTheLoai = find(R.id.recyclerTheLoai)
        toolBarText = find(R.id.toolBarText)
        val link = intent.getStringExtra("linkTheLoai")

        adapterTheLoai = AdapterTruyenFullHayNhat(false, this, listTruyen)
        recyclerViewTheLoai?.adapter = adapterTheLoai

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutmanager = recyclerView?.layoutManager as GridLayoutManager
                val tongItemDaHienThi = layoutmanager.findFirstCompletelyVisibleItemPosition()
                val tongItem = layoutmanager.itemCount
                Log.d("Tong item đã hiển thị", "${tongItemDaHienThi + itemDangHienThi}")
                Log.d("Tong item", "${tongItem}")
                if (tongItem <= (tongItemDaHienThi + itemDangHienThi)) {
                    if (oldPage <= maxPage) {
                        getToanBoDuLieuTruyen("$linkPage?p=$oldPage")
                    }
                }
            }
        }
        recyclerViewTheLoai?.addOnScrollListener(scrollListener)
        getToanDuLieuTruyen(link)
        toolbar(link)
    }

    private fun toolbar(link: String) {
        doAsync {
            val document = Jsoup.connect(link).get()
            val theLoai = document.select("div[class=col-md-8] h1[class=title-cate] ").text()
            uiThread {
                toolBarText?.text = theLoai
            }
        }
    }


    private fun getToanDuLieuTruyen(link: String) {
        doAsync {
            linkPage = link
            val doc = Jsoup.connect("${linkPage}?p=1").get()
            val elementMaxPage = doc.select("div.pagination a").last()
            if (elementMaxPage != null) {
                maxPage = elementMaxPage.text().toInt()
            }


            getToanBoDuLieuTruyen("$linkPage?p=1")

        }

    }

    private fun getToanBoDuLieuTruyen(link: String) {
        doAsync {
            val doc = Jsoup.connect(link).get()
            val elementListTruyen = doc.select("div.media.mainpage-manga")
            for (value in elementListTruyen) {
                val linkTruyen = value.select("a.tooltips").attr("href")
                val linkHinhTruyen = value.select("img").attr("src")
                val tenTruyen = value.select("h4.manga-newest").text()
                val tenChap = ""//value.select("div[class=media-body] p[class=description descripfix] a").text()
                val linkChap = value.select("a.tooltips").attr("href")
                val chapterModel = ChapterModel(tenChap, linkChap)
                listChapter.add(chapterModel)

                val truyenmodal = TruyenModel(tenTruyen, linkTruyen, listChapter, linkHinhTruyen)
                listTruyen.add(truyenmodal)
            }

            uiThread {
                adapterTheLoai?.notifyDataSetChanged()
                oldPage++
            }
        }


    }
}
