package br.com.liebersonsantos.easynvest_androidtest.core.extensions

import android.widget.TextView
import br.com.liebersonsantos.easynvest_androidtest.core.Util
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */

fun TextView.formatValueCurrency(value: Double?) {
    this.text = "R$ ${String.format(Locale("pt", "BR"), "%.2f", value)}"
}

fun TextView.formatValue(value: Double?) {
    this.text = "${String.format(Locale("pt", "BR"), "%.2f", value)}%"
}

fun TextView.parseDate(stringDate: String) {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Util.defaultLocale)
    val format2 = SimpleDateFormat("dd/MM/yyyy", Util.defaultLocale)
    val date = format.parse(stringDate)
    this.text = format2.format(date)
}