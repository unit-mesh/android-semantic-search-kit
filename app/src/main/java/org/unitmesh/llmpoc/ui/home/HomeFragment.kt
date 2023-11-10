package org.unitmesh.llmpoc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cc.unitmesh.rag.document.Document
import cc.unitmesh.rag.store.InMemoryEmbeddingStore
import org.unitmesh.llmpoc.R
import org.unitmesh.llmpoc.databinding.FragmentHomeBinding
import org.unitmesh.llmpoc.embedding.STSemantic

class HomeFragment : Fragment() {

    private lateinit var embeddingStore: InMemoryEmbeddingStore<Document>
    private lateinit var stSemantic: STSemantic
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var textContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.defaultSource
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // 初始化容器
        textContainer = root.findViewById(R.id.textContainer)

        // 添加默认的 TextView
        addDefaultSentence("That is a happy dog")
        addDefaultSentence("That is a very happy person")
        addDefaultSentence("Today is a sunny day")

        val addButton: Button = root.findViewById(R.id.addButton)
        addButton.setOnClickListener { addSentence() }

        val computeButton: Button = root.findViewById(R.id.computeButton)
        computeButton.setOnClickListener { compute() }

        stSemantic = STSemantic.create(this.requireContext())
        embeddingStore = InMemoryEmbeddingStore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addSentence() {
        // 创建新的 TextView
        val newTextView = EditText(requireContext())
        newTextView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        textContainer.addView(newTextView)
    }

    fun addDefaultSentence(sentence: String) {
        val editText = EditText(requireContext())
        editText.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        editText.setText(sentence)
        textContainer.addView(editText)
    }

    fun compute() {
        // 获取所有的 TextView
        val textViews = mutableListOf<EditText>()
        for (i in 0 until textContainer.childCount) {
            val child = textContainer.getChildAt(i)
            if (child is EditText) {
                textViews.add(child)
            }
        }

        // 获取所有的句子
        val sentences = textViews.map { it.text.toString() }

        // 处理添加句子的逻辑
        sentences.forEach { embeddingStore.add(stSemantic.embed(it), Document.from(it)) }
        val defaultTextView = binding.defaultSource
        val defaultText = defaultTextView.text.toString()

        // 处理计算相似度的逻辑
        val relevantEmbeddings = embeddingStore.findRelevant(stSemantic.embed(defaultText), 3)
        val jsonOutput = binding.jsonOutput
        jsonOutput.text = relevantEmbeddings.toString()
    }
}