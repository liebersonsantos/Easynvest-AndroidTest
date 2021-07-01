package br.com.liebersonsantos.easynvest_androidtest.core

import android.app.Application
import br.com.liebersonsantos.easynvest_androidtest.BuildConfig
import timber.log.Timber

/**
 * Created by lieberson on 6/30/21.
 * @author lieberson.xsantos@gmail.com
 */
class CustomApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}