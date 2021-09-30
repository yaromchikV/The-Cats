package com.yaromchikv.thecatapi.utils

import android.content.Context
import android.util.AttributeSet

class CustomImageView(context: Context?, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context!!, attrs) {

    private var mHeightRatio = 0.0f

    var heightRatio: Float
        get() = mHeightRatio
        set(ratio) {
            if (ratio != mHeightRatio) {
                mHeightRatio = ratio
                requestLayout()
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mHeightRatio > 0.0) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width * mHeightRatio).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
