package br.com.liebersonsantos.easynvest_androidtest.simulator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.liebersonsantos.easynvest_androidtest.core.State
import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse
import br.com.liebersonsantos.easynvest_androidtest.simulator.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
class SimulatorViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: Repository) : ViewModel() {

    private val _response = MutableLiveData<State<SimulatorResponse>>()
    val response: LiveData<State<SimulatorResponse>>
        get() = _response

    fun getSimulate(investedAmount: Double?, rate: Long?, maturityDate: String?) {
        viewModelScope.launch {
            try {
                _response.value = State.loading(true)
                val response = withContext(ioDispatcher) {
                    repository.simulate(
                        investedAmount, "CDI", rate, false, maturityDate
                    )
                }

                _response.value = State.success(response)
            } catch (throwable: Throwable) {
                _response.value = State.error(throwable)
            }
        }
    }
}