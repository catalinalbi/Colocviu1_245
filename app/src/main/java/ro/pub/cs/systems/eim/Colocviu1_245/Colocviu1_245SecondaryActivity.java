package ro.pub.cs.systems.eim.Colocviu1_245;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Colocviu1_245SecondaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_245_main);
        Intent intent = getIntent();
        String allTerms = intent.getStringExtra("allTerms");
        int sum = 0;
        String[] words = allTerms.split("\\W+");
        for (int i = 0; i < words.length; i++) {
            if (words[i] != "+") {
                sum += Integer.valueOf(words[i]);
            }
        }
        Log.v("[SECONDARY ACTIVITY]", "Called!!!");
        setResult(sum);
        finish();
    }

}
