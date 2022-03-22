package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.EmptyDataObserver
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicClickListener
import ashutosh.jharkhand.regionallanguageadminapp.adapters.TopicLongClickListener
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentTopicBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class TopicFragment : Fragment() {

    private val args: TopicFragmentArgs by navArgs()

    private lateinit var binding: FragmentTopicBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()
        setUpClickListener()

        return binding.root
    }

    private fun setUpClickListener() {
        binding.addTopicButton.setOnClickListener {
            val taskEditText = EditText(requireContext())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Add New Topic")
                .setMessage("Enter the name of new topic")
                .setView(taskEditText)
                .setPositiveButton("Add") { dialog, which ->
                    viewModel.addNewTopicToFirebase(args.category.id, taskEditText.text.toString().trim())
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    }

    private fun setUpRecyclerView() {
        binding.topicRecyclerView.setHasFixedSize(true)

        val topicAdapter = TopicAdapter(args.category.categoryImage,
            TopicClickListener { topic ->
                findNavController().navigate(TopicFragmentDirections.actionTopicFragmentToSetsFragment(topic, args.category, topic.topicName))
            },
            TopicLongClickListener { topic ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete topic")
                    .setMessage("Are you sure you want to delete this topic?")
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.deleteTopic(args.category ,topic)
                    }
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
                true
            }
        )
        binding.topicRecyclerView.adapter = topicAdapter

        val emptyDataObserver = EmptyDataObserver(binding.topicRecyclerView, binding.emptyTextView, binding.longClickText)
        topicAdapter.registerAdapterDataObserver(emptyDataObserver)

        viewModel.updateTopics(args.category.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeTopicSnapshotListener(args.category.id)
    }

}