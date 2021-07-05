package org.tensorflow.lite.examples.detection;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Startup extends Application {
    private TextToSpeech tts;
    private Timer TTsTimer;
    @Override
    public void onCreate(){
        super.onCreate();
        TTsTimer=new Timer();
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
        TTsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                tts.speak("to scan sticker click at the top of the screen" + "To scan date tap the bottom",TextToSpeech.QUEUE_FLUSH,null);
                TTsTimer.cancel();
            }
        },1500);
    }
}
