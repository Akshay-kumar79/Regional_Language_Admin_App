package ashutosh.jharkhand.regionallanguageadminapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regionallanguageadminapp.databinding.ListItemQuestionBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Question

class QuestionAdapter: RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var questions: List<Question> = ArrayList()

    fun setData(data: List<Question>){
        questions = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    class ViewHolder(private val binding: ListItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemQuestionBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(question: Question){
            binding.question.text = question.question
            binding.option1.text = question.opt1
            binding.option2.text = question.opt2
            binding.option3.text = question.opt3
            binding.option4.text = question.opt4
            binding.correctAnswer.text = when(question.correctAnswer){
                0 -> {
                    question.opt1
                }
                1 -> {
                    question.opt2
                }
                2 -> {
                    question.opt3
                }
                else -> {
                    question.opt4
                }
            }

            binding.executePendingBindings()
        }
    }

}