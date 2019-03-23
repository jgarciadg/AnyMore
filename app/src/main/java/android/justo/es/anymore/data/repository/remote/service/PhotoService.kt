package android.justo.es.anymore.data.repository.remote.service

import android.justo.es.anymore.data.model.AddPersonData
import android.justo.es.anymore.data.model.RecognizeData
import android.justo.es.anymore.data.model.ResponseRecognizeData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by justo on 22/03/2019.
 */
interface PhotoService {
    @POST("addPerson")
    fun addPerson(@Body data: AddPersonData): Call<Void>

    @POST(".")
    fun recognizePerson(@Body data: RecognizeData): Call<ResponseRecognizeData>
}