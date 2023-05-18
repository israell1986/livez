package com.example.livez.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.data.api.search.response.NationalizeData
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            viewModel.search(binding.textInput.text.toString())
        }

        viewModel.uiModel.observe(viewLifecycleOwner) {
            it.predictions?.let { event ->
                if (!event.consumed) {
                    val data = event.consume() as List<com.example.core.data.api.search.response.NationalizeData.Country>?

                    if (data.isNullOrEmpty()) {
                        binding.error.text = "No result. try again"

                    } else {
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                }
            }
            it.errorMsg?.let {
                binding.error.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}