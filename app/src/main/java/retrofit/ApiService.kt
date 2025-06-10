package retrofit

import model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    fun createUser(@Body user: User): Call<User>

//    @GET("users/{userId}")
//    fun getUser(@Path("userId") userId: String): Call<User>
}
