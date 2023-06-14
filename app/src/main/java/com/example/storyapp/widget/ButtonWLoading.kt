package com.example.storyapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.storyapp.R


class ButtonWLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val progressBar: LottieAnimationView
    private val buttonTextView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.button_w_loading, this, true)
        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_indicator)
        loadAttr(attrs, defStyleAttr)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        buttonTextView.isEnabled = enabled
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonWLoading,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.ButtonWLoading_text)
        val loading = arr.getBoolean(R.styleable.ButtonWLoading_loading, false)
        val enabled = arr.getBoolean(R.styleable.ButtonWLoading_enabled, true)
        val lottieResId = arr.getResourceId(R.styleable.ButtonWLoading_lottie_resId, R.raw.lottie_loader)
        arr.recycle()
        isEnabled = enabled
        buttonTextView.isEnabled = enabled
        setText(buttonText)
        progressBar.setAnimation(lottieResId)
        setLoading(loading)
    }

    fun setLoading(loading: Boolean){
        isClickable = !loading
        if(loading){
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    fun setText(text : String?) {
        buttonTextView.text = text
    }

}