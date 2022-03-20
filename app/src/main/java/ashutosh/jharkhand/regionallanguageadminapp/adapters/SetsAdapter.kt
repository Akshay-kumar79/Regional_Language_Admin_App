package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemSetBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Set

class SetsAdapter: RecyclerView.Adapter<SetsAdapter.ViewHolder>() {

    private var sets: List<Set> = ArrayList()

    fun setData(data: List<Set>){
        sets = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position])
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    class ViewHolder(private val binding: ListItemSetBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemSetBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(set: Set){
            val setText =  "Set-${set.number}"
            binding.topicName.text = setText
            binding.executePendingBindings()
        }
    }
}