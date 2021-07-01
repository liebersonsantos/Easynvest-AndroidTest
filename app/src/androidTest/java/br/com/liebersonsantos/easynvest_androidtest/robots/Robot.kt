package br.com.liebersonsantos.easynvest_androidtest.robots

import android.content.Context
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.liebersonsantos.easynvest_androidtest.R
import br.com.liebersonsantos.easynvest_androidtest.data.ApiService.URL
import br.com.liebersonsantos.easynvest_androidtest.simulator.view.activity.MainActivity
import br.com.liebersonsantos.easynvest_androidtest.util.UiTests
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

/**
 * Created by lieberson on 6/30/21.
 * @author lieberson.xsantos@gmail.com
 */
class Robot(
    private val mockWebServer: MockWebServer?,
    private val activityTestRule: ActivityTestRule<MainActivity>
) {

    fun startActivity() {
        activityTestRule.launchActivity(Intent(Intent.ACTION_MAIN))
    }

    fun finishActivity() {
        activityTestRule.finishActivity()
    }

    fun request(): Robot {
        mockWebServer?.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path?.contains(URL) == true) {
                    return MockResponse().setBody(
                        readFileFromAssets(
                            "response.json",
                            InstrumentationRegistry.getInstrumentation().context
                        )
                    )
                }

                return MockResponse().setBody(
                    readFileFromAssets(
                        "error_response.json",
                        InstrumentationRegistry.getInstrumentation().context
                    )
                )
            }
        }
        return this
    }

    fun checkFieldsIsShowing(): Robot {
        UiTests.checkHintIsDisplayed("R$")
        UiTests.checkHintIsDisplayed("dia/mês/ano")
        UiTests.checkHintIsDisplayed("100%")
        return this
    }

    fun checkCalculate(): Robot {
        UiTests.inputTextWithReplaceById(R.id.edtInvestedAmount, "R$ 100,00")
        UiTests.inputTextWithReplaceById(R.id.edtMaturityDate, "01/07/2021")
        UiTests.inputTextWithReplaceById(R.id.edtRate, "100")
        UiTests.clickById(R.id.btnSimulate)
        UiTests.isTextDisplayed("R$ 31,60")

        return this
    }

    fun checkErrorEmptyValue(): Robot {
        UiTests.inputTextWithReplaceById(R.id.edtMaturityDate, "01/07/2021")
        UiTests.inputTextWithReplaceById(R.id.edtRate, "100")
        UiTests.clickById(R.id.btnSimulate)
        UiTests.checkSnackbar("Valor não deve ser vazio")

        return this
    }

    fun checkCalculateAndBack(): Robot {
        UiTests.inputTextWithReplaceById(R.id.edtInvestedAmount, "R$ 100,00")
        UiTests.inputTextWithReplaceById(R.id.edtMaturityDate, "01/07/2021")
        UiTests.inputTextWithReplaceById(R.id.edtRate, "100")
        UiTests.clickById(R.id.btnSimulate)
        UiTests.sleep()
        UiTests.isTextDisplayed("R$ 31,60")
        UiTests.clickById(R.id.btnSimulateAgain)
        UiTests.isTextDisplayed("Quanto você gostaria de aplicar? *")
        return this
    }

    private fun readFileFromAssets(fileName: String, c: Context): String {
        return try {
            val `is`: InputStream = c.assets.open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}