package android.justo.es.anymore

import android.justo.es.anymore.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_false.*


class FalseActivity : AppCompatActivity() {

    lateinit var currentPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_false)

        if(intent.extras != null)
            currentPath = intent.extras["path"] as String

        val bitmap = BitmapFactory.decodeFile(currentPath)
        imageUpload.setImageBitmap(bitmap)

    }
}
