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
        val categories = ArrayList<Category>()
        categories.add(Category("id", "Maths", encodeImage(bitmapFromDrawable(R.drawable.asset_1, requireContext()))))
        categories.add(Category("id", "English", encodeImage(ContextCompat.getDrawable(requireContext(), R.drawable.asset_2)!!.toBitmap())))
        categories.add(Category("id", "Science", encodeImage(ContextCompat.getDrawable(requireContext(), R.drawable.asset_3)!!.toBitmap())))
        categories.add(Category("id", "Sanskrit", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_4))))
        categories.add(Category("id", "Hindi", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_5))))
        categories.add(Category("id", "SST", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_6))))
        categories.add(Category("id", "Physics", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_7))))
        categories.add(Category("id", "Chemistry", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_8))))
        categories.add(Category("id", "Biology", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_9))))
        categories.add(Category("id", "Physical education", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_10))))
        categories.add(Category("id", "Computer science", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_11))))
        categories.add(Category("id", "Computer graphics", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_12))))
        categories.add(Category("id", "Linux OS", encodeImage(BitmapFactory.decodeResource(requireContext().resources, R.drawable.asset_13))))


        binding.categoryRecyclerView.setHasFixedSize(true)

        val categoryAdapter = CategoryAdapter()
        binding.categoryRecyclerView.adapter = categoryAdapter
        //categoryAdapter.setData(categories)
    }


}