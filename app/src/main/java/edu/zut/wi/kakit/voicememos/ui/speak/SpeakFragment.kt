package edu.zut.wi.kakit.voicememos.ui.speak

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import edu.zut.wi.kakit.voicememos.databinding.FragmentFirstBinding

import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SpeakFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val askMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {map ->
        for (entry in map.entries)
        {
            Toast.makeText(context, "${entry.key} = ${entry.value}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}