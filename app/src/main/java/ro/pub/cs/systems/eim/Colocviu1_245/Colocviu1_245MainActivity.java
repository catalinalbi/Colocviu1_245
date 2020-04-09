package ro.pub.cs.systems.eim.Colocviu1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_245MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    EditText nextTermEditText;
    TextView allTermsTextView;
    Context context;
    int sum = Integer.MAX_VALUE;
    String lastValue = "";
    private int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_245_main);

        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);
        nextTermEditText = findViewById(R.id.nextTerm_editText);
        allTermsTextView = findViewById(R.id.allTerms_textView);
        context = this;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextTermEditText.getText().toString().length() >= 1) {
                    String text = allTermsTextView.getText().toString();
                    if (text.length() == 0) {
                        text = nextTermEditText.getText().toString();
                    }
                    else {
                        text += (" + " + nextTermEditText.getText().toString());
                    }
                    allTermsTextView.setText(text);
                    nextTermEditText.setText("");
                }
            }
        });

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTermsTextView.getText().toString().compareTo(lastValue) == 0) {
                    Toast.makeText(context, "The activity returned with result " + sum, Toast.LENGTH_LONG).show();
                }
                else {
                    lastValue = allTermsTextView.getText().toString();
                    Intent intent = new Intent(context, Colocviu1_245SecondaryActivity.class);
                    intent.putExtra("allTerms", allTermsTextView.getText().toString());
                    startActivityForResult(intent, 100);
                }
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("SEND_DATA");

    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "SERVER DATA: " + intent.getStringExtra("NEW_DATA").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            sum = resultCode;

            if (sum >= 10 && serviceStatus == 0) {
                Intent service = new Intent(getApplicationContext(), Colocviu1_245Service.class);
                service.putExtra("sum", sum);
                getApplicationContext().startService(service);
                serviceStatus = 1;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (sum != Integer.MAX_VALUE) {
            savedInstanceState.putInt("allTermsSum", sum);
        }
        if (allTermsTextView.getText().toString().length() > 0) {
            savedInstanceState.putString("allTerms", allTermsTextView.getText().toString());
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("allTermsSum")) {
            sum = savedInstanceState.getInt("allTermsSum");
        }
        if (savedInstanceState.containsKey("allTerms")) {
            allTermsTextView.setText(savedInstanceState.getString("allTerms"));
            if (lastValue == "") {
                lastValue = savedInstanceState.getString("allTerms");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_245Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
