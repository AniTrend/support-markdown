package co.anitrend.support.markdown.sample.koin

import android.text.util.Linkify
import androidx.core.content.ContextCompat
import co.anitrend.support.markdown.core.CoreDelimiterPlugin
import co.anitrend.support.markdown.data.koin.dataModules
import co.anitrend.support.markdown.html.AlignTagHandler
import co.anitrend.support.markdown.html.CenterTagHandler
import co.anitrend.support.markdown.image.ImagePlugin
import co.anitrend.support.markdown.sample.R
import co.anitrend.support.markdown.sample.component.MainActivity
import co.anitrend.support.markdown.sample.feed.FeedFragment
import co.anitrend.support.markdown.sample.feed.adapter.FeedAdapter
import co.anitrend.support.markdown.sample.feed.viewmodel.FeedViewModel
import co.anitrend.support.markdown.sample.feed.viewmodel.contract.AbstractFeedViewModel
import co.anitrend.support.markdown.util.UtilityPlugin
import co.anitrend.support.markdown.video.WebMPlugin
import co.anitrend.support.markdown.video.YouTubePlugin
import coil.ImageLoader
import coil.util.CoilUtils
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val coreModule = module {
    single {
        val context = androidContext()
        Markwon.builder(context)
            .usePlugin(
                CoreDelimiterPlugin.create(
                ContextCompat.getColor(
                    context,
                    R.color.colorAccent
                )
            ))
            .usePlugin(UtilityPlugin.create())
            //.usePlugin(ImagePlugin.create())
            //.usePlugin(WebMPlugin.create())
            //.usePlugin(YouTubePlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(LinkifyPlugin.create(Linkify.WEB_URLS))
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(
                HtmlPlugin.create { plugin -> plugin
                    .addHandler(AlignTagHandler())
                    .addHandler(CenterTagHandler())
                }
            ).build()
    }
}

private val viewModelModule = module {
    viewModel<AbstractFeedViewModel> {
        FeedViewModel(
            interactor = get()
        )
    }
}

private val fragmentModule = module {
    scope<MainActivity> {
        fragment {
            FeedFragment(
                adapter = FeedAdapter(
                    markwon = get()
                )
            )
        }
    }
}

private val imageLoaderModule = module {
    factory {
        val context = androidContext()
        ImageLoader.Builder(context)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(
                        CoilUtils.createDefaultCache(context)
                    )
                    .build()
            }.build()
    }
}

val appModules = listOf(coreModule, viewModelModule, fragmentModule, imageLoaderModule) + dataModules