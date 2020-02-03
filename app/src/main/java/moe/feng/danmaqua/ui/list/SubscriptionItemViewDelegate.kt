package moe.feng.danmaqua.ui.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.drakeet.multitype.ItemViewDelegate
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moe.feng.danmaqua.R
import moe.feng.danmaqua.model.Subscription
import moe.feng.danmaqua.util.HttpUtils
import moe.feng.danmaqua.util.ext.TAG

class SubscriptionItemViewDelegate(var callback: Callback? = null)
    : ItemViewDelegate<Subscription, SubscriptionItemViewDelegate.ViewHolder>() {

    interface Callback {

        fun onSubscriptionItemClick(item: Subscription)

    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

        val avatarView: ImageView by bindView(R.id.avatarView)
        val avatarRing: ImageView by bindView(R.id.avatarRing)
        val usernameView: TextView by bindView(R.id.usernameView)

        var avatarLoadTask: Job? = null
        lateinit var item: Subscription

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.subscription_item_view, parent, false))
            .apply {
                itemView.setOnClickListener {
                    callback?.onSubscriptionItemClick(item)
                }
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Subscription) = with(holder) {
        this.item = item

        usernameView.text = item.username

        itemView.setBackgroundResource(if (item.selected) {
            R.drawable.subscription_item_background_selected
        } else {
            R.drawable.subscription_item_background_normal
        })
        avatarRing.isVisible = item.selected

        Log.i(TAG, "pos=$adapterPosition name=${item.username}")
        avatarLoadTask?.cancel()
        avatarLoadTask = launch {
            Log.i(TAG, "pos=$adapterPosition name=${item.username} avatar=${item.avatar}")
            avatarView.setImageBitmap(HttpUtils.loadBitmapWithCache(item.avatar))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Subscription, payloads: List<Any>) {
        super.onBindViewHolder(holder, item, payloads)
    }

}