package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.CategoryClickListener
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentMainBinding
import ashutosh.jharkhand.regionallanguageadminapp.models.Category
import ashutosh.jharkhand.regionallanguageadminapp.utils.bitmapFromDrawable
import ashutosh.jharkhand.regionallanguageadminapp.utils.encodeImage
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

        val categoryAdapter = CategoryAdapter(CategoryClickListener { category ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToTopicFragment(category, category.categoryName))
        })
        binding.categoryRecyclerView.adapter = categoryAdapter
    }


}