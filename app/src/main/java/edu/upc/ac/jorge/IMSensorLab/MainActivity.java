package edu.upc.ac.jorge.IMSensorLab;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.upc.ac.jorge.IMSensorLab.MESSAGE";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessageNoFilter(View view) {
        Intent intent = new Intent(this, QDisplayAbsOrientation.class);
        String message = "nofilter";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void sendMessageFilter(View view) {
        Intent intent = new Intent(this, QDisplayAbsOrientation.class);
        String message = "filter";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void sendMessageRelNoG(View view) {
        Intent intent = new Intent(this, QDisplayRelativeOrientation.class);
        String message = "nogravity";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}