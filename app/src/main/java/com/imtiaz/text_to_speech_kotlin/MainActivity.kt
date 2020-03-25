package com.imtiaz.text_to_speech_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*



/** Extend your activity with TextToSpeech.OnInitListener. */
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.button_speak
        editText = this.edittext_input

        buttonSpeak!!.isEnabled = false;
        /** Initialize TextToSpeech class variable. */
        /** TextToSpeech(Context, OnInitListener) */
        tts = TextToSpeech(this, this)


        /** You may set an event, which can trigger the Speech output. A button onClickListener is used in this example. */
        buttonSpeak!!.setOnClickListener { speakOut() }
    }




    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }


    /**
     *
     * When your application is started, TextToSpeech Engine may take some duration of time for initialization.
     * To avoid speaking out, you may initially disable the button to speak.
     * When the TextToSpeech Engine is initialized, onInit() function is called, which should be overridden
     * and you may enable the button here. And finally, when your Activity is destroyed,
     * stop and shutdown TextToSpeech Engine
     * */
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
