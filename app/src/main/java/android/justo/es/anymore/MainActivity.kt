package android.justo.es.anymore

import android.content.Intent
import android.content.SharedPreferences
import android.justo.es.anymore.data.repository.PhotoRepository
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var currentPhotoPath: String
    private var photoRepository = PhotoRepository(this)
    private lateinit var sharedPreferences: SharedPreferences
    private var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (sharedPreferences.getString("name", "").isNotEmpty()) {
            firstTime = false
            dispatchTakePictureIntent()
        }

        buttonContinue.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    val REQUEST_TAKE_PHOTO = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "android.justo.es.anymore.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       /* if (requestCode == REQUEST_TAKE_PHOTO) {
            val imgFile = File(currentPhotoPath)

            if (firstTime) {
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val name = nameEditText.text.toString() + timeStamp

                sharedPreferences.edit().putString("name", name).commit()

                photoRepository.addPerson(name, imgFile)
            } else {
                showLoading()
                photoRepository.recognizePerson(imgFile)
            }
        }*/

        correctLogin()
    }

    fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun correctLogin() {
        val intent = Intent(this, CorrectActivity::class.java)
        startActivity(intent)
    }

    fun incorrectLogin() {
        val intent = Intent(this, FalseActivity::class.java)
        intent.putExtra("path", currentPhotoPath)
        startActivity(intent)
    }
}
