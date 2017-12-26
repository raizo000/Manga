package  com.example.jenov.manga.Activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.jenov.manga.Adapter.AdapterTruyenMoi
import com.example.jenov.manga.Adapter.AdapterTruyenFullHayNhat
import com.example.jenov.manga.Activity.Fragment.theLoaiFragment
import com.example.jenov.manga.Layout.MainLayout
import com.example.jenov.manga.Model.ChapterModel
import com.example.jenov.manga.Model.TruyenModel
import com.example.jenov.manga.R

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity(), TextWatcher {
    var timKiem: EditText? = null
    var tempList: MutableList<TruyenModel> = mutableListOf()


    var noidung: LinearLayout? = null

    var listChapterTruyenTieuBieu: MutableList<ChapterModel> = mutableListOf()
    var listTruyenTieuBieu: MutableList<TruyenModel> = mutableListOf()

    var listChapterTruyenFullHayNhat: MutableList<ChapterModel> = mutableListOf()
    var listTruyenFullMoiNhat: MutableList<TruyenModel> = mutableListOf()

    var listChapterTruyenCapNhat: MutableList<ChapterModel> = mutableListOf()
    var listTruyenCapNhat: MutableList<TruyenModel> = mutableListOf()

    var listChapterTruyenFull: MutableList<ChapterModel> = mutableListOf()
    var listTruyenFull: MutableList<TruyenModel> = mutableListOf()

    var adapterTruyenTieuBieu: AdapterTruyenMoi? = null
    var adapterTruyenCapNhat: AdapterTruyenMoi? = null
    var adapterTruyenFullHayNhat: AdapterTruyenFullHayNhat? = null
    var adapterTimKiem: AdapterTruyenFullHayNhat? = null

    var recyclerTruyenTieuBieu: RecyclerView? = null
    var recyclerTruyenCapNhat: RecyclerView? = null
    var recyclerTruyenFullHayNhat: RecyclerView? = null
    var recyclerTimKiem: RecyclerView? = null

    var imgTheLoai: ImageView? = null
    var isTheLoai: Boolean = false
    var fragmentTheLoai: theLoaiFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainLayout().setContentView(this)
        getDanhSachTruyenTieuBieu()
        getDanhSachTruyenMoiCapNhat()
        if (listTruyenFull.size == 0) {
            getDuLieuTruyenFull()
        }

        recyclerTruyenTieuBieu = find(R.id.recyclTruyenMoi)
        adapterTruyenTieuBieu = AdapterTruyenMoi(this, listTruyenTieuBieu)
        recyclerTruyenTieuBieu?.adapter = adapterTruyenTieuBieu

        recyclerTruyenCapNhat = find(R.id.recyclTruyenCapNhat)
        adapterTruyenCapNhat = AdapterTruyenMoi(this, listTruyenCapNhat)
        recyclerTruyenCapNhat?.adapter = adapterTruyenCapNhat

        recyclerTruyenFullHayNhat = find(R.id.recyclTruyenFullHayNhat)
        adapterTruyenFullHayNhat = AdapterTruyenFullHayNhat(false, this, listTruyenFullMoiNhat)
        recyclerTruyenFullHayNhat?.adapter = adapterTruyenFullHayNhat

        timKiem = find(R.id.search)
        recyclerTimKiem = find(R.id.timKiemRecycler)
        noidung = find(R.id.noiDung)
        timKiem?.addTextChangedListener(this)

        fragmentTheLoai = theLoaiFragment()

        imgTheLoai = find(R.id.theLoai)
        imgTheLoai?.onClick {
            if (isTheLoai) {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(fragmentTheLoai)
                fragmentTransaction.commit()
            } else {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.khungNoiDung, fragmentTheLoai)
                fragmentTransaction.commit()
            }
            isTheLoai = !isTheLoai
        }

    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        if (p0?.trim()!!.length > 0) {
            noidung?.visibility = View.GONE
            recyclerTimKiem?.visibility = View.VISIBLE

            tempList.clear()
            tempList = listTruyenFull.filter { p0.toString() in it.tenTruyen }.toMutableList()
            adapterTimKiem = AdapterTruyenFullHayNhat(true, this, tempList)
            recyclerTimKiem?.adapter = adapterTimKiem
            adapterTimKiem?.notifyDataSetChanged()
        } else {
            noidung?.visibility = View.VISIBLE
            recyclerTimKiem?.visibility = View.GONE
        }

    }


    private fun getDanhSachTruyenTieuBieu() {
        doAsync {
            val document = Jsoup.connect("http://truyentranh.net/").get()
            val elementTruyenTieuBieu = document.select(" div.update-list.slider-fixtop div.hot-manga")
            for (item in elementTruyenTieuBieu) {

                val tenTruyen = item.select("div.thumbnails a").attr("title")
                val tenChap = item.select("div.caption a.Chapter p").text()
                val linkTruyen = item.select("div.thumbnails a").attr("href")
                val linkHinh = item.select("div.thumbnails img").attr("src")
                val linkChap = item.select("div.caption a.Chapter").attr("href")
                val chapterModel = ChapterModel(tenChap, linkChap)

                listChapterTruyenTieuBieu.add(chapterModel)
                val truyenModel = TruyenModel(tenTruyen, linkTruyen, listChapterTruyenTieuBieu, linkHinh)
                listTruyenTieuBieu.add(truyenModel)
            }

            uiThread {
                adapterTruyenTieuBieu?.notifyDataSetChanged()
            }

        }
    }


    private fun getDuLieuTruyenFull() {

        doAsync {
            var linkPage = "http://truyentranh.net/danh-sach.tall.html?p="
            val doc = Jsoup.connect("${linkPage}1").get()
            val elementMaxPage = doc.select("div.pagination a").last()
            val maxPage = elementMaxPage.text().toInt()
            for (value in 1..maxPage) {
                getItem("$linkPage$value")
            }
        }
    }

    private fun getItem(link: String) {
        val document = Jsoup.connect(link).get()
        val elementList = document.select("div[id=loadlist] div[class=media mainpage-manga]")
        for (item in elementList) {
            val tenTruyen = item.select(" h4[class=manga-newest]").text()
            val tenChap = ""
            val linkTruyen = item.select("a.tooltips").attr("href")
            val linkHinh = item.select("img").attr("src")
            val linkChap = ""
            val chapterModel = ChapterModel(tenChap, linkChap)
            listChapterTruyenFull.add(chapterModel)
            val truyenModel = TruyenModel(tenTruyen, linkTruyen, listChapterTruyenFull, linkHinh)
            listTruyenFull.add(truyenModel)
        }
    }

    private fun getDanhSachTruyenMoiCapNhat() {
        doAsync {
            val document = Jsoup.connect("http://truyentranh.net/danh-sach.tmoinhat.html").get()
            val elementListTruyenMoiCapNhat = document.select("section.bodycontainer div#loadlist div[class=col-md-6 col-sm-6] div[class=media mainpage-manga]")
            for (item in elementListTruyenMoiCapNhat) {

                val tenTruyen = item.select("div.media-body a").attr("title")
                val tenChap = ""
                val linkTruyen = item.select(" div[class=media-left cover-manga] a").attr("href")
                val linkHinh = item.select("div.media-left.cover-manga  a img").attr("src")
                val linkChap = item.select(" div[class=media-left cover-manga] a").attr("href")
                val chapterModel = ChapterModel(tenChap, linkChap)
                listChapterTruyenFullHayNhat.add(chapterModel)
                val truyenModel = TruyenModel(tenTruyen, linkTruyen, listChapterTruyenFullHayNhat, linkHinh)
                listTruyenFullMoiNhat.add(truyenModel)
            }
            uiThread {
                adapterTruyenFullHayNhat?.notifyDataSetChanged()
            }
        }
    }
}
