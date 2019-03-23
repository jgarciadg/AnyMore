package android.justo.es.anymore.common

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Created by justo on 22/03/2019.
 */
object Encoder {
    fun encodeFileToBase64Binary(file: File): String? {
        var encodedfile: String? = null
        try {
            val fileInputStreamReader = FileInputStream(file)
            val bytes = ByteArray(file.length().toInt())
            fileInputStreamReader.read(bytes)
            encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return encodedfile
    }
}