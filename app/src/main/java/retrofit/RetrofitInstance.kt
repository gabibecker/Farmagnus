
import retrofit2.Retrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit.MedicamentoService
import retrofit2.converter.moshi.MoshiConverterFactory

// Criação do Moshi
val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

// Criando a instância do Retrofit
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.0.102:8080/")  // Substitua pelo seu URL base
    .addConverterFactory(MoshiConverterFactory.create(moshi))  // Usando o Moshi
    .build()

// Aqui você pode criar seu serviço Retrofit
val service: MedicamentoService = retrofit.create(MedicamentoService::class.java)