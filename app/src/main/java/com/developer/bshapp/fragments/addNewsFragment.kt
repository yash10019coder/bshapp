package com.developer.bshapp.fragments


import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.*
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.util.*


class addNewsFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val sharedPrefFile = "languageSharedPreference"
    lateinit var etHeadline: TextInputLayout
    lateinit var etMandal: TextInputLayout
    lateinit var etNews: TextInputLayout
    lateinit var etDate: TextInputLayout
    lateinit var etMoreDetails: TextInputLayout
    lateinit var btnHeadline: ImageButton
    lateinit var newsBtn: ImageButton
    lateinit var placeHolder: ImageView
    lateinit var mandalBtn: ImageButton
    lateinit var submitBtn: Button
    lateinit var moreDetailBtn: ImageButton
    lateinit var newsImg: ImageView
    lateinit var agreementTxt: TextView
    lateinit var checkBox: CheckBox
    private var clickedButton: String = "none"
    lateinit var bitmap: Bitmap
    lateinit var encodedImage: String
    lateinit var language: String
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minutes = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinutes = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_news, container, false)
        initialization(view)
        setupUILanguage()
        btnHeadline.setOnClickListener {
            launchSpeechToText("Headline")
        }

        newsBtn.setOnClickListener {
            launchSpeechToText("News")

        }

        mandalBtn.setOnClickListener {
            launchSpeechToText("Mandal")
        }
        moreDetailBtn.setOnClickListener {
            launchSpeechToText("MoreDetails")
        }

        submitBtn.setOnClickListener {
            validate()
        }

        etDate.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                pickDate()
            }
        }
        newsImg.setOnClickListener {

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

    private fun validate() {
        val headline = etHeadline.editText?.text.toString()
        val news = etNews.editText?.text.toString()
        val mandal = etMandal.editText?.text.toString()
        val date = etDate.editText?.text.toString()
        val moreDetails = etMoreDetails.editText?.text.toString()

        if (headline.isEmpty()) {
            etHeadline.editText?.error = "Headline Required"
        } else if (news.isEmpty()) {
            etNews.editText?.error = "News content Required"
        } else if (mandal.isEmpty()) {
            etMandal.editText?.error = "Mandal Required"
        } else if (date.isEmpty()) {
            etDate.editText?.error = "Date Required"
        } else if (moreDetails.isEmpty()) {
            etMoreDetails.editText?.error = "More Details Cannot be Empty"
        } else if (!checkBox.isChecked) {
            Toast.makeText(requireContext(), "Agree to the Conditions", Toast.LENGTH_LONG).show()
        } else {

            Log.d("value", headline.toString())
//            Toast.makeText(requireContext(),headline,Toast.LENGTH_LONG).show()
            uploadToDB(headline, news, mandal, date, moreDetails)
        }


    }


    private fun uploadToDB(
        headline: String,
        news: String,
        mandal: String,
        date: String,
        moreDetails: String
    ) {

            val retrofit = Retrofit.Builder()
                .baseUrl("http://139.59.61.90/main/") // as we are sending data in json format so
                .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

            val params = HashMap<String?, String?>()
            params["image"] = encodedImage
            params["headings"] = headline
            params["news"] = news
            params["mandal"] = mandal
            params["date"] = date
            params["more"] = moreDetails
            params["uploader"] = "me"

            CoroutineScope(Dispatchers.IO).launch {

                // Do the POST request and get response
                val response = service.uploadNews(params)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        // Convert raw JSON to pretty JSON using GSON library
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser().parse(
                                response.body()
                                ?.string())
                        )
                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.action_addNewsFragment_to_sentSuccessfullyFragment)
                        }
                        Log.d("Pretty Printed JSON :", prettyJson)

                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.action_addNewsFragment_to_sentFailedFragment)
                        }

                    }
                }
            }

    }

    private fun setupUILanguage() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        language = sharedPreferences.getString("language", "empty").toString()
        when (language) {
            "kanada" -> {
                etHeadline.hint = requireContext().getString(R.string.kannadaHeadline)
                etNews.hint = requireContext().getString(R.string.kannadaNews)
                etMandal.hint = requireContext().getString(R.string.kannadaMandal)
                etDate.hint = requireContext().getString(R.string.kannadaDate)
                etMoreDetails.hint = requireContext().getString(R.string.kannadaMoreDetails)
                submitBtn.text = requireContext().getString(R.string.kannadaSubmit)
                agreementTxt.text = requireContext().getString(R.string.kannadaAllTrue)
            }
            "tamil" -> {
                etHeadline.hint = requireContext().getString(R.string.tamilHeadline)
                etNews.hint = requireContext().getString(R.string.tamilNews)
                etMandal.hint = requireContext().getString(R.string.tamilMandal)
                etDate.hint = requireContext().getString(R.string.tamilDate)
                etMoreDetails.hint = requireContext().getString(R.string.tamilMoreDetails)
                submitBtn.text = requireContext().getString(R.string.tamilSubmit)
                agreementTxt.text = requireContext().getString(R.string.tamilAllTrue)
            }
            else -> {

                etHeadline.hint = requireContext().getString(R.string.teluguHeadline)
                etNews.hint = requireContext().getString(R.string.teluguNews)
                etMandal.hint = requireContext().getString(R.string.teluguMandal)
                etDate.hint = requireContext().getString(R.string.teluguDate)
                etMoreDetails.hint = requireContext().getString(R.string.teluguMoreDetails)
                submitBtn.text = requireContext().getString(R.string.teluguSubmit)
                agreementTxt.text = requireContext().getString(R.string.teluguAllTrue)

            }
        }

    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minutes = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        getDateTimeCalendar()

        DatePickerDialog(requireContext(), this, year, month, day).show()

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minutes, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinutes = minute

        etDate.editText?.setText("$savedDay-$savedMonth-$savedYear  $savedHour:$savedMinutes")
    }

    private fun initialization(view: View) {


        etHeadline = view.findViewById(R.id.etHeadline)
        etNews = view.findViewById(R.id.etNews)
        etMandal = view.findViewById(R.id.etMandal)
        etMoreDetails = view.findViewById(R.id.etMoreDetails)
        etDate = view.findViewById(R.id.etDate)
        agreementTxt = view.findViewById(R.id.agreementTxt)
        checkBox = view.findViewById(R.id.checkBox)
        placeHolder = view.findViewById(R.id.placeholder)


        btnHeadline = view.findViewById(R.id.btnHeadline)
        newsBtn = view.findViewById(R.id.newsBtn)
        mandalBtn = view.findViewById(R.id.mandalBtn)
        moreDetailBtn = view.findViewById(R.id.moreDetailBtn)
        newsImg = view.findViewById(R.id.newsImg)




        submitBtn = view.findViewById(R.id.submitBtn)
    }

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


    private var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val results: ArrayList<String> = data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                when (clickedButton) {
                    "Headline" -> {
                        etHeadline.editText?.setText(Objects.requireNonNull(results)[0])

                    }
                    "News" -> {
                        etNews.editText?.setText(Objects.requireNonNull(results)[0])
                    }
                    "Mandal" -> {
                        etMandal.editText?.setText(Objects.requireNonNull(results)[0])
                    }
                    "MoreDetails" -> {
                        etMoreDetails.editText?.setText(Objects.requireNonNull(results)[0])
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

                    newsImg.setImageBitmap(bitmap)
                    placeHolder.visibility = View.INVISIBLE

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
        encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)


    }

}