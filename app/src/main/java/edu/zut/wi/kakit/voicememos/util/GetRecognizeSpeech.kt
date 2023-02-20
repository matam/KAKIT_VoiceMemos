package edu.zut.wi.kakit.voicememos.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract
import java.util.*

class GetRecognizeSpeech : ActivityResultContract<String, ArrayList<String>?>() {
    override fun createIntent(context: Context, input: String) =
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault())
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "pl_PL")

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(RecognizerIntent.EXTRA_PROMPT, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?) : ArrayList<String>? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
    }
}
