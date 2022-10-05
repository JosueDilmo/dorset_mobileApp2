package com.josue.dorset_mobileapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josue.dorset_mobileapp2.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey("NEW_TEXT") }.apply {
            val newText = arguments?.getString("NEW_TEXT")
            binding.helloBlankFragment.text = newText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}