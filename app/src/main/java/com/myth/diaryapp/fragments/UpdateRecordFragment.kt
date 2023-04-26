package com.myth.diaryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.myth.diaryapp.MainActivity
import com.myth.diaryapp.R
import com.myth.diaryapp.databinding.FragmentUpdateRecordBinding
import com.myth.diaryapp.model.DiaryRecord
import com.myth.diaryapp.viewmodel.RecordViewModel

class UpdateRecordFragment : Fragment() {

    private var _binding: FragmentUpdateRecordBinding? = null
    private val binding get() = _binding

    private lateinit var recordViewModel: RecordViewModel
    private lateinit var currentRecord: DiaryRecord

    private val args: UpdateRecordFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateRecordBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordViewModel = (activity as MainActivity).recordViewModel
        currentRecord = args.record!!

        binding?.etRecordBodyUpdate!!.setText(currentRecord.recordBody)
        binding?.etRecordTitleUpdate!!.setText(currentRecord.recordTitle)

        binding?.fabDone!!.setOnClickListener {
            val title = binding?.etRecordTitleUpdate!!.text.toString().trim()
            val body = binding?.etRecordBodyUpdate!!.text.toString().trim()

            if (title.isNotEmpty()) {
                val record = DiaryRecord(currentRecord.id, title, body, currentRecord.recordTimestamp)

                recordViewModel.updateRecord(record)
                view.findNavController().navigate(R.id.action_updateRecordFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    context, "Please enter the title", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun deleteRecord() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete diary entry?")
            setMessage("Are you sure you want to delete this entry?")
            setPositiveButton("Delete") { _, _ ->
                recordViewModel.deleteRecord(currentRecord)
                view?.findNavController()
                    ?.navigate(R.id.action_updateRecordFragment_to_homeFragment)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_record_xml, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteRecord()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}