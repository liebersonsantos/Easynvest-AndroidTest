package br.com.liebersonsantos.easynvest_androidtest.simulator.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
@Parcelize
data class InvestmentParameter(
    @SerializedName("rate")
    val rate: Int = 0,
    @SerializedName("maturityDate")
    val maturityDate: String = "",
    @SerializedName("maturityBusinessDays")
    val maturityBusinessDays: Int = 0,
    @SerializedName("investedAmount")
    val investedAmount: Double = 0.0,
    @SerializedName("maturityTotalDays")
    val maturityTotalDays: Int = 0,
    @SerializedName("isTaxFree")
    val isTaxFree: Boolean = false,
    @SerializedName("yearlyInterestRate")
    val yearlyInterestRate: Double = 0.0
) : Parcelable