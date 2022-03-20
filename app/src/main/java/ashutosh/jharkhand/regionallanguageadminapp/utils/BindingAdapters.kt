package ashutosh.jharkhand.regionallanguageadminapp.utils

import android.hardware.lights.LightsManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicAdapter
import ashutosh.jharkhand.regionallanguageadminapp.models.Category
import ashutosh.jharkhand.regionallanguageadminapp.models.Topic


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
 *  Topics Fragment
 */
@BindingAdapter("listTopics")
fun listTopics(recyclerView: RecyclerView, topics: List<Topic>?){
    if (topics != null){
        val adapter = recyclerView.adapter as TopicAdapter
        adapter.setData(topics)
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