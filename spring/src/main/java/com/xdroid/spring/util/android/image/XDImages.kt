package com.xdroid.spring.util.android.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xdroid.spring.R
import com.xdroid.spring.util.android.tool.XDAndroids

/**
 *   图片加载工具
 */
class XDImages {

    companion object {

        /**
         * resId 显示的资源id
         * view  控件
         * loadingHolder 加载中的占位图
         * errHolder 错误占位图
         */
        @JvmStatic
        @JvmOverloads
        fun loadImgFromResources(
            context: Context,
            resId: Int,
            view: ImageView,
            loadingHolder: Int = -1,
            errHolder: Int = -1
        ) {
            val holder: Int = if (loadingHolder == -1) {
                R.drawable.tsh_loadimg_placeholder
            } else {
                loadingHolder
            }
            val err: Int = if (errHolder == -1) {
                R.drawable.tsh_loadimg_placeholder_err
            } else {
                errHolder
            }
            Glide.with(context)
                .load(resId)
                .apply(RequestOptions().placeholder(holder).error(err))
                .into(view)

        }

        /**
         * resId 显示的资源id
         * view  控件
         * loadingHolder 加载中的占位图
         * errHolder 错误占位图
         */
        @JvmStatic
        @JvmOverloads
        fun loadImgFromUrl(
            context: Context,
            url: String?,
            view: ImageView,
            loadingHolder: Int = -1,
            errHolder: Int = -1
        ) {
            if (url == null) return
            val holder: Int = if (loadingHolder == -1) {
                R.drawable.tsh_loadimg_placeholder
            } else {
                loadingHolder
            }
            val err: Int = if (errHolder == -1) {
                R.drawable.tsh_loadimg_placeholder_err
            } else {
                errHolder
            }
            Glide.with(context)
                .load(url)
                .apply(RequestOptions().placeholder(holder).error(err))
                .into(view)
        }


        /**
         * 缩放图片
         */
        fun zoomImage(bm: Bitmap, scale: Float): Bitmap? {
            //获得图片的宽高
            val width = bm.width
            val height = bm.height
            //计算缩放比例  取得想要缩放的matrix参数
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            //得到新的图片
            return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
        }


        /**
         *  图片上绘制文字
         */
        private fun drawTextToBitmap(
            context: Context, bitmap: Bitmap,
            info: String, size: Int, color: Int,
            paddingTop: Int, paddingLeft: Int
        ): Bitmap? {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = color
            paint.textSize = XDAndroids.dpToPx(context, size).toFloat()
            var bitmapConfig = bitmap.config
            paint.isDither = true // 获取跟清晰的图像采样
            paint.isFilterBitmap = true // 过滤一些
            paint.style = Paint.Style.FILL_AND_STROKE
            if (bitmapConfig == null) bitmapConfig = Bitmap.Config.ARGB_8888
            val bitmap = bitmap.copy(bitmapConfig, true)
            val canvas = Canvas(bitmap)
            canvas.drawText(
                info,
                XDAndroids.dpToPx(context, paddingLeft).toFloat(),
                XDAndroids.dpToPx(context, paddingTop).toFloat(),
                paint
            )
            return bitmap
        }


    }


}