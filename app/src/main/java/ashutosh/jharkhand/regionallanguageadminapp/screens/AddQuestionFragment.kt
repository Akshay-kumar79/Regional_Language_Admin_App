package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentAddQuestionBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class AddQuestionFragment : Fragment() {

    private val args: AddQuestionFragmentArgs by navArgs()

    private lateinit var binding: FragmentAddQuestionBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.addButton.setOnClickListener {
            if (viewModel.addQuestionToFirebase(args.category.id, args.topic.id, args.set.id))
                findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.addQuestionFinished()
    }

}