package br.com.liebersonsantos.easynvest_androidtest.simulator.view.fragment

import androidx.test.rule.ActivityTestRule
import br.com.liebersonsantos.easynvest_androidtest.data.ApiService
import br.com.liebersonsantos.easynvest_androidtest.robots.Robot
import br.com.liebersonsantos.easynvest_androidtest.simulator.view.activity.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by lieberson on 6/30/21.
 *
 * @author lieberson.xsantos@gmail.com
 */
class SimulatorFragmentTest {
    private lateinit var robot: Robot
    private var mockWebServer: MockWebServer? = null

    init {
        mockWebServer = MockWebServer()
        ApiService.service(mockWebServer?.url("/").toString())
    }

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        robot = Robot(mockWebServer, activityRule)
    }

    @After
    fun finishActivity() {
        mockWebServer?.shutdown();
        robot.finishActivity()
    }

    @Test
    fun testFieldsAreShowingOnScreen() {
        robot.startActivity()
        robot.checkFieldsIsShowing()
    }

    @Test
    fun testErrorOnSendEmptyValue() {
        robot.startActivity()
        robot.checkErrorEmptyValue()
    }

    @Test
    fun testSuccessOnSendCorrectValue() {
        robot.request()
        robot.startActivity()
        robot.checkCalculate()
    }

    @Test
    fun testSuccessOnSendCorrectValueAndBack() {
        robot.request()
        robot.startActivity()
        robot.checkCalculateAndBack()
    }

    @Test
    fun testRunAllCheckRobots() {
        robot.request()
        robot.startActivity()
        robot.checkFieldsIsShowing()
            .checkErrorEmptyValue()
            .checkCalculate()
    }
}