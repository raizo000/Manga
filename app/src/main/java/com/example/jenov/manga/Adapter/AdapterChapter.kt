package com.example.jenov.manga.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.jenov.manga.Activity.noiDungTruyenActivity
import com.example.jenov.manga.DB.DatabaseHelper

import com.example.jenov.manga.Layout.itemChapter
import com.example.jenov.manga.Model.ChapterModel
import com.example.jenov.manga.Model.LinkModel
import com.example.jenov.manga.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by Binh Nguyen on 11/04/2017.
 */
class AdapterChapter(var context: Context, var listChapter: MutableList<ChapterModel>) : RecyclerView.Adapter<AdapterChapter.HolderChapter>() {
    override fun getItemCount(): Int {
        return listChapter.size
    }

    override fun onBindViewHolder(holder: HolderChapter?, position: Int) {
        holder?.bindingData(context, listChapter[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HolderChapter {
        val view = itemChapter().createView(AnkoContext.Companion.create(context, parent, false))
        return HolderChapter(view)
    }

    class HolderChapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindingData(context: Context, chapterModel: ChapterModel) {
            val tencChap = itemView.find<TextView>(R.id.tenChapterChiTiet)
            val db = DatabaseHelper(context)
            val ngayDang = itemView.find<TextView>(R.id.ngayDang)
            val allData: MutableList<LinkModel>
            var data = LinkModel("", "")
            allData = db.getParticularMangaData(chapterModel.linkChap)
            for (values in allData) {
                data = LinkModel(values.link, "")
            }
            Log.d("data", "${data.link}")
            if (data.link.equals(chapterModel.linkChap)) {
                tencChap.text = chapterModel.tenChap + " Đã xem"
            } else {
                tencChap.text = chapterModel.tenChap
            }
            //     if (db.getParticularMangaData(chapterModel.linkChap) != null) {
            //        tencChap.text = chapterModel.tenChap + " Đã xem"
            //     } else {

            //     }

            //   tencChap.text = chapterModel.tenChap
            ngayDang.text = chapterModel.ngayDang
            itemView.setOnClickListener {
                val message: String
                val affectedRow: Long = db.insertMangatData(chapterModel.linkChap, chapterModel.ngayDang)
                if (affectedRow.toInt() == (-1)) {
                    message = "Data not added"
                } else {
                    message = "Data added successfully"
                }
                //     Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, noiDungTruyenActivity::class.java)
                intent.putExtra("linkNoiDungTruyen", chapterModel.linkChap)
                context.startActivity(intent)
            }
        }
    }


}