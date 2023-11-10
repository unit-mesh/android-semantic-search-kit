package org.unitmesh.llmpoc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "That is a happy person"
    }

    val text: LiveData<String> = _text

    fun addSentence(sourceSentence: String, compareSentence: String) {
        // 处理添加句子的逻辑
    }

    fun computeSimilarity() {
        // 处理计算相似度的逻辑
    }
}