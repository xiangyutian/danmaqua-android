package moe.feng.danmaqua

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.tencent.mmkv.MMKV
import moe.feng.common.eventshelper.EventsHelper
import moe.feng.common.eventshelper.of
import moe.feng.danmaqua.event.SettingsChangedListener
import moe.feng.danmaqua.model.BlockedTextRule
import moe.feng.danmaqua.util.ext.booleanProperty
import moe.feng.danmaqua.util.ext.intProperty
import moe.feng.danmaqua.util.ext.jsonArrayProperty
import moe.feng.danmaqua.util.ext.notnullStringProperty

/**
 * Danmaqua constants and settings
 */
object Danmaqua {

    const val EXTRA_PREFIX = "${BuildConfig.APPLICATION_ID}.extra"
    const val EXTRA_DATA = "${EXTRA_PREFIX}.DATA"
    const val EXTRA_ACTION = "${EXTRA_PREFIX}.ACTION"
    const val EXTRA_CONNECT_ROOM_ID = "${EXTRA_PREFIX}.CONNECT_ROOM_ID"

    const val ACTION_PREFIX = "${BuildConfig.APPLICATION_ID}.action"

    const val NOTI_CHANNEL_ID_STATUS = "status"
    const val NOTI_ID_LISTENER_STATUS = 10

    const val PENDING_INTENT_REQUEST_STOP = 10
    const val PENDING_INTENT_REQUEST_ENTER_MAIN = 11

    const val DEFAULT_FILTER_PATTERN = "【(.*)】"

    object Settings {

        private val mmkv: MMKV get() = MMKV.defaultMMKV()

        var introduced: Boolean by mmkv.booleanProperty(
            key = "introduced", defaultValue = false
        )

        var enabledAnalytics: Boolean by mmkv.booleanProperty(
            key = "enabled_analytics", defaultValue = true
        )

        object UI {

            var darkMode: Int by mmkv.intProperty(
                key = "dark_mode", defaultValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )

        }

        object Filter {

            var enabled: Boolean by mmkv.booleanProperty(
                key = "filter_enabled", defaultValue = false
            )

            var pattern: String by mmkv.notnullStringProperty(
                key = "filter_pattern", defaultValue = DEFAULT_FILTER_PATTERN
            )

            var blockedTextPatterns: List<BlockedTextRule> by mmkv.jsonArrayProperty(
                key = "filter_blocked_text_rules",
                arrayClass = Array<BlockedTextRule>::class.java
            )

        }

        object Floating {

            var backgroundAlpha: Int by mmkv.intProperty(
                key = "floating_alpha", defaultValue = 255
            )

            var textSize: Int by mmkv.intProperty(
                key = "floating_text_size", defaultValue = 14
            )

            var twoLine: Boolean by mmkv.booleanProperty(
                key = "floating_two_line", defaultValue = false
            )

        }

        fun notifyChanged(context: Context) {
            EventsHelper.getInstance(context)
                .of<SettingsChangedListener>()
                .onSettingsChanged()
        }

    }

}