package br.com.liebersonsantos.easynvest_androidtest.simulator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.liebersonsantos.easynvest_androidtest.core.State
import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse
import br.com.liebersonsantos.easynvest_androidtest.simulator.repository.Repository
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.exceptions.base.MockitoException
import java.io.IOException

/**
 * Created by lieberson on 6/30/21.
 *
 * @author lieberson.xsantos@gmail.com
 */
@ExperimentalCoroutinesApi
class SimulatorViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private var repository = mock(Repository::class.java)
    private lateinit var viewModel: SimulatorViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SimulatorViewModel(testCoroutineDispatcher, repository)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun `testing calculate loading`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doReturn(mockResponse()).`when`(repository)
            .simulate(100.0, "CDI", 100, false, "2021-06-30")

        viewModel.getSimulate(100.0, 100, "2021-06-30")
        assertThat(viewModel.response.value).isEqualTo(State.loading<SimulatorResponse>(true))
    }

    @Test
    fun `testing loading success`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doReturn(mockResponse()).`when`(repository)
            .simulate(100.0, "CDI", 100, false, "2021-06-30")

        viewModel.getSimulate(100.0, 100, "2021-06-30")
        assertThat(viewModel.response.value).isEqualTo(State.loading<SimulatorResponse>(true))

        testCoroutineDispatcher.resumeDispatcher()
        assertThat(viewModel.response.value).isEqualTo(State.success(mockResponse()))
    }

    @Test(expected = MockitoException::class)
    fun `testing data error state`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doThrow(IOException::class.java).`when`(repository)
            .simulate(100.0, "CDI", 100, false, "2021-06-30")

        viewModel.getSimulate(100.0, 100, "2021-06-30")
        assertThat(viewModel.response.value).isEqualTo(State.loading<SimulatorResponse>(true))

        testCoroutineDispatcher.resumeDispatcher()
        assertThat(viewModel.response.value).isEqualTo(State.error<SimulatorResponse>(IOException()))
    }

    private fun mockResponse(): SimulatorResponse =
        Gson().fromJson("{\"investmentParameter\":{\"investedAmount\":100.0,\"yearlyInterestRate\":4.8731,\"maturityTotalDays\":1096,\"maturityBusinessDays\":777,\"maturityDate\":\"2023-06-03T00:00:00\",\"rate\":100.0,\"isTaxFree\":false},\"grossAmount\":231.60,\"taxesAmount\":4.74,\"netAmount\":226.86,\"grossAmountProfit\":31.60,\"netAmountProfit\":26.86,\"annualGrossRateProfit\":15.80,\"monthlyGrossRateProfit\":0.4,\"dailyGrossRateProfit\":0.000188830770053494,\"taxesRate\":15.0,\"rateProfit\":4.8731,\"annualNetRateProfit\":13.43}",
            SimulatorResponse::class.java)

}