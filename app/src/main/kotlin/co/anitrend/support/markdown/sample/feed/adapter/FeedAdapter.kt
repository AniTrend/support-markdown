package co.anitrend.support.markdown.sample.feed.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.anitrend.support.markdown.core.extensions.onDestroy
import co.anitrend.support.markdown.core.extensions.setMarkdown
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.sample.databinding.AdapterFeedBinding
import coil.load
import coil.request.Disposable
import coil.transform.CircleCropTransformation
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.MutableStateFlow

internal class FeedAdapter(
    private val markwon: Markwon
) : PagingDataAdapter<TextFeed, FeedAdapter.ViewHolder>(Companion) {

    val clickState = MutableStateFlow<TextFeed?>(null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFeedBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getBindingAdapterPosition] which
     * will have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(markwon, item, clickState)
    }

    /**
     * Called when a view created by this adapter has been recycled.
     *
     *
     * A view is recycled when a [LayoutManager] decides that it no longer
     * needs to be attached to its parent [RecyclerView]. This can be because it has
     * fallen out of visibility or a set of cached views represented by views still
     * attached to the parent RecyclerView. If an item view has large or expensive data
     * bound to it such as large bitmaps, this may be a good place to release those
     * resources.
     *
     *
     * RecyclerView calls this method right before clearing ViewHolder's internal data and
     * sending it to RecycledViewPool. This way, if ViewHolder was holding valid information
     * before being recycled, you can call [ViewHolder.getBindingAdapterPosition] to get
     * its adapter position.
     *
     * @param holder The ViewHolder for the view being recycled
     */
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    class ViewHolder(
        private val binding: AdapterFeedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var disposable: Disposable? = null

        fun onBind(markwon: Markwon, feed: TextFeed?, clickState: MutableStateFlow<TextFeed?>) {
            disposable = binding.composerAvatar.load(
                feed?.user?.avatar?.medium
            ) {
                transformations(
                    CircleCropTransformation()
                )
            }

            binding.composerAvatar.setOnClickListener {
                clickState.value = feed
            }

            binding.composer.text = feed?.user?.name
            binding.feedTime.text = feed?.createdAt
            val markDownText = feed?.text ?: "**No content available**"

            Log.i(javaClass.simpleName, "${feed?.user?.id} | ${feed?.user?.name}")
            Log.i(javaClass.simpleName, "${feed?.text}")
            binding.feedText.setMarkdown(markwon, markDownText)
        }

        fun onRecycled() {
            binding.composerAvatar.setOnClickListener(null)
            binding.feedText.onDestroy()
            disposable?.dispose()
            disposable = null
        }
    }

    private companion object : DiffUtil.ItemCallback<TextFeed>() {
        override fun areItemsTheSame(
            oldItem: TextFeed,
            newItem: TextFeed
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TextFeed,
            newItem: TextFeed
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.text == newItem.text
        }
    }
}