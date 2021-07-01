package br.com.liebersonsantos.easynvest_androidtest.simulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.liebersonsantos.easynvest_androidtest.simulator.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
class SimulatorViewModelFactory(
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: Repository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimulatorViewModel::class.java)) {
            return SimulatorViewModel(ioDispatcher, repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}
