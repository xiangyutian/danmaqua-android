package moe.feng.danmaqua.ui.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.google.androidbrowserhelper.trusted.TwaLauncher
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.contact_author_dialog_layout.*
import moe.feng.danmaqua.R
import moe.feng.danmaqua.util.IntentUtils
import java.lang.Exception

class ContactAuthorDialogFragment : BaseBottomSheetDialogFragment() {

    private val email: String get() = getString(R.string.about_author_email_summary)
        .filter { it != '_' }.replace('#', '@')

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_author_dialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get()
            .load("https://avatars.githubusercontent.com/fython")
            .placeholder(R.drawable.avatar_placeholder_empty)
            .into(avatarView)

        emailSummary.text = email

        weiboButton.setOnClickListener {
            context?.let {
                TwaLauncher(it).launch(getString(R.string.about_author_weibo_url).toUri())
            }
            dismiss()
        }
        bilibiliButton.setOnClickListener {
            context?.let {
                TwaLauncher(it).launch(getString(R.string.about_author_bilibili_url).toUri())
            }
            dismiss()
        }
        emailButton.setOnClickListener {
            context?.let {
                try {
                    it.startActivity(IntentUtils.sendMail(email))
                } catch (e: Exception) {
                    val cm = it.getSystemService<ClipboardManager>()
                    cm?.setPrimaryClip(ClipData.newPlainText("email", email))
                    Toast.makeText(it, R.string.toast_copied_to_clipboard, Toast.LENGTH_LONG).show()
                }
            }
            dismiss()
        }
    }

}