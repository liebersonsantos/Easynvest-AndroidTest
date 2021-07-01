package br.com.liebersonsantos.easynvest_androidtest.data

import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
const val SIMULATE = "ecfaebf5-782b-4b24-ae4f-23b5c3a861da"

interface Api {
    @GET(SIMULATE)
    suspend fun simulate(
        @Query("investedAmount") investedAmount: Double?,
        @Query("index") index: String?,
        @Query("rate") rate: Long?,
        @Query("isTaxFree") isTaxFree: Boolean?,
        @Query("maturityDate") maturityDate: String?
    ): SimulatorResponse
}