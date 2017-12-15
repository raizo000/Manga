package com.example.jenov.manga.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.jenov.manga.Layout.itemNoiDungTruyen
import com.example.jenov.manga.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by jenov on 12/04/2017.
 */
class AdapterNoiDungTruyen (val context:Context,val listHinhTruyen:MutableList<String>): RecyclerView.Adapter<AdapterNoiDungTruyen.HolderNoiDungTruyen>(){
    override fun getItemCount(): Int {
        return listHinhTruyen.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, p1: Int): HolderNoiDungTruyen {
        val view = itemNoiDungTruyen().createView(AnkoContext.Companion.create(context,viewGroup,false))
        return HolderNoiDungTruyen(view)
    }

    override fun onBindViewHolder(holder: HolderNoiDungTruyen?, possition: Int) {
        holder?.bindData(context,listHinhTruyen.get(possition))
    }

    class HolderNoiDungTruyen(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(context: Context, linkhinh:String){
            val imgHinhTruyen = itemView.find<ImageView>(R.id.imgHinhTruyen)

            Picasso.with(context).load(linkhinh).into(imgHinhTruyen)
        }
    }
}