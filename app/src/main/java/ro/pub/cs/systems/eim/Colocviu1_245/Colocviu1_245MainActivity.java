package ro.pub.cs.systems.eim.Colocviu1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_245MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    EditText nextTermEditText;
    TextView allTermsEditText;
    Context context;
    int sum = Integer.MAX_VALUE;
    String lastValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_245_main);

        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);
        nextTermEditText = findViewById(R.id.nextTerm_editText);
        allTermsEditText = findViewById(R.id.allTerms_textView);
        context = this;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextTermEditText.getText().toString().length() >= 1) {
                    String text = allTermsEditText.getText().toString();
                    if (text.length() == 0) {
                        text = nextTermEditText.getText().toString();
                    }
                    else {
                        text += (" + " + nextTermEditText.getText().toString());
                    }
                    allTermsEditText.setText(text);
                    nextTermEditText.setText("");
                }
            }
        });

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTermsEditText.getText().toString().compareTo(lastValue) == 0) {
                    Toast.makeText(context, "The activity returned with result " + sum, Toast.LENGTH_LONG).show();
                }
                else {
                    lastValue = allTermsEditText.getText().toString();
                    Intent intent = new Intent(context, Colocviu1_245SecondaryActivity.class);
                    intent.putExtra("allTerms", allTermsEditText.getText().toString());
                    startActivityForResult(intent, 100);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            sum = resultCode;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (sum != Integer.MAX_VALUE) {
            savedInstanceState.putInt("allTermsSum", sum);
        }
        if (allTermsEditText.getText().toString().length() > 0) {
            savedInstanceState.putString("allTerms", allTermsEditText.getText().toString());
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("allTermsSum")) {
            sum = savedInstanceState.getInt("allTermsSum");
        }
        if (savedInstanceState.containsKey("allTerms")) {
            allTermsEditText.setText(savedInstanceState.getString("allTerms"));
            if (lastValue == "") {
                lastValue = savedInstanceState.getString("allTerms");
            }
        }
    }
}
