package info.devexchanges.compoundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Represents a spinner control that can be scrolled using side arrows.
 */
public class DynamicValueTextView extends LinearLayout {

    /**
     * The state to save to keep the state of the super class correctly.
     */
    private static String STATE_SUPER_CLASS = "SuperClass";
    private static String STATE_CURRENT_VALUE = "currentValues";
    /**
     * The currently value in TextView.
     */
    private int currentValueState = 0;

    private ImageView btnPrevious;
    private ImageView btnNext;

    public DynamicValueTextView(Context context) {
        super(context);

        initializeViews(context);
    }

    public DynamicValueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextValue);
        typedArray.recycle();

        initializeViews(context);
    }

    public DynamicValueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextValue);
        typedArray.recycle();

        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the control.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.layout_text_value, this);
    }

    public void setValues(int value) {
        TextView currentValue = (TextView) this.findViewById(R.id.text_value);
        currentValue.setText(String.valueOf(value));
        checkInstanceValues();

        //set current state value for save instance state
        this.currentValueState = value;
    }

    private void checkInstanceValues() {
        TextView currentValue = (TextView) this.findViewById(R.id.text_value);
        // If the first value is show, hide the previous button
        if (currentValue.getText().toString().equals("0")) {
            btnPrevious.setVisibility(INVISIBLE);
            currentValue.setTextColor(Color.RED);
        } else {
            btnPrevious.setVisibility(VISIBLE);
            currentValue.setTextColor(Color.BLUE);
        }

    }

    public int getValues() {
        TextView currentValue = (TextView) this.findViewById(R.id.text_value);
        String value = currentValue.getText().toString();

        return Integer.parseInt(value);
    }

    @Override
    protected void onFinishInflate() {

        // When the controls in the layout are doing being inflated, set the
        // callbacks for the side arrows
        super.onFinishInflate();

        // When the previous button is pressed, select the previous item in the
        // list
        btnPrevious = (ImageView) this.findViewById(R.id.btn_previous);
        btnPrevious.setImageResource(R.drawable.subtract);

        if (getValues() == 0) {
            btnPrevious.setVisibility(GONE);
        }

        // When the next button is pressed, decrease value by 1 unit
        btnPrevious.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TextView currentValue = (TextView) findViewById(R.id.text_value);
                String value = currentValue.getText().toString();
                int numOfvalue = Integer.parseInt(value);
                setValues(numOfvalue - 1);
            }
        });

        // When the next button is pressed, increase value by 1 unit
        btnNext = (ImageView) this.findViewById(R.id.btn_next);
        btnNext.setImageResource(R.drawable.add);
        btnNext.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                TextView currentValue = (TextView) findViewById(R.id.text_value);
                String value = currentValue.getText().toString();
                int numOfvalue = Integer.parseInt(value);
                setValues(numOfvalue + 1);
            }
        });
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState());
        bundle.putInt(STATE_CURRENT_VALUE, currentValueState);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle)state;

            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER_CLASS));
            setValues(bundle.getInt(STATE_CURRENT_VALUE));
        }
        else
            super.onRestoreInstanceState(state);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        // Makes sure that the state of the child views in the side
        // spinner are not saved since we handle the state in the
        // onSaveInstanceState.
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        // Makes sure that the state of the child views in the side
        // spinner are not restored since we handle the state in the
        // onSaveInstanceState.
        super.dispatchThawSelfOnly(container);
    }
}
