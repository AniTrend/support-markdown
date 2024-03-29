package co.anitrend.support.markdown.data.koin

import co.anitrend.support.markdown.data.repository.GetFeedTextPagedRepository
import co.anitrend.support.markdown.data.usecase.TextFeedUseCase
import co.anitrend.support.markdown.domain.interactor.GetTextFeedPaged
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private val useCaseModule = module {
    single<GetTextFeedPaged> {
        TextFeedUseCase(
            repository = get()
        )
    }
}

private val repositoryModule = module {
    factory {
        GetFeedTextPagedRepository(
            client = get()
        )
    }
}

private val networkModule = module {
    single {
        OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    single {
        ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .okHttpClient(get())
            .build()
    }
}

val dataModules = listOf(useCaseModule, repositoryModule, networkModule)