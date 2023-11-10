package org.unitmesh.llmpoc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.unitmesh.llmpoc.R
import org.unitmesh.llmpoc.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // 在此处初始化布局元素
        val addButton: Button = root.findViewById(R.id.addButton)
        val computeButton: Button = root.findViewById(R.id.computeButton)

        addButton.setOnClickListener { addSentence() }
        computeButton.setOnClickListener { compute() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addSentence() {

    }

    fun compute() {

    }

    fun showJsonOutput(view: View) {
        val textView: TextView = binding.jsonOutput
        textView.text = "json output"
    }
}