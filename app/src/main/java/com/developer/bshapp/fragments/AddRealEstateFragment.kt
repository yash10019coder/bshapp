package com.developer.bshapp.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isEmpty
import androidx.navigation.Navigation
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.developer.bshapp.databinding.FragmentAddRealEstateBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream


class AddRealEstateFragment : Fragment() {
    lateinit var binding: FragmentAddRealEstateBinding
    lateinit var language: String
    private var clickedButton: String = "none"
    lateinit var bitmap: Bitmap
    lateinit var encodedImage: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRealEstateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding!!.submitBtn.setOnClickListener {
            verify()
        }

        binding!!.imageHolder.setOnClickListener {

            Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        onImagePicked.launch(intent)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: com.karumi.dexter.listener.PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }

                }).check()

        }

        return view
    }

    private fun verify(){
        if (binding?.etpropertytype?.isEmpty())
            binding?.etpropertytype?.editText?.error = "This can't be empty"
        else if (binding?.etpropertytype?.isEmpty())
            binding?.etpropertytype?.editText?.error = "This can't be empty"
        else if (binding?.etacerage?.isEmpty())
            binding?.etacerage?.editText?.error = "This can't be empty"
        else if (binding?.etonefeet?.isEmpty())
            binding?.etonefeet?.editText?.error = "This can't be empty"
        else if (binding?.etaddress?.isEmpty())
            binding?.etaddress?.editText?.error = "This can't be empty"
        else if (binding?.etcontactname?.isEmpty())
            binding?.etcontactname?.editText?.error = "This can't be empty"
        else if (binding?.etcontactnum?.isEmpty())
            binding?.etcontactnum?.editText?.error = "This can't be empty"
        else if (binding?.etMoreDetails?.isEmpty())
            binding?.etMoreDetails?.editText?.error = "This can't be empty"
        else{
            uploadToDB()
        }
//        if (binding?.et?.editText.toString().equals(""))
//            binding?.et?.editText?.error="This can't be empty"
    }
     private fun uploadToDB(
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://139.59.61.90/main/") // as we are sending data in json format so
            .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

        val params = HashMap<String?, String?>()
         params["image"]=encodedImage
         params["owner"] = binding?.etcontactname?.editText?.text.toString()
         params["propertyType"] = binding?.etpropertytype?.editText?.text.toString()
         params["transactionType"] = binding?.etpropertytrans?.editText?.text.toString()
         params["space"] = binding?.etacerage?.editText?.text.toString()
         params["price"] = binding?.etonefeet?.editText?.text.toString()
         params["address"] = binding?.etaddress?.editText?.text.toString()
         params["contact"] = binding?.etcontactnum?.editText?.text.toString()
         params["details"] = binding?.etMoreDetails?.editText?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.uploadRealEstate(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser().parse(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addRealEstateFragment_to_sentSuccessfullyFragment)
                    }

                } else {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addRealEstateFragment_to_sentFailedFragment)
                    }
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }
    private var onImagePicked =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val mainData: Intent = result.data!!
                val filePath: Uri = mainData.data!!
                try {
                    val inputStream = requireActivity().contentResolver.openInputStream(filePath)

                    bitmap = BitmapFactory.decodeStream(inputStream)

                    binding?.newsImg?.setImageBitmap(bitmap)
                    binding?.placeholder?.visibility = View.INVISIBLE

                    imageStore(bitmap)
                } catch (e: Exception) {
                    print(e.message)
                }


            }
        }

    private fun imageStore(bitmap: Bitmap) {
        val stream: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val imageBytes = stream.toByteArray()
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)


    }
}