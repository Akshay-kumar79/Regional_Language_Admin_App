package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentAddCategoryBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class AddCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddCategoryBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.imagePicker.setOnClickListener {
            findNavController().navigate(AddCategoryFragmentDirections.actionAddCategoryFragmentToIconSelectFragment())
        }

        binding.doneButton.setOnClickListener {
            if(viewModel.addCategoryToFirebase())
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.addCategoryFinished()
    }

}