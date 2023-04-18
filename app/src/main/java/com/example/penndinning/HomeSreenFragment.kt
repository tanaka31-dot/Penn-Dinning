package com.example.penndinning

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.penndinning.databinding.FragmentHomeSreenBinding

class HomeSreenFragment : Fragment() {
    private var _binding: FragmentHomeSreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DiningViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeSreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DiningViewModel::class.java]
        recyclerView = binding.sectionListRv
        viewModel.response.observe(viewLifecycleOwner) {
            val adapter = context?.let { it1 -> DiningAdapter(it1, it) }
            recyclerView.adapter = adapter
        }
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}