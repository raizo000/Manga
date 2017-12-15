package com.example.jenov.manga.Layout

import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.jenov.manga.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by Binh Nguyen on 10/28/2017.
 */
class itemTruyenFullHayNhat : AnkoComponent<ViewGroup?> {
    override fun createView(ui: AnkoContext<ViewGroup?>): View {
        return with(ui) {

            frameLayout {
                lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation=2f
                }
                cardView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            id = R.id.HinhTruyen
                            scaleType = ImageView.ScaleType.FIT_XY

                        }.lparams(width = dip(100), height = dip(150))
                        textView {
                            id = R.id.tenTruyen
                            typeface = Typeface.DEFAULT_BOLD
                            maxLines = 2
                            ellipsize = TextUtils.TruncateAt.END
                        }
                        textView {
                            id = R.id.tenChap
                        }
                    }
                }.lparams(width = dip(130), height = dip(190))
            }
        }
    }
}