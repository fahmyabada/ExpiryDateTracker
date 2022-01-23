package com.example.expirydatetracker.ui.expiryItems

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirydatetracker.R
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.databinding.FragmentExpireItemsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ExpireItemsFragment : Fragment()  {
    @Inject
    lateinit var viewModelFactory: ExpiryItemsViewModelFactory
    private lateinit var viewModel: ExpiryItemsViewModel

    @Inject
    lateinit var adapterValue: CustomHolderExpiryDate
    private lateinit var binding: FragmentExpireItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_expire_items, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[ExpiryItemsViewModel::class.java]

        binding.imgArrow.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_expireDateFragment_to_homeFragment)
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recItemsExpiry.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterValue
        }

        adapterValue.setOnItemClickListener { item: Item ->
                val bundle = bundleOf("id" to item.id)

                Navigation.findNavController(requireView())
                    .navigate(R.id.action_expireDateFragment_to_updateItemFragment, bundle)
        }

        displayItems()
    }

    private fun displayItems() {
        val responseLiveData = viewModel.getItemsExpiry()
        responseLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                adapterValue.differ.submitList(it)
            } else {
                Toast.makeText(requireContext(), "لا يوجد منتجات", Toast.LENGTH_LONG).show()
            }
        })
    }
}