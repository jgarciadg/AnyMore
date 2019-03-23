package android.justo.es.anymore.data.repository.remote

import android.justo.es.anymore.data.repository.remote.service.PeopleService
import android.justo.es.anymore.data.repository.remote.service.PhotoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by justo on 15/03/2019.
 */
class APIClient {
    object Single {
        private val BASE_URL = "http://qss.unex.es:2070/"

        private fun getRetrofit(baseUrl: String = BASE_URL): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        fun getPhotoService(): PhotoService {
            return getRetrofit("http://qss.unex.es:2070/").create(PhotoService::class.java)
        }

        fun getPeopleService(): PeopleService {
            return getRetrofit("http://qapps.unex.es:7777/").create(PeopleService::class.java)
        }


    }
}