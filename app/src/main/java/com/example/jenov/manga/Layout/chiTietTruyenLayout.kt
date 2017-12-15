package com.example.jenov.manga.Layout

import android.graphics.Typeface
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout

import com.example.jenov.manga.Activity.chiTietTruyenActivity
import com.example.jenov.manga.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by Binh Nguyen on 10/28/2017.
 */
class chiTietTruyenLayout : AnkoComponent<chiTietTruyenActivity> {
    override fun createView(ui: AnkoContext<chiTietTruyenActivity>): View {
        return with(ui) {
            verticalLayout {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    background = ContextCompat.getDrawable(context, R.drawable.shadown)
                }

                lparams(width = matchParent, height = matchParent) {
                    topMargin = dip(6)
                }
                nestedScrollView {

                    verticalLayout {
                        view {
                            backgroundColor = ContextCompat.getColor(context, R.color.gray)
                        }.lparams(width = matchParent, height = dip(2)) {
                            bottomMargin = dip(10)
                        }
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // hình truyện
                            imageView {
                                id = R.id.hinhTruyenChiTiet
                                scaleType = ImageView.ScaleType.FIT_END
                            }.lparams(width = dip(120), height = dip(180), weight = 1f) {
                                leftMargin = dip(4)
                            }
                            // thông tin truyện
                            verticalLayout {
                                textView {
                                    textResource = R.string.tenTruyenChiTiet
                                    maxLines = 1
                                    typeface = Typeface.createFromAsset(context.assets, "Roboto-Black.ttf")
                                    textSize = 20f
                                    ellipsize = TextUtils.TruncateAt.END
                                    id = R.id.tenTruyenChiTiet
                                }.lparams {
                                    bottomMargin = dip(5)
                                }
                                textView {
                                    textResource = R.string.tacGiaTruyenChiTiet
                                    maxLines = 7
                                    typeface = Typeface.createFromAsset(context.assets, "Roboto-Bold.ttf")
                                    ellipsize = TextUtils.TruncateAt.END
                                    id = R.id.thongTinTruyen
                                }.lparams {
                                    bottomMargin = dip(5)
                                }

                            }.lparams(weight = 2f, width = dip(250)) {
                                leftMargin = dip(7)
                            }
                        }
                        //
                        // thanh ngang
                        view {
                            backgroundColor = ContextCompat.getColor(context, R.color.gray)
                        }.lparams(width = matchParent, height = dip(2)) {
                            topMargin = dip(8)
                            bottomMargin = dip(4)
                        }
                        //
                        // Mô tả truyện
                        verticalLayout {

                            textView {
                                textSize = dip(5).toFloat()
                                text = "Mô tả:"
                                typeface = Typeface.createFromAsset(context.assets, "Roboto-Bold.ttf")

                            }
                            linearLayout {
                                textView {
                                    id = R.id.moTaTruyenChiTiet
                                    maxLines = 5
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams(width = matchParent, height = wrapContent) {

                                }
                            }.lparams(width = matchParent, height = dip(90)) {
                                leftMargin = dip(10)
                            }
                        }.lparams {
                            leftMargin = dip(10)
                        }
                        //
                        // thanh ngang
                        view {
                            backgroundColor = ContextCompat.getColor(context, R.color.gray)
                        }.lparams(width = matchParent, height = dip(2)) {
                            topMargin = dip(10)
                        }

                        //
                        // Danh sách chapter
                        verticalLayout {
                            textView {
                                text = "Danh sách chapter"
                                textSize = sp(6).toFloat()
                                typeface = Typeface.createFromAsset(context.assets, "Roboto-Bold.ttf")
                            }
                            recyclerView {
                                id = R.id.danhSachChapter
                                isNestedScrollingEnabled = false
                                layoutManager = LinearLayoutManager(context)
                            }.lparams {
                                padding = dip(10)
                                leftMargin = dip(15)
                            }
                        }
                    }
                }
            }
        }
    }
}