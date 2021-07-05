package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.tensorflow.lite.examples.detection.env.Logger;

import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AfterActivity extends AppCompatActivity {
    private Button MainButton, reScanButton;
    //private TextView textFromDetect;
    //
    private static final Logger LOGGER = new Logger();
    LinearLayout layout;

    //
    public ConcurrentLinkedQueue<String> ReadQueue=new ConcurrentLinkedQueue<>();
    public HashSet<String> setOfLabesFromDetector = new HashSet<>(); 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterscan);
        reScanButton=findViewById(R.id.ReScan);
        MainButton=findViewById(R.id.MainMenu);

        //linearLayout1
        //layout=findViewById(R.id.layouta);
        layout=findViewById(R.id.linearLayout1);
        //

        //reScanButton.setOnClickListener(v -> startActivity(new Intent(AfterActivity.this, DetectorActivity.class)));

        if (getIntent().hasExtra("labels_detect")) {
            ReadQueue = (ConcurrentLinkedQueue<String>) getIntent().getSerializableExtra("labels_detect");
            for (String label :ReadQueue ) {
                setOfLabesFromDetector.add(label);
            }
            //textFromDetect.append(setOfLabesFromDetector.toString());
            // asaf debug
            LOGGER.i("*******************************");
            LOGGER.i(setOfLabesFromDetector.toString());
            LOGGER.i("*******************************");

            if (setOfLabesFromDetector.contains("fat")){
                ImageView imageView1 = new ImageView(AfterActivity.this);
                imageView1.setImageResource(R.drawable.fat);
                addView1(imageView1,300,300);
            }
            if (setOfLabesFromDetector.contains("sugar")){
                ImageView imageView1 = new ImageView(AfterActivity.this);
                imageView1.setImageResource(R.drawable.sugar);
                addView1(imageView1,300,300);
            }
            if (setOfLabesFromDetector.contains("salt")){
                ImageView imageView1 = new ImageView(AfterActivity.this);
                imageView1.setImageResource(R.drawable.salt);
                addView1(imageView1,300,300);
            }
            if (setOfLabesFromDetector.contains("green")){
                ImageView imageView1 = new ImageView(AfterActivity.this);
                imageView1.setImageResource(R.drawable.green);
                addView1(imageView1,300,300);
            }

        }

        reScanButton.setOnClickListener(v ->{
            Intent intent = new Intent(AfterActivity.this, DetectorActivity.class);// New activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // Call once you redirect to another activity
        });

        MainButton.setOnClickListener(v -> startActivity(new Intent(AfterActivity.this, MainActivity.class)));
    }

    public void addView1(ImageView imageView,int width, int height){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(5,10,5,10);

        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }
}