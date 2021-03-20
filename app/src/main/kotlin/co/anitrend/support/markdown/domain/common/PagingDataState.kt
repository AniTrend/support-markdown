package co.anitrend.support.markdown.domain.common

import androidx.paging.PagingData
import co.anitrend.support.markdown.domain.entities.TextFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PagingDataState(
    val data: Flow<PagingData<TextFeed>> = emptyFlow()
)