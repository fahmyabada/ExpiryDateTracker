package com.example.expirydatetracker.ui.scanCode

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import com.example.expirydatetracker.R
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.databinding.FragmentScanBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var nameItem: String
    private lateinit var categoryItem: String
    private lateinit var dateItem: String
    private lateinit var someActivityPermissionLauncher: ActivityResultLauncher<String>

    @Inject
    lateinit var viewModelFactory: ScanCodeViewModelFactory
    private lateinit var viewModel: ScanCodeViewModel

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_scan, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[ScanCodeViewModel::class.java]


        codeScanner = CodeScanner(requireContext(), binding.scannerView)

        // Parameters (default values)
        codeScanner.camera =
            CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            CoroutineScope(Dispatchers.Main).launch {
                it.text.split(",").forEachIndexed { index, item ->
                    if (index == 0)
                        nameItem = item
                    if (index == 1)
                        categoryItem = item
                    if (index == 2)
                        dateItem = item
                }
                viewModel.saveItems(
                    Item(
                        name = nameItem,
                        category = categoryItem,
                        date = dateItem,
                        id = 0,
                        statusExpiry = 1
                    )
                )

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            CoroutineScope(Dispatchers.Main).launch {
//                Toast.makeText(
//                    requireContext(), "Camera initialization error: ${it.message}",
//                    Toast.LENGTH_LONG
//                ).show()
                if (!checkPermission()) {
                    requestPermission()
                }
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        viewModel.data.observe(viewLifecycleOwner, {
            nameItem = ""
            categoryItem = ""
            dateItem = ""
            Toast.makeText(requireContext(), "تم الحفظ", Toast.LENGTH_LONG).show()
        })
    }

    private fun checkPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        Log.i("", "checkPermission**** $result")

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        someActivityPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun showMessageOk(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context as Activity)
            .setMessage(message)
            .setPositiveButton("موافق", okListener)
            .create()
            .show()
    }

    private fun showMessageOkAndSetting(
        okListener: DialogInterface.OnClickListener,
        settingListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(context as Activity)
            .setMessage("اذا اردت تفعيل صلاحية فتح الكاميرا.. \n\n عليك بالذهاب الي اعدادات التطبيق وتفعيلها يدويا.. ")
            .setPositiveButton("موافق", okListener)
            .setNegativeButton("اعدادات", settingListener)
            .create()
            .show()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        someActivityPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->

            if (result) {

                Log.i("", "PermissionSuccess**** ")

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        showMessageOk("انت تحتاج الي تفعيل هذه الصلاحية حتي تستطيع فتح الكاميرا.. ") { _, _ ->
                            someActivityPermissionLauncher.launch(Manifest.permission.CAMERA)

                            return@showMessageOk
                        }
                    } else {

                        val oKClick = { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                        }

                        // this (_) write if not used to clean code
                        val settingClick = { dialog: DialogInterface, _: Int ->
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts(
                                    "package",
                                    (context as Activity).packageName,
                                    null
                                )
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        showMessageOkAndSetting(
                            oKClick,
                            settingClick
                        )

                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}