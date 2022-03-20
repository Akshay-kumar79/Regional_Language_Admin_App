package ashutosh.jharkhand.regionallanguageadminapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryAdapter
import ashutosh.jharkhand.regionallanguageadminapp.models.Category


/**
 * Main Fragment
 */
@BindingAdapter("listCategories")
fun listCategories(recyclerView: RecyclerView, categories: List<Category>?){
    if (categories != null){
        val adapter = recyclerView.adapter as CategoryAdapter
        adapter.setData(categories)
    }
}

/**
 *  Icons Select Fragment
 */

@BindingAdapter("changesToSelectedIcon")
fun changesToSelectedIcon(imageView: ImageView, encodedImage: String?){
    if (encodedImage != null && encodedImage.isNotEmpty()){
        imageView.setImageBitmap(decodeImage(encodedImage))
    }
}

@BindingAdapter("selectIconPaceholderVisibility")
fun selectIconPaceholderVisibility(imageView: ImageView, encodedImage: String?){
    if (encodedImage == null || encodedImage.isEmpty()) {
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.INVISIBLE
    }
}