package com.example.jenov.manga.Layout

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.jenov.manga.R
import org.jetbrains.anko.*

/**
 * Created by Binh on 6/16/17.
 */
class itemNoiDungTruyen :AnkoComponent<ViewGroup?>{
    override fun createView(ui: AnkoContext<ViewGroup?>): View {
        return with(ui){
            frameLayout {
                lparams(width = matchParent,height = matchParent)

                imageView {
                    id = R.id.imgHinhTruyen
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams(width = matchParent,height = matchParent)
            }
        }
    }

}