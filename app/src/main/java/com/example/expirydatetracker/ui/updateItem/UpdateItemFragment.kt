package com.example.expirydatetracker.ui.updateItem

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.expirydatetracker.R
import com.example.expirydatetracker.databinding.FragmentUpdateItemBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class UpdateItemFragment : Fragment() {
    private lateinit var binding: FragmentUpdateItemBinding
    private var date: String = ""
    private var time: String = ""
    private var parser = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US)
    private var formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var formatTime = SimpleDateFormat("hh:mm", Locale.US)
    private var idItem: Int = -1

    @Inject
    lateinit var viewModelFactory: UpdateItemViewModelFactory
    private lateinit var viewModel: UpdateItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idItem = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[UpdateItemViewModel::class.java]

        binding.imgArrow.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_updateItemFragment_to_homeFragment)
        }

        binding.txtDate.setOnClickListener {
            val now = Calendar.getInstance()
            val year = now.get(Calendar.YEAR)
            val month = now.get(Calendar.MONTH)
            val day = now.get(Calendar.DATE)
            val datePicker = DatePickerDialog(
                requireContext(), { _, i, i2, i3 ->
                    val selectDate = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR, i)
                    selectDate.set(Calendar.MONTH, i2)
                    selectDate.set(Calendar.DATE, i3)
                    date = formatDate.format(selectDate.time)
                    binding.txtDate.text = date
                },
                year, month, day
            )

            datePicker.show()
        }

        binding.txtTime.setOnClickListener {
            val now = Calendar.getInstance()

            val timePicker = TimePickerDialog(
                requireContext(),
                { _, i, i2 ->
                    val selectTime = Calendar.getInstance()
                    selectTime.set(Calendar.HOUR_OF_DAY, i)
                    selectTime.set(Calendar.MINUTE, i2)
                    time = formatTime.format(selectTime.time)
                    binding.txtTime.text = time
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), false
            )

            timePicker.show()
        }

        if (idItem > 0) {
            val getItemsById = viewModel.getItemsById(idItem)
            getItemsById.observe(viewLifecycleOwner, {
                if (it != null) {
                    Log.i("date*****",date)
                    Log.i("time*****",time)
                    binding.TIEName.setText(it.name)
                    binding.TIECategory.setText(it.category)
                    binding.txtDate.text = formatDate.format(parser.parse(it.date)!!)
                    binding.txtTime.text = formatTime.format(parser.parse(it.date)!!)
                    date = binding.txtDate.text.toString()
                    time = binding.txtTime.text.toString()
                } else {
                    Toast.makeText(requireContext(), "لا يوجد منتجات", Toast.LENGTH_LONG).show()
                }
            })

        }

        binding.btnSave.setOnClickListener {
            val dateSave = "$date $time"
            Log.i("dateSave*****",dateSave)
            viewModel.updateItem(
                idItem,
                binding.TIEName.text.toString(),
                binding.TIECategory.text.toString(),
                dateSave
            )
        }

        viewModel.update.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireContext(), "تم التعديل", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(requireContext(), "لا يوجد منتجات", Toast.LENGTH_LONG).show()
            }
        })
    }

}