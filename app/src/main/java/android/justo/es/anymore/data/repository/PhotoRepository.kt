package android.justo.es.anymore.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.justo.es.anymore.common.Encoder
import android.justo.es.anymore.data.model.*
import android.justo.es.anymore.data.repository.remote.APIClient
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created by justo on 22/03/2019.
 */
class PhotoRepository(activity: AppCompatActivity) {
    private val photoService = APIClient.Single.getPhotoService()
    private val peopleService = APIClient.Single.getPeopleService()
    private val liveDataError = MutableLiveData<Boolean>()

    fun addPerson(name: String, image: File) {
        peopleService.addPerson(AddPersonFirstData(name, 1)).enqueue(object : Callback<AddPersonDataResponse> {
            override fun onFailure(call: Call<AddPersonDataResponse>?, t: Throwable?) {
                Log.d("", t.toString())
            }

            override fun onResponse(call: Call<AddPersonDataResponse>?, response: Response<AddPersonDataResponse>?) {
                Log.d("", response?.body().toString())
                val personId = response?.body()?.insertedId
                val asyncTask = object : AsyncTask<Unit, Unit, Unit>() {
                    override fun doInBackground(vararg params: Unit?) {
                        val image_encoded = Encoder.encodeFileToBase64Binary(image)

                        photoService.addPerson(AddPersonData(person_id = personId!!, image = image_encoded!!)).enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                                Log.d("", t.toString())
                            }

                            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                                Log.d("", response.toString())
                            }

                        })
                    }
                }
                asyncTask.execute()
            }
        })

    }

    fun recognizePerson(image: File): LiveData<Boolean> {
        val asyncTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                val image_encoded = Encoder.encodeFileToBase64Binary(image)

                photoService.recognizePerson(RecognizeData(image_encoded!!)).enqueue(object : Callback<ResponseRecognizeData> {
                    override fun onFailure(call: Call<ResponseRecognizeData>?, t: Throwable?) {
                        Log.d("", t.toString())
                    }

                    override fun onResponse(call: Call<ResponseRecognizeData>?, response: Response<ResponseRecognizeData>?) {
                        Log.d("", response?.body()?.persons.toString())
                    }

                })
            }
        }
        asyncTask.execute()

        return liveDataError
    }

}