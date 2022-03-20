package ashutosh.jharkhand.regionallanguageadminapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regionallanguageadminapp.R
import ashutosh.jharkhand.regionallanguageadminapp.adapters.IconAdapter
import ashutosh.jharkhand.regionallanguageadminapp.adapters.IconClickListener
import ashutosh.jharkhand.regionallanguageadminapp.databinding.FragmentIconSelectBinding
import ashutosh.jharkhand.regionallanguageadminapp.utils.bitmapFromDrawable
import ashutosh.jharkhand.regionallanguageadminapp.utils.encodeImage
import ashutosh.jharkhand.regionallanguageadminapp.viewModel.MainViewModel


class IconSelectFragment : Fragment() {

    private lateinit var binding: FragmentIconSelectBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIconSelectBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {

        val data: ArrayList<Int> = getIconData()

        val iconAdapter = IconAdapter(requireContext(), IconClickListener { resourceId ->
            val encodedImage = encodeImage(bitmapFromDrawable(resourceId, requireContext()))
            viewModel.onIconSelected(encodedImage)
            findNavController().popBackStack()
        })
        binding.iconRecyclerView.adapter = iconAdapter

        iconAdapter.setData(data)
    }

    private fun getIconData(): ArrayList<Int> {
        val data: ArrayList<Int> = ArrayList()
        data.add(R.drawable.asset_1)
        data.add(R.drawable.asset_2)
        data.add(R.drawable.asset_3)
        data.add(R.drawable.asset_4)
        data.add(R.drawable.asset_5)
        data.add(R.drawable.asset_6)
        data.add(R.drawable.asset_7)
        data.add(R.drawable.asset_8)
        data.add(R.drawable.asset_9)
        data.add(R.drawable.asset_10)
        data.add(R.drawable.asset_11)
        data.add(R.drawable.asset_12)
        data.add(R.drawable.asset_13)
        data.add(R.drawable.asset_14)
        data.add(R.drawable.asset_15)
        data.add(R.drawable.asset_16)
        data.add(R.drawable.asset_17)
        data.add(R.drawable.asset_18)
        data.add(R.drawable.asset_19)
        data.add(R.drawable.asset_20)
        data.add(R.drawable.asset_21)
        data.add(R.drawable.asset_22)
        data.add(R.drawable.asset_23)
        data.add(R.drawable.asset_24)
        data.add(R.drawable.asset_25)
        data.add(R.drawable.asset_26)
        data.add(R.drawable.asset_27)
        data.add(R.drawable.asset_28)
        data.add(R.drawable.asset_29)
        data.add(R.drawable.asset_30)
        data.add(R.drawable.asset_31)
        data.add(R.drawable.asset_32)
        data.add(R.drawable.asset_33)
        data.add(R.drawable.asset_34)
        data.add(R.drawable.asset_35)
        data.add(R.drawable.asset_36)
        data.add(R.drawable.asset_37)
        data.add(R.drawable.asset_38)
        data.add(R.drawable.asset_39)
        data.add(R.drawable.asset_40)
        data.add(R.drawable.asset_41)
        data.add(R.drawable.asset_42)
        data.add(R.drawable.asset_43)
        data.add(R.drawable.asset_44)
        data.add(R.drawable.asset_45)
        data.add(R.drawable.asset_46)
        data.add(R.drawable.asset_47)
        data.add(R.drawable.asset_48)
        data.add(R.drawable.asset_49)
        data.add(R.drawable.asset_50)
        return data
    }

}