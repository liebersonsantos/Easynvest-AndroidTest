package br.com.liebersonsantos.easynvest_androidtest.simulator.repository

import br.com.liebersonsantos.easynvest_androidtest.data.Api
import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
class Repository(private val api : Api) {
    suspend fun simulate(investedAmount: Double?, index: String?, rate: Long?, isTaxFree: Boolean?, maturityDate: String?): SimulatorResponse =
        api.simulate(investedAmount, index, rate, isTaxFree, maturityDate)
}