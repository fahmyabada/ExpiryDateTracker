package com.example.expirydatetracker.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.MenuCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirydatetracker.R
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var adapterValue: CustomHolderHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        binding.imgSetting.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.imgSetting)
            popup.setForceShowIcon(true)
            MenuCompat.setGroupDividerEnabled(popup.menu, true)
            popup.inflate(R.menu.popup_menu)


            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.scan -> {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeFragment_to_scanFragment)
                    }
                    R.id.date -> {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeFragment_to_expireDateFragment)
                    }
                }
                return@setOnMenuItemClickListener true
            }

            popup.show()

        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterValue
        }

        adapterValue.setOnItemClickListener{ item: Item ->
            Log.i("idHome********","${item.id}")
            val bundle = bundleOf("id" to item.id)

            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_updateItemFragment, bundle)
        }

        displayItems()
    }

    private fun displayItems() {
        val responseLiveData = viewModel.getItems()
        responseLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                adapterValue.differ.submitList(it)
            } else {
                Toast.makeText(requireContext(), "لا يوجد منتجات", Toast.LENGTH_LONG).show()
            }
        })
    }


}