package edu.zut.wi.kakit.voicememos.ui.speak

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import edu.zut.wi.kakit.voicememos.databinding.RecordMainBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SpeakFragment : Fragment() {

    private var _binding: RecordMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val simpleContractRegistration = registerForActivityResult(GetNotes()) { data ->
        if (data == null)
            Toast.makeText(context, "User Cancelled", Toast.LENGTH_SHORT).show()
        else
        {
            binding.idOutput.text = data.toString()
        }
    }
    private val askPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context,"Permission is granted",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Permission is not granted",Toast.LENGTH_LONG).show()

            }
        }

    private val askMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {map ->
        for (entry in map.entries)
        {
            Toast.makeText(context, "${entry.key} = ${entry.value}", Toast.LENGTH_SHORT).show()
        }

//---------------  Example use inside code
//                askMultiplePermissions.launch(arrayOf(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION,
//                    android.Manifest.permission.BLUETOOTH
//                ))

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecordMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idMic.setOnClickListener{
         simpleContractRegistration.launch("Say something")
            askPermission.launch(android.Manifest.permission.RECORD_AUDIO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    class GetNotes : ActivityResultContract<String, ArrayList<String>?>() {
        override fun createIntent(context: Context, extraPrompt: String) =
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, extraPrompt)
            }

        override fun parseResult(resultCode: Int, result: Intent?) : ArrayList<String>? {
            if (resultCode != Activity.RESULT_OK) {
                return null
            }
            return result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        }
    }
}