package com.example.jenov.manga.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

import com.example.jenov.manga.Adapter.AdapterChapter
import com.example.jenov.manga.Layout.chiTietTruyenLayout
import com.example.jenov.manga.Model.ChapterModel
import com.example.jenov.manga.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup

class chiTietTruyenActivity : AppCompatActivity() {
    var tenTruyenTruyenChiTiet: TextView? = null
    var moTaTruyenChiTiet: TextView? = null
    var thongTinTruyenChiTiet: TextView? = null
    var hinhTruyenChiTiet: ImageView? = null

    var listChapter = mutableListOf<ChapterModel>()

    var chapterRecyclerView: RecyclerView? = null
    var adapterChapter: AdapterChapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chiTietTruyenLayout().setContentView(this)
        val linkTruyen = intent.getStringExtra("linkTruyen")
        getChitietTruyen(linkTruyen)
        hinhTruyenChiTiet = find(R.id.hinhTruyenChiTiet)
        tenTruyenTruyenChiTiet = find(R.id.tenTruyenChiTiet)
        moTaTruyenChiTiet = find(R.id.moTaTruyenChiTiet)
        chapterRecyclerView = find(R.id.danhSachChapter)
        thongTinTruyenChiTiet = find(R.id.thongTinTruyen)
        adapterChapter = AdapterChapter(this, listChapter)
        chapterRecyclerView?.adapter = adapterChapter
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)

    }

    private fun getChitietTruyen(linkTruyen: String) {
        doAsync {
            val document = Jsoup.connect(linkTruyen).get()
            val tenTruyen = document.select("div[class=media manga-detail] div[class=media-body] h1").text()
            val hinhTruyen = document.select("div[class=media manga-detail] div[class=media-left cover-detail] img ").attr("src")
            val thongTinTruyen = document.select("p[class=description-update]")
            val moTa = document.select("div[class=col-md-12 mar-top] div[class=manga-content] p")

            val chapterElement = document.select("section#examples a  ")
            for (value in chapterElement) {
                val linkChapter = value.attr("href")
                val tenChapterRaw = value.attr("title")
                val ngayCapNhat = value.select("span").text()

                val matchedResults = Regex(pattern = "(\\d+,?)+").findAll(tenChapterRaw)
                val chapterNumber = StringBuilder()
                for (matchedText in matchedResults) {
                    chapterNumber.append(matchedText.value)
                }

                val tenChapter = "Chapter " + chapterNumber
                val chapter = ChapterModel(tenChapter, linkChapter, ngayCapNhat)
                listChapter.add(chapter)
            }

            uiThread {
                Log.d("Th", "hello")
                Picasso.with(applicationContext).load(hinhTruyen).into(hinhTruyenChiTiet)
                tenTruyenTruyenChiTiet?.text = "$tenTruyen"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    thongTinTruyenChiTiet?.text = Html.fromHtml(thongTinTruyen.html(), 0)
                } else {
                    thongTinTruyenChiTiet?.text = Html.fromHtml(thongTinTruyen.html())
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    moTaTruyenChiTiet?.text = Html.fromHtml(moTa.html().replace("<img>", ""), 0)
                } else {
                    moTaTruyenChiTiet?.text = Html.fromHtml(moTa.html())

                }
                adapterChapter?.notifyDataSetChanged()
            }
        }
    }

}
