package com.myth.diaryapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myth.diaryapp.MainActivity
import com.myth.diaryapp.R
import com.myth.diaryapp.adapter.RecordsAdapter
import com.myth.diaryapp.databinding.FragmentHomeBinding
import com.myth.diaryapp.model.DiaryRecord
import com.myth.diaryapp.viewmodel.RecordViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var recordsViewModel: RecordViewModel
    private lateinit var recordsAdapter: RecordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordsViewModel = (activity as MainActivity).recordViewModel

        setUpRecyclerView()
        binding?.fabAddRecord!!.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_homeFragment_to_newRecordFragment
            )
        }
    }


    private fun setUpRecyclerView(){
        recordsAdapter = RecordsAdapter()

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager((activity as MainActivity).applicationContext)
            adapter = recordsAdapter
        }

        activity?.let {
            recordsViewModel.getAllRecords().observe(
                viewLifecycleOwner
            ){record ->
                recordsAdapter.differ.submitList(record)
                updateUI(record)
            }
        }
    }


    private fun updateUI(record : List<DiaryRecord>){
        if(record != null){
            if(record.isNotEmpty()){
                binding?.cardView!!.visibility = View.GONE
                binding?.recyclerView!!.visibility = View.VISIBLE
            }else{
                binding?.cardView!!.visibility = View.VISIBLE
                binding?.recyclerView!!.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView

        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String){
        val searchQuery = query

        recordsViewModel.searchRecords(searchQuery).observe(
            viewLifecycleOwner
        ){list->recordsAdapter.differ.submitList(list)}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}