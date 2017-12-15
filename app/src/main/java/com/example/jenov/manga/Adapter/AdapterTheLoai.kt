package com.example.jenov.manga.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jenov.manga.Layout.FragmentLayout.itemTheLoai
import com.example.jenov.manga.Model.TheLoai
import com.example.jenov.manga.R

import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by jenov on 11/21/2017.
 */
class AdapterTheLoai(val context: Context, val theLoaiList: MutableList<TheLoai>) : RecyclerView.Adapter<AdapterTheLoai.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindingData(context: Context, theLoaiModel: TheLoai) {
            val theLoaiText = itemView.find<TextView>(R.id.tenTheLoai)
            theLoaiText.text = theLoaiModel.ten
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = itemTheLoai().createView(AnkoContext.Companion.create(context, parent, false))
        return Holder(view);
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindingData(context, theLoaiList.get(position))
    }

    override fun getItemCount(): Int {
        return theLoaiList.size
    }
}