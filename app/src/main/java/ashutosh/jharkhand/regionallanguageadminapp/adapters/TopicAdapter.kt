package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemTopicBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Topic
import ashutosh.jharkhand.regionallanguageadminapp.utils.decodeImage

class TopicAdapter(private val categoryImage: String): RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    private var topics: List<Topic> = ArrayList()

    fun setData(data: List<Topic>){
        topics = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position], categoryImage)
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    class ViewHolder(private val binding: ListItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemTopicBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(topic: Topic, categoryImage: String){
            binding.topicName.text = topic.topicName
            if (categoryImage.isNotEmpty()) {
                binding.topicImage.setImageBitmap(decodeImage(categoryImage))
            }
            binding.executePendingBindings()
        }
    }

}