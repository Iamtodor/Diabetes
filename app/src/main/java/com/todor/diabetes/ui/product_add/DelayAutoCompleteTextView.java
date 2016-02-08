package com.todor.diabetes.ui.product_add;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

public class DelayAutoCompleteTextView extends AutoCompleteTextView {

    private static final int     MESSAGE_TEXT_CHANGED       = 100;
    private static final int     DEFAULT_AUTOCOMPLETE_DELAY = 750;
    private final        Handler mHandler                   = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    private int autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private ProgressBar progressBar;

    public DelayAutoCompleteTextView(Context context) {
        super(context);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadingIndicator(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay);
    }

    @Override
    public void onFilterComplete(int count) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}
