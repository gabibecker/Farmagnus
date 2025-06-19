package retrofit

import model.LoginRequest
import model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("usuarios")
    fun createUser(@Body user: User): Call<User>

    @POST("usuarios/login")
    suspend fun loginUser(@Body credentials: LoginRequest): Response<Boolean>

}
