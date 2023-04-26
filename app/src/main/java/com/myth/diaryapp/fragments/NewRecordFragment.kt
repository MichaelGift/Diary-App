package com.myth.diaryapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.myth.diaryapp.MainActivity
import com.myth.diaryapp.R
import com.myth.diaryapp.databinding.FragmentNewRecordBinding
import com.myth.diaryapp.model.DiaryRecord
import com.myth.diaryapp.viewmodel.RecordViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewRecordFragment : Fragment() {
    private var _binding: FragmentNewRecordBinding? = null
    private val binding get() = _binding

    private lateinit var recordsViewModel: RecordViewModel

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewRecordBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordsViewModel = (activity as MainActivity).recordViewModel
        mView = view
    }

    private fun saveRecord(view: View) {
        val recordTitle = binding?.etRecordTitle!!.text.toString().trim()
        val recordBody = binding?.etRecordBody!!.text.toString().trim()
        val currentTime =  Calendar.getInstance().time
        val sdf = SimpleDateFormat("HHmm EEEE d MMMM yyyy", Locale.getDefault())
        val recordTimestamp = sdf.format(currentTime).toString()

        if (recordTitle.isNotEmpty()) {
            val diaryRecord = DiaryRecord(0, recordTitle, recordBody, recordTimestamp)

            recordsViewModel.addRecord(diaryRecord)

            Toast.makeText(
                mView.context,
                "Saved",
                Toast.LENGTH_SHORT
            ).show()

            view.findNavController().navigate(R.id.action_newRecordFragment_to_homeFragment)
        } else {
            Toast.makeText(
                mView.context,
                "Please enter title",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.newrecord_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveRecord(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}