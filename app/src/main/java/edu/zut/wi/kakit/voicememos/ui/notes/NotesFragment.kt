package edu.zut.wi.kakit.voicememos.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.zut.wi.kakit.voicememos.R
import edu.zut.wi.kakit.voicememos.databinding.FragmentSecondBinding
import edu.zut.wi.kakit.voicememos.model.NoteModel
import edu.zut.wi.kakit.voicememos.ui.speak.SpeakFragment
import edu.zut.wi.kakit.voicememos.util.GetRecognizeSpeech
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NotesFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var dataNotes = ArrayList<NoteModel>()
    //private var adapter = NotesListAdapter(dataNotes)
    private val notesViewModel: NotesViewModel by viewModels()
    //private var  adapter = notesViewModel.getDataNotes().value?.let { NotesListAdapter(it) }
    private var  adapter = NotesListAdapter(dataNotes)
    //private lateinit var adapter : NotesListAdapter
    private val getRecognizeSpeech = registerForActivityResult(GetRecognizeSpeech()) { data ->
        if (data == null)
            Toast.makeText(context, "User Cancelled", Toast.LENGTH_SHORT).show()
        else
        {
            //notesViewModel.addData(data)
            dataNotes.add(NoteModel(dataNotes.size+1, reformatDate(0) ,concatAnswer(data)))
            adapter.notifyDataSetChanged()

        }
    }

    private val askPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context,"Permission is granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Permission is not granted", Toast.LENGTH_LONG).show()

            }
        }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerview.layoutManager= LinearLayoutManager(context)
//        notesViewModel.dataNotes.observe(viewLifecycleOwner, Observer<ArrayList<NoteModel>> {
//            adapter = NotesListAdapter(it)
//            binding.recyclerview.adapter=adapter
//            adapter?.notifyDataSetChanged()
//        })
        //adapter = notesViewModel.getDataNotes().value?.let { NotesListAdapter(it) }!!
        binding.recyclerview.adapter=adapter
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.idMic2.setOnClickListener{
            askPermission.launch(android.Manifest.permission.RECORD_AUDIO)
            getRecognizeSpeech.launch("Say something")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun reformatDate(valueDays: Long) : String
    {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().minusDays(valueDays).format(formatter)

    }

    private fun concatAnswer(data : ArrayList<String>) : String
    {
        val sb = StringBuilder()
        data.forEach{
            sb.append(it + "\n")
        }
        return sb.toString()
    }
}