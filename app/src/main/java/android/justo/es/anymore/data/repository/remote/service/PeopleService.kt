package android.justo.es.anymore.data.repository.remote.service

import android.justo.es.anymore.data.model.AddPersonDataResponse
import android.justo.es.anymore.data.model.AddPersonFirstData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by justo on 23/03/2019.
 */
interface PeopleService {
    @POST("persons")
    fun addPerson(@Body data: AddPersonFirstData): Call<AddPersonDataResponse>
}