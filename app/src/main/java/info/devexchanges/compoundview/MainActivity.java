package info.devexchanges.compoundview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DynamicValueTextView textView = (DynamicValueTextView)findViewById(R.id.text_view);
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int values = textView.getValues();
                Toast.makeText(MainActivity.this, "Value of this text: " + values, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
