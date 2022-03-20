package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import android.text.InputType
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
import ashutosh.jharkhand.regionallanguageadminapp.adapters.SetClickListener
import ashutosh.jharkhand.regionallanguageadminapp.adapters.SetsAdapter
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentSetsBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class SetsFragment : Fragment() {

    private val args: SetsFragmentArgs by navArgs()

    private lateinit var binding: FragmentSetsBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()
        setUpClickListener()

        return binding.root
    }

    private fun setUpClickListener() {
        binding.addSetButton.setOnClickListener {
            val taskEditText = EditText(requireContext())
            taskEditText.inputType = InputType.TYPE_CLASS_NUMBER
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Add New Set")
                .setMessage("Enter the Set number")
                .setView(taskEditText)
                .setPositiveButton("Add") { dialog, which ->
                    viewModel.addNewSetToFirebase(args.category.id, args.topic.id, taskEditText.text.toString().trim())
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    }

    private fun setUpRecyclerView() {
        binding.setRecyclerView.setHasFixedSize(true)

        val setsAdapter = SetsAdapter(SetClickListener { set ->
            findNavController().navigate(
                SetsFragmentDirections.actionSetsFragmentToQuestionsFragment(
                    set,
                    args.topic,
                    args.category,
                    "Set-${set.number}"
                )
            )
        })
        binding.setRecyclerView.adapter = setsAdapter

        viewModel.updateSets(args.category.id, args.topic.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeSetSnapshotListener(args.category.id, args.topic.id)
    }

}