package com.example.storyapp.ui.addstory

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.example.storyapp.Locator
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityAddStoryBinding
import com.example.storyapp.utils.*
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AddStoryViewModel>(factoryProducer = { Locator.addStoryViewModelFactory })
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latLng: LatLng? = null
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private lateinit var desc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        desc = binding.edAddDescription
        setMyButtonEnable()

        desc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        if (!REQUIRED_CAMERA_PERMISSIONS.checkPermissionsGranted(baseContext)) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_CAMERA_PERMISSIONS, REQUEST_CODE_CAMERA_PERMISSION
            )
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btGallery.setOnClickListener { startGallery() }
        binding.swShareLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLastLocation()
            }
        }
        binding.btCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.buttonAdd.setOnClickListener {
            if (getFile != null) {
                getFile?.let {
                    viewModel.addStory(
                        it.reduceFileImage(), binding.edAddDescription.text.toString(), latLng
                    )
                }
            } else {
                Toast.makeText(this, getString(R.string.choose_image), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.addStoryState.launchAndCollectIn(this@AddStoryActivity) { state ->
            when (state.resultAddStory) {
                is ResultState.Success<String> -> {
                    binding.buttonAdd.setLoading(false)
                    Toast.makeText(
                        this@AddStoryActivity,
                        getString(R.string.add_story_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                is ResultState.Loading -> binding.buttonAdd.setLoading(true)
                is ResultState.Error -> {
                    binding.buttonAdd.setLoading(false)
                    Toast.makeText(
                        this@AddStoryActivity, state.resultAddStory.message, Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile.reduceFileImage()
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.ivPreviewPhoto.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = selectedImg.uriToFile(this@AddStoryActivity)
            getFile = myFile
            binding.ivPreviewPhoto.setImageURI(selectedImg)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            else -> {
                binding.swShareLocation.isChecked = false
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (!REQUIRED_CAMERA_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.didnt_get_camera_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        } else if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS) {
            if (!REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.didnt_get_location_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity, packageName, it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latLng = LatLng(location.latitude, location.longitude)
                } else {
                    binding.swShareLocation.isChecked = false
                    Toast.makeText(
                        this@AddStoryActivity,
                        getString(R.string.cant_found_location),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setMyButtonEnable() {
        val result = binding.edAddDescription.text
        if (result != null && result.toString().isNotEmpty()) {
            binding.buttonAdd.isEnabled = true
            binding.buttonAdd.setText(getString(R.string.upload))
        } else {
            binding.buttonAdd.isEnabled = false
            binding.buttonAdd.setText(getString(R.string.fill_description))
        }
        binding.buttonAdd.isEnabled = result != null && result.toString().isNotEmpty()
    }

    companion object {
        private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private val REQUIRED_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_CAMERA_PERMISSION = 10
        private const val REQUEST_CODE_LOCATION_PERMISSIONS = 11
    }
}