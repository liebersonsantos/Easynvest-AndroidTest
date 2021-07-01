package br.com.liebersonsantos.easynvest_androidtest.simulator.repository

import br.com.liebersonsantos.easynvest_androidtest.data.Api
import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse
import com.google.common.truth.Truth
import com.google.gson.Gson
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by lieberson on 6/30/21.
 *
 * @author lieberson.xsantos@gmail.com
 */
@ExperimentalCoroutinesApi
class RepositoryTest : TestCase() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val api = Mockito.mock(Api::class.java)

    @Test
    fun `test response repository`() = testCoroutineDispatcher.runBlockingTest {
        val repository = Repository(api)
        val response = mockResponse()
        testCoroutineDispatcher.pauseDispatcher()

        Mockito.doReturn(response).`when`(api)
            .simulate(100.0, "CDI", 100, false, "2021-06-30")

        repository.simulate(100.0, "CDI", 100, false, "2021-06-30").let {
            Truth.assertThat(it).isEqualTo(response)
        }
    }

    private fun mockResponse(): SimulatorResponse =
        Gson().fromJson("{\"investmentParameter\":{\"investedAmount\":100.0,\"yearlyInterestRate\":4.8731,\"maturityTotalDays\":1096,\"maturityBusinessDays\":777,\"maturityDate\":\"2023-06-03T00:00:00\",\"rate\":100.0,\"isTaxFree\":false},\"grossAmount\":231.60,\"taxesAmount\":4.74,\"netAmount\":226.86,\"grossAmountProfit\":31.60,\"netAmountProfit\":26.86,\"annualGrossRateProfit\":15.80,\"monthlyGrossRateProfit\":0.4,\"dailyGrossRateProfit\":0.000188830770053494,\"taxesRate\":15.0,\"rateProfit\":4.8731,\"annualNetRateProfit\":13.43}",
            SimulatorResponse::class.java)
}