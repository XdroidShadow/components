package com.xdroid.spring.util.javas.tool

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xdroid.spring.R
import com.xdroid.spring.util.androids.tool.XDLog
import com.xdroid.spring.util.androids.tool.XDDimensions
import java.io.File
import java.io.FileOutputStream

/**
 *   图片加载工具
 */
class XDImages {

    companion object {
        private const val TAG = "XDImages"

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
            paint.textSize = XDDimensions.dpToPx(context, size).toFloat()
            var bitmapConfig = bitmap.config
            paint.isDither = true // 获取跟清晰的图像采样
            paint.isFilterBitmap = true // 过滤一些
            paint.style = Paint.Style.FILL_AND_STROKE
            if (bitmapConfig == null) bitmapConfig = Bitmap.Config.ARGB_8888
            val bitmap = bitmap.copy(bitmapConfig, true)
            val canvas = Canvas(bitmap)
            canvas.drawText(
                info,
                XDDimensions.dpToPx(context, paddingLeft).toFloat(),
                XDDimensions.dpToPx(context, paddingTop).toFloat(),
                paint
            )
            return bitmap
        }


        var TRANSFORM_BITMAP_PATH = ""
        const val width = 312 * 5
        const val height = 416 * 5

        /**
         * 将图片压缩保存到sd卡
         */
        fun transformBitmap(context: Context, bitmapPath: String?, number: String?): Bitmap? {
            try {
                val destinationImgPath = TRANSFORM_BITMAP_PATH + File(bitmapPath).name
                XDLog.e(TAG, "压缩图片保存的路径$destinationImgPath")
                var bitmap = getSmallBitmap(bitmapPath, true)
                val f = File(destinationImgPath)
                if (!f.exists()) f.createNewFile()
                val outputStream = FileOutputStream(f)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
                val uri = Uri.fromFile(f)
                //发送广播通知更新图库，这样系统图库可以找到这张图片
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                return bitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 图片压缩
         * isOrigin  是否是原图，不进行压缩
         */
        fun getSmallBitmap(filePath: String?, isOrigin: Boolean): Bitmap {
            var res: Bitmap
            if (isOrigin) {
                res = BitmapFactory.decodeFile(filePath)
            } else {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(filePath, options)
                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, width, height)
                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false
                res = BitmapFactory.decodeFile(filePath, options)
            }
            if (res.width > res.height) {
                res = rotateBitmap(res)
            }
            return res
        }

        /**
         * 如果是横向图片，旋转90度
         */
        private fun rotateBitmap(bitmap: Bitmap): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(90f)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        /**
         * 计算图片的缩放值
         */
        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                // Calculate ratios of height and width to requested height and width
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                // Choose the smallest ratio as inSampleSize value, this will
                // guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            XDLog.e(TAG, "采样率：$inSampleSize")
            return inSampleSize
        }


    }


}