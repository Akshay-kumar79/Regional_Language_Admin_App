package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemIconBinding

class IconAdapter(private val context: Context, private val iconClickListener: IconClickListener) : RecyclerView.Adapter<IconAdapter.ViewHolder>() {

    var iconList: List<Int> = ArrayList()

    fun setData(data: List<Int>) {
        iconList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(iconList[position], context, iconClickListener)
    }

    override fun getItemCount(): Int {
        return iconList.size
    }

    class ViewHolder(private val binding: ListItemIconBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemIconBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(resourceId: Int, context: Context, iconClickListener: IconClickListener) {
            binding.drawableId = resourceId
            binding.iconClickListener = iconClickListener
            binding.icon.setImageDrawable(ContextCompat.getDrawable(context, resourceId))
            binding.executePendingBindings()
        }
    }
}

class IconClickListener(val clickListener: (id: Int) -> Unit){
    fun onClick(resourceId: Int) = clickListener(resourceId)
}