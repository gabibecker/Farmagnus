package retrofit

import model.Medicamento
import retrofit2.http.GET

interface MedicamentoService {

    @GET("medicamento")
    suspend fun getAll(): List<Medicamento>
}