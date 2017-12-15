package com.example.jenov.manga.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.jenov.manga.Activity.chiTietTruyenActivity
import com.example.jenov.manga.Layout.itemTruyenDeCu
import com.example.jenov.manga.Model.TruyenModel
import com.example.jenov.manga.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by Binh Nguyen on 10/26/2017.
 */
class AdapterTruyenMoi(var context: Context, var listTruyen: MutableList<TruyenModel>) : RecyclerView.Adapter<AdapterTruyenMoi.HolderTruyenDeCu>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): HolderTruyenDeCu {
        val view = itemTruyenDeCu().createView(AnkoContext.Companion.create(context, viewGroup, false))
        return HolderTruyenDeCu(view)
    }

    override fun onBindViewHolder(holder: HolderTruyenDeCu?, position: Int) {
        holder?.bindingData(context, listTruyen[position], position)
    }

    override fun getItemCount(): Int {
        return listTruyen.size
    }

    class HolderTruyenDeCu(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindingData(context: Context, truyenModel: TruyenModel, position: Int) {
            val tenTruyen = itemView.find<TextView>(R.id.tenTruyen)
            tenTruyen.text = truyenModel.tenTruyen
            val tenChap = itemView.find<TextView>(R.id.tenChap)

            tenChap.text = truyenModel.listChap[position].tenChap
            val hinh = itemView.find<ImageView>(R.id.HinhTruyen)
            Picasso.with(context).load(truyenModel.linkHinhTruyen).into(hinh)

            itemView.setOnClickListener {
                val intent = Intent(context, chiTietTruyenActivity::class.java)
                intent.putExtra("linkTruyen", truyenModel.linkTruyen)
                context.startActivity(intent)
            }
        }
    }
}