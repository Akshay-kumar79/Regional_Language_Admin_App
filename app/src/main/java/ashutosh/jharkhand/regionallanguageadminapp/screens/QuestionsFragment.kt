package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.QuestionAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicClickListener
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentAddCategoryBinding
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentQuestionsBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class QuestionsFragment : Fragment() {

    private val args: QuestionsFragmentArgs by navArgs()

    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()
        setUpClickListener()

        return binding.root
    }

    private fun setUpClickListener() {
        binding.addQuestionButton.setOnClickListener {
            findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToAddQuestionFragment(args.set, args.topic, args.category))
        }
    }

    private fun setUpRecyclerView() {
        binding.questionRecyclerView.setHasFixedSize(true)

        val questionAdapter = QuestionAdapter()
        binding.questionRecyclerView.adapter = questionAdapter

        viewModel.updateQuestions(args.category.id, args.topic.id, args.set.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeQuestionSnapshotListener(args.category.id, args.topic.id, args.set.id)
    }
}