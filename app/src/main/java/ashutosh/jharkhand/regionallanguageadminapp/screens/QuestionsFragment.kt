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
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.*
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

        val questionAdapter = QuestionAdapter(QuestionLongClickListener { question ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Question")
                .setMessage("Are you sure you want to delete this Question?")
                .setPositiveButton("Yes") { dialog, which ->
                    viewModel.deleteQuestion(args.category, args.topic , args.set, question)
                }
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
            true
        })
        binding.questionRecyclerView.adapter = questionAdapter

        val emptyDataObserver = EmptyDataObserver(binding.questionRecyclerView, binding.emptyTextView, binding.longClickText)
        questionAdapter.registerAdapterDataObserver(emptyDataObserver)

        viewModel.updateQuestions(args.category.id, args.topic.id, args.set.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeQuestionSnapshotListener(args.category.id, args.topic.id, args.set.id)
    }
}