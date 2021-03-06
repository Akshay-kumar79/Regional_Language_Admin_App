package ashutosh.jharkhand.regionallanguageadminapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.content.ContentProviderCompat.requireContext
import ashutosh.jharkhand.regionallanguageadminapp.R
import java.io.ByteArrayOutputStream

fun bitmapFromDrawable(id: Int, context: Context): Bitmap = BitmapFactory.decodeResource(context.resources, id)

fun decodeImage(encodedImage: String): Bitmap {
    val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun encodeImage(decodedImage: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    decodedImage.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
    val bytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}