package com.example.jenov.manga.Layout

import android.graphics.Typeface
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.example.jenov.manga.Activity.noiDungTruyenActivity
import com.example.jenov.manga.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by jenov on 12/04/2017.
 */
class noiDungTruyenLayout : AnkoComponent<noiDungTruyenActivity> {
    override fun createView(ui: AnkoContext<noiDungTruyenActivity>): View {
        return with(ui) {
            frameLayout {


                recyclerView {
                    id = R.id.recyclerNoiDungTruyen
                    layoutManager = LinearLayoutManager(context)
                }
                linearLayout {

                    orientation = LinearLayout.HORIZONTAL

                    button {
                        id = R.id.PreButton
                        text = "<"
                        typeface = Typeface.DEFAULT_BOLD
                        backgroundColor = ContextCompat.getColor(context, R.color.lightGray)
                    }.lparams(width = dip(50), height = matchParent)

                    textView {
                        id = R.id.tenChapterNoiDung
                        textColor = ContextCompat.getColor(context, R.color.black)
                        typeface = Typeface.createFromAsset(context.assets, "Roboto-Bold.ttf")
                        gravity = Gravity.CENTER
                        backgroundColor = ContextCompat.getColor(context, R.color.lightGray)
                    }.lparams(width = wrapContent, height = matchParent, weight = 1f)

                    button {
                        id = R.id.NextButton
                        text = ">"
                        textColor = ContextCompat.getColor(context, R.color.black)
                        typeface = Typeface.DEFAULT_BOLD
                        backgroundColor = ContextCompat.getColor(context, R.color.lightGray)
                    }.lparams(width = dip(50), height = matchParent)
                }.lparams(width = matchParent, height = dip(36)) {
                    gravity = Gravity.BOTTOM
                }
            }
        }
    }
}
