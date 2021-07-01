package br.com.liebersonsantos.easynvest_androidtest.simulator.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.liebersonsantos.easynvest_androidtest.core.extensions.formatValue
import br.com.liebersonsantos.easynvest_androidtest.core.extensions.formatValueCurrency
import br.com.liebersonsantos.easynvest_androidtest.core.extensions.parseDate
import br.com.liebersonsantos.easynvest_androidtest.databinding.FragmentSimulatorResultBinding
import br.com.liebersonsantos.easynvest_androidtest.simulator.model.SimulatorResponse

class SimulatorResultFragment : Fragment() {
    private var _binding: FragmentSimulatorResultBinding? = null
    private val binding
    get() = _binding!!

    private var simulatorResponse: SimulatorResponse? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSimulatorResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simulatorResponse = arguments?.getParcelable(SIMULATE_RESPONSE)
        simulatorResponse?.let { fillDataResponse(it) }

        binding.btnSimulateAgain.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun fillDataResponse(simulatorResponse: SimulatorResponse){
        binding.txtResult.formatValueCurrency(simulatorResponse.grossAmount)
        binding.txtInvestedAmount.formatValueCurrency(simulatorResponse.investmentParameter.investedAmount)
        binding.txtGrossAmount.formatValueCurrency(simulatorResponse.grossAmount)
        binding.txtGrossAmountProfitLabel.formatValueCurrency(simulatorResponse.grossAmountProfit)
        binding.txtGrossAmountProfit.formatValueCurrency(simulatorResponse.grossAmountProfit)
        binding.txtTaxesAmount.formatValueCurrency(simulatorResponse.taxesAmount)
        binding.txtTaxesAmount.text = "${binding.txtTaxesAmount.text} [${simulatorResponse.taxesRate}%]"
        binding.txtNetAmount.formatValueCurrency(simulatorResponse.netAmount)
        binding.txtMaturityDate.parseDate(simulatorResponse.investmentParameter.maturityDate)
        binding.txtMaturityTotalDays.text = simulatorResponse.investmentParameter.maturityTotalDays.toString()
        binding.txtMonthlyGrossRateProfit.formatValue(simulatorResponse.monthlyGrossRateProfit)
        binding.txtRate.text = "${simulatorResponse?.investmentParameter.rate}%"
        binding.txtAnnualGrossRateProfit.formatValue(simulatorResponse.annualGrossRateProfit)
        binding.txtRateProfit.formatValue(simulatorResponse.rateProfit)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}