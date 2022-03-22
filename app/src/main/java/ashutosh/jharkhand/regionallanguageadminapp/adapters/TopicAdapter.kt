package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemTopicBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Topic
import ashutosh.jharkhand.regionallanguageadminapp.utils.decodeImage

class TopicAdapter(
    private val categoryImage: String,
    private val topicClickListener: TopicClickListener,
    private val topicLongClickListener: TopicLongClickListener
    )
    : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    private var topics: List<Topic> = ArrayList()

    fun setData(data: List<Topic>){
        topics = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position], categoryImage, topicClickListener, topicLongClickListener)
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

        fun bind(topic: Topic, categoryImage: String, topicClickListener: TopicClickListener, topicLongClickListener: TopicLongClickListener){
            binding.topic = topic
            binding.topicClickListener = topicClickListener
            binding.topicLongClickListener = topicLongClickListener
            binding.topicName.text = topic.topicName
            if (categoryImage.isNotEmpty()) {
                binding.topicImage.setImageBitmap(decodeImage(categoryImage))
            }
            binding.executePendingBindings()
        }
    }
}

class TopicClickListener(val clickListener: (topic: Topic) -> Unit){
    fun onClick(topic: Topic) = clickListener(topic)
}

class TopicLongClickListener(val longClickListener: (topic: Topic) -> Boolean){
    fun onLongClick(topic: Topic) = longClickListener(topic)
}