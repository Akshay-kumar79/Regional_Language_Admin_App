package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemCategoryBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Category
import ashutosh.jharkhand.regionallanguageadminapp.utils.decodeImage

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var categoryList: List<Category> = ArrayList()

    fun setData(data: List<Category>) {
        categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(private val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(category: Category) {
            binding.name.text = category.name
            if (category.image.isNotEmpty()) {
                binding.image.setImageBitmap(decodeImage(category.image))
            }
            binding.executePendingBindings()
        }
    }

}