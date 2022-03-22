package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryClickListener
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryLongClickListener
import ashutosh.jharkhand.regionallanguageadminapp.adapters.EmptyDataObserver
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentMainBinding
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()
        setUpClickListener()

        return binding.root
    }

    private fun setUpClickListener() {
        binding.addCategoryButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddCategoryFragment())
        }
    }

    private fun setUpRecyclerView() {
        binding.categoryRecyclerView.setHasFixedSize(true)

        val categoryAdapter = CategoryAdapter(
            CategoryClickListener { category ->
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToTopicFragment(category, category.categoryName))
            },
            CategoryLongClickListener { category ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Category")
                    .setMessage("Are you sure you want to delete this category?")
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.deleteCategory(category)
                    }
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            true
            }
        )
        binding.categoryRecyclerView.adapter = categoryAdapter

        val emptyDataObserver = EmptyDataObserver(binding.categoryRecyclerView, binding.emptyTextView, binding.longClickText)
        categoryAdapter.registerAdapterDataObserver(emptyDataObserver)
    }


}