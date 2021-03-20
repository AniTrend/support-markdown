package co.anitrend.support.markdown.sample.koin

import android.os.Build.VERSION.SDK_INT
import androidx.core.content.ContextCompat
import co.anitrend.support.markdown.center.CenterPlugin
import co.anitrend.support.markdown.core.CorePlugin
import co.anitrend.support.markdown.core.plugin.CoilStorePlugin
import co.anitrend.support.markdown.data.koin.dataModules
import co.anitrend.support.markdown.ephasis.EmphasisPlugin
import co.anitrend.support.markdown.heading.HeadingPlugin
import co.anitrend.support.markdown.image.ImagePlugin
import co.anitrend.support.markdown.link.LinkifyPlugin
import co.anitrend.support.markdown.sample.R
import co.anitrend.support.markdown.sample.component.MainActivity
import co.anitrend.support.markdown.sample.feed.FeedFragment
import co.anitrend.support.markdown.sample.feed.adapter.FeedAdapter
import co.anitrend.support.markdown.sample.feed.viewmodel.FeedViewModel
import co.anitrend.support.markdown.sample.feed.viewmodel.contract.AbstractFeedViewModel
import co.anitrend.support.markdown.spoiler.SpoilerPlugin
import co.anitrend.support.markdown.webm.WebMPlugin
import co.anitrend.support.markdown.youtube.YouTubePlugin
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.coil.CoilImagesPlugin
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val coreModule = module {
    single {
        val context = androidContext()
        val color = ContextCompat.getColor(
                context,
                R.color.colorAccent
            )

        val radius = context.resources.getDimensionPixelSize(
            R.dimen.margin_md
        ).toFloat()

        Markwon.builder(context)
            .usePlugin(HtmlPlugin.create())
            .usePlugin(CorePlugin.create())
            .usePlugin(LinkifyPlugin.create())
            .usePlugin(HeadingPlugin.create())
            .usePlugin(EmphasisPlugin.create())
            .usePlugin(CenterPlugin.create())
            .usePlugin(ImagePlugin.create())
            .usePlugin(WebMPlugin.create())
            .usePlugin(YouTubePlugin.create())
            .usePlugin(SpoilerPlugin.create(color))
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(
                CoilImagesPlugin.create(
                    CoilStorePlugin.create(
                        ImageRequest.Builder(context)
                            .transformations(RoundedCornersTransformation(radius))
                    ),
                    get()
                )
            )
            .build()
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
            }
            .componentRegistry {
                if (SDK_INT >= 28)
                    add(ImageDecoderDecoder())
                else
                    add(GifDecoder())
            }.build()
    }
}

val appModules = listOf(coreModule, viewModelModule, fragmentModule, imageLoaderModule) + dataModules