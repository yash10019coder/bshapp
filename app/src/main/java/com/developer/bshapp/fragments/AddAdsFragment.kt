 package com.developer.bshapp.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.developer.bshapp.databinding.FragmentAddAdsBinding
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

class AddAdsFragment : Fragment() {
    var binding: FragmentAddAdsBinding? = null
    lateinit var language: String
    private var clickedButton: String = "none"
    lateinit var bitmap: Bitmap
    lateinit var encodedImage: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAdsBinding.inflate(inflater, container, false)
        val view = binding!!.root
        binding!!.headertitleBtn.setOnClickListener {
//            launchSpeechToText("Heading Title")
        }

        binding!!.cmpynamBtn.setOnClickListener {
//            launchSpeechToText("Company Name")

        }

        binding!!.detailsbtn.setOnClickListener {
//            launchSpeechToText("details")
        }
        binding!!.adresbtn.setOnClickListener {
//            launchSpeechToText("Adress")
        }

        binding!!.submitBtn.setOnClickListener {
            uploadToDB()
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

//     private fun validate() {
//        if (binding?.etheadingtitle?.isEmpty()) {
//            binding?.etcmpyname?.editText?.error = "Headline Required"
//        } else if (news.isEmpty()) {
//            etNews.editText?.error = "News content Required"
//        } else if (mandal.isEmpty()) {
//            etMandal.editText?.error = "Mandal Required"
//        } else if (date.isEmpty()) {
//            etDate.editText?.error = "Date Required"
//        } else if (moreDetails.isEmpty()) {
//            etMoreDetails.editText?.error = "More Details Cannot be Empty"
//        } else if (!checkBox.isChecked) {
//            Toast.makeText(requireContext(), "Agree to the Conditions", Toast.LENGTH_LONG).show()
//        } else {
//
//            Log.d("value", headline.toString())
////            Toast.makeText(requireContext(),headline,Toast.LENGTH_LONG).show()
//            uploadToDB(headline, news, mandal, date, moreDetails)
//        }


//    }


    private fun uploadToDB() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://139.59.61.90/main/") // as we are sending data in json format so
            .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

        val params = HashMap<String?, String?>()
        params["image"] = encodedImage
        params["description"] =binding?.etdis?.editText?.text.toString()
        params["companyName"] = binding?.etcmpyname?.editText?.text.toString()
        params["phone"] = binding?.etphn?.editText?.text.toString()
        params["websiteLink"] = binding?.etwebsite?.editText?.text.toString()
        params["price"] = binding?.etprice?.editText?.text.toString()
        params["address"] = binding?.etadres?.editText?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.uploadAdd(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addAdsFragment_to_sentSuccessfullyFragment)
                    }
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser().parse(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }


    /*
    private fun launchSpeechToText(clickedName: String) {
        clickedButton = clickedName
        var languagePref = "te"
        if (language == "kannada") {
            languagePref = "kn"

        } else if (language == "tamil") {
            languagePref = "ta"
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            languagePref
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
            languagePref
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

        try {
            launchSomeActivity.launch(intent)

        } catch (e: Exception) {
            Toast
                .makeText(
                    requireContext(), " " + e.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }


    }
*/
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
