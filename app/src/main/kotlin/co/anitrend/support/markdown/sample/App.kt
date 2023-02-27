package co.anitrend.support.markdown.sample

import android.app.Application
import co.anitrend.support.markdown.sample.koin.appModules
import coil.ImageLoader
import coil.ImageLoaderFactory
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            fragmentFactory()
            modules(appModules)
        }
    }

    /**
     * Return a new [ImageLoader].
     */
    override fun newImageLoader() = get<ImageLoader>()
}