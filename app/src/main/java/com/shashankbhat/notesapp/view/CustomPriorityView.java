package com.shashankbhat.notesapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.shashankbhat.notesapp.R;

/**
 * Created by SHASHANK BHAT on 22-Jul-20.
 */
public class CustomPriorityView extends AppCompatImageView {

    public static final String TAG = "CustomView";

    public CustomPriorityView(Context context) {
        super(context);
        init();
    }

    public CustomPriorityView(Context context, AttributeSet attrs) {
        super(context, attrs,0);

        TypedArray customPriorityView = context.obtainStyledAttributes(attrs, R.styleable.CustomPriorityView);
        try{
            int priority = customPriorityView.getInt(R.styleable.CustomPriorityView_priority, 1);
            setPriority(priority);
        }finally {
            customPriorityView.recycle();
        }
    }

    public CustomPriorityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setPriority(int priority){

        switch (priority){
            case 1:
                setBackground(getResources().getDrawable(R.drawable.circle));
                getBackground().setTint(getResources().getColor(R.color.low));
                break;
            case 2:
                setBackground(getResources().getDrawable(R.drawable.circle));
                getBackground().setTint(getResources().getColor(R.color.medium));
                break;
            case 3:
                setBackground(getResources().getDrawable(R.drawable.circle));
                getBackground().setTint(getResources().getColor(R.color.high));
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG,"onLayout"+ left  + top + right+ bottom);
    }

    private void init(){
        setPriority(1);
    }
}
