package com.example.storyapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.storyapp.R

class EditTextEmail : AppCompatEditText {
    private lateinit var editTextBackground: Drawable
    private lateinit var editTextErrorBackground: Drawable
    private var isError = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isError) editTextErrorBackground else editTextBackground
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            if (!TextUtils.isEmpty(text) && text.toString().isValidEmail()) {
                error = null
                isError = false
            } else {
                error = resources.getString(R.string.invalid_email)
                isError = true
            }
        })
    }

    private fun init() {
        editTextBackground = ContextCompat.getDrawable(context, R.drawable.bg_edt_text) as Drawable
        editTextErrorBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_edt_text_error) as Drawable
    }

    private fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}