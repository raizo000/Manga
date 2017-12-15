package com.example.jenov.manga.Layout

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.jenov.manga.R

import org.jetbrains.anko.*

/**
 * Created by Binh Nguyen on 11/05/2017.
 */
class itemChapter : AnkoComponent<ViewGroup?> {

    override fun createView(ui: AnkoContext<ViewGroup?>): View {
        return with(ui) {

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                lparams(width = matchParent, height = wrapContent)

                textView {
                    id = R.id.tenChapterChiTiet
                    typeface = Typeface.createFromAsset(context.assets, "Roboto-Medium.ttf")
                    textSize = dip(5).toFloat()
                }.lparams(width = dip(300), height = dip(20), weight = 1f) {
                    padding = dip(4)
                }
                textView {
                    id = R.id.ngayDang
                    typeface = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
                    textSize = dip(5).toFloat()
                    gravity = Gravity.RIGHT

                }.lparams(width = dip(200), height = dip(25), weight = 1f) {
                    padding = dip(4)
                }

            }
        }
    }
}