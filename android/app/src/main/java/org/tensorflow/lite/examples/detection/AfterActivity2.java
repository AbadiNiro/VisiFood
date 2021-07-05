package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.*;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import java.util.*;

public class AfterActivity2 extends AppCompatActivity {
    private Timer TTSFunc;
    private Button MainButton, reScanButton;
    private ImageView test_image;
    private TextRecognizer textRecognizer;
    private String stringResult;
    private TextToSpeech tts;
    private Map<String,String> monthMap= new HashMap<String, String>(){{
        this.put("01","January");
        this.put("02","February");
        this.put("03","March");
        this.put("04","April");
        this.put("05","May");
        this.put("06","June");
        this.put("07","July");
        this.put("08","August");
        this.put("09","September");
        this.put("10","October");
        this.put("11","November");
        this.put("12","December");
    }};

    //Intent intent = getIntent();
    //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterscan_test_asaf);
        reScanButton = findViewById(R.id.buttonScanAgain);
        MainButton = findViewById(R.id.backMainMenu);
        test_image = findViewById(R.id.imageView2);
        TTSFunc=new Timer();
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = tts.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            test_image.setImageBitmap(b);

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            Frame imageFrame = new Frame.Builder()
                    .setBitmap(b)                 // your image bitmap
                    .build();

            String imageText = "";

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText = textBlock.getValue();                   // return string
            }
            String finalImageText = imageText;
            TTSFunc.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(finalImageText.split("/").length!=3)
                    {
                        tts.speak("Failed to read date",TextToSpeech.QUEUE_FLUSH,null);
                        Log.d("Bad format","failed");
                    }
                    else
                    {
                        String year;
                        String[]arr= finalImageText.split("/");
                        if(arr[2].length()==2)
                        {
                            year= "20 " + arr[2];
                        }
                        else
                        {
                            year="20 "+arr[2].substring(arr[2].length()-2);
                        }
                        int SpeechStatus = tts.speak(arr[0] + " " + monthMap.get(arr[1]) + " " + year, TextToSpeech.QUEUE_FLUSH, null);
                        Log.d("Good format",arr[0] + " " + monthMap.get(arr[1]) + " " + arr[2]);
                    }
                }
            },1000);

            TextView wordOutPut = (TextView) findViewById(R.id.textView2);
            wordOutPut.setText(imageText);


        }

        //test_image.setImageBitmap(bitmap);
        // startActivity(new Intent(AfterActivity2.this, DetectorActivityDates.class))
        reScanButton.setOnClickListener(v ->{
            Intent intent = new Intent(AfterActivity2.this, DetectorActivityDates.class);// New activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // Call once you redirect to another activity
        });
        MainButton.setOnClickListener(v -> startActivity(new Intent(AfterActivity2.this, MainActivity.class)));




    }

}