package com.example.penndinning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.penndinning.databinding.FragmentDiningDetailsBinding


class DiningDetailsFragment : Fragment() {
    private var _binding: FragmentDiningDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DiningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiningDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}