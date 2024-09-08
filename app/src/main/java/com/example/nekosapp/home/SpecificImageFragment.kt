package com.example.nekosapp.home

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.nekosapp.MainActivity
import com.example.nekosapp.R
import com.example.nekosapp.databinding.FragmentSpecificImageBinding
import com.example.nekosapp.utils.ImageUtils
import okio.IOException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class SpecificImageFragment : Fragment() {

    private var _binding: FragmentSpecificImageBinding? = null
    private val binding get() = _binding!!

    private val args: SpecificImageFragmentArgs by navArgs()

    private val specificImgViewModel: SpecificImageViewModel by viewModels()

    /** Lifecycle Methods */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpecificImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Start immersive mode
        (activity as MainActivity).enableImageImmersiveMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Download image setup
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        // Get selected image id and display image
        specificImgViewModel.retrieveSpecificImage(args.specificImageId)
        specificImgViewModel.specificItem.observe(viewLifecycleOwner) {
            it.imageUrl?.let { url ->
                // Display image in imageview
                val imgUri = url.toUri().buildUpon().scheme("https").build()
                binding.specificImg.load(imgUri)

                // Download button
                binding.downloadBtn.setOnClickListener {
                    executor.execute {
                        val bitmapImg = loadImage(url)
                        handler.post {
                            if (bitmapImg != null) {
                                saveMediaToStorage(bitmapImg)
                            }
                        }
                    }
                }
            }
        }

        // Handle status
        specificImgViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                SpecificImageStatus.LOADING -> {
                    binding.loadingAnim.setImageResource(R.drawable.loading_animation)
                    ImageUtils.changeImageViewSize(binding.loadingAnim, 200f, 200f)
                    binding.loadingAnim.visibility = View.VISIBLE

                    binding.errorMessage.visibility = View.GONE
                }

                SpecificImageStatus.ERROR -> {
                    binding.loadingAnim.setImageResource(R.drawable.ic_connection_error)
                    ImageUtils.changeImageViewSize(binding.loadingAnim, 70f, 70f)

                    binding.errorMessage.visibility = View.VISIBLE

                    binding.downloadBtn.visibility = View.GONE
                }

                else -> {
                    binding.loadingAnim.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                }
            }
        }

        // Back button
        binding.backBtn.setOnClickListener {
            // When you use navigateUp(), the previous fragment is popped back to the
            // top of the stack. This means that the fragment isn't destroyed and recreated;
            // instead, its state is preserved as it was when it was pushed onto the back stack.
            this.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // End immersive mode
        (activity as MainActivity).disableImageImmersiveMode()

        _binding = null
    }

    /** Other Methods */

    private fun loadImage(string: String?): Bitmap? {
        val url = convertStringToUrl(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            Log.d(TAG, "Error: ${e.message}")
            Toast.makeText(requireContext(), "Error loading image.", Toast.LENGTH_SHORT).show()
        }
        return null
    }

    private fun convertStringToUrl(string: String?): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            Log.d(TAG, "Error: ${e.message}")
        }
        return null
    }

    private fun saveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requireContext().contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image.jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(requireContext(), "Saved to gallery.", Toast.LENGTH_SHORT).show()
        }
    }

    /** Constants */

    companion object {
        const val TAG = "SpecificImageFragment"
    }
}