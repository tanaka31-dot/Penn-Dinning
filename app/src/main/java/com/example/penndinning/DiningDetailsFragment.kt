package com.example.penndinning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.penndinning.databinding.FragmentDiningDetailsBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DiningViewModel::class.java]
        val position = DiningDetailsFragmentArgs.fromBundle(requireArguments()).id
        viewModel.response.observe(viewLifecycleOwner) {

        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if(!isLoading) {
                val dining = viewModel.getDiningsList()?.get(position)
                Picasso.get().load(dining?.image).into(binding.diningImageBig)
                binding.diningNameDetails.text = dining?.name
                val dys = dining?.days
                println(dys?.size)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}