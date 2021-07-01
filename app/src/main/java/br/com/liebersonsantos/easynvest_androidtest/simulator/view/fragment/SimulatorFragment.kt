package br.com.liebersonsantos.easynvest_androidtest.simulator.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.liebersonsantos.easynvest_androidtest.R
import br.com.liebersonsantos.easynvest_androidtest.core.Status
import br.com.liebersonsantos.easynvest_androidtest.core.Util.hideKeyboard
import br.com.liebersonsantos.easynvest_androidtest.core.extensions.*
import br.com.liebersonsantos.easynvest_androidtest.data.ApiService
import br.com.liebersonsantos.easynvest_androidtest.data.ApiService.URL
import br.com.liebersonsantos.easynvest_androidtest.databinding.FragmentSimulatorBinding
import br.com.liebersonsantos.easynvest_androidtest.simulator.repository.Repository
import br.com.liebersonsantos.easynvest_androidtest.simulator.viewmodel.SimulatorViewModel
import br.com.liebersonsantos.easynvest_androidtest.simulator.viewmodel.SimulatorViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

const val SIMULATE_RESPONSE = "SIMULATE_RESPONSE"

class SimulatorFragment : Fragment(R.layout.fragment_simulator) {
    private var _binding: FragmentSimulatorBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SimulatorViewModel by lazy {
        val factory = SimulatorViewModelFactory(Dispatchers.IO, Repository(ApiService.service(URL)))
        ViewModelProvider(this, factory).get(SimulatorViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtInvestedAmount.moneyMasK()
        binding.edtMaturityDate.dateMask()

        binding.btnSimulate.setOnClickListener {
            if (validateAmount()) {
                viewModel.getSimulate(
                    investedAmount = binding.edtInvestedAmount.unMaskMoney(),
                    rate = binding.edtRate.unMaskMoney().toLong(),
                    maturityDate = binding.edtMaturityDate.getDateFormatted()
                )
            }

            hideKeyboard(view)
        }

        observeResponse()

    }

    private fun observeResponse() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) {
                return@Observer
            }
            binding.progressBar.visibility = if (it.loading == true) View.VISIBLE else View.GONE
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { simulateResponse ->
                        Timber.tag("RESPONSE").i("${simulateResponse.grossAmount}")
                        findNavController().navigate(
                            R.id.action_simulatorFragment_to_simulatorResultFragment,
                            Bundle().apply {
                                putParcelable(SIMULATE_RESPONSE, it.data)
                            })
                        cleanInvestmentAmount()
                    }
                }
                Status.ERROR -> { snackbar(getString(R.string.simulate_error)) }
                Status.LOADING -> { }
            }
        })
    }

    private fun cleanInvestmentAmount() {
        binding.edtInvestedAmount.setText("")
    }

    private fun validateAmount(): Boolean {
        if (binding.edtInvestedAmount.unMaskMoney() <= 0){
            snackbar(getString(R.string.simulation_amount))
            binding.edtInvestedAmount.requestFocus()
            return false
        }

        return true
    }

    private fun snackbar(message: String){
        val snackbar = Snackbar.make(binding.btnSimulate, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.setText(message)
        snackbar.setAction(getString(R.string.ok), null)
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}