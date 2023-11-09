package org.unitmesh.llmpoc.embedding

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import ai.onnxruntime.OrtUtil
import android.content.Context
import android.content.res.AssetManager
import org.unitmesh.tokenizer.huggingface.tokenizers.HuggingFaceTokenizer
import java.io.InputStream


class STSemantic(
    private val tokenizer: HuggingFaceTokenizer,
    private val session: OrtSession,
    private val env: OrtEnvironment,
) {
    fun getTokenizer(): HuggingFaceTokenizer {
        return tokenizer
    }

    fun embed(input: String): List<Double> {
        val tokenized = tokenizer.encode(input, true)

        val inputIds = tokenized.ids
        val attentionMask = tokenized.attentionMask
        val typeIds = tokenized.typeIds

        val size = inputIds.size
        val tensorInput = OrtUtil.reshape(inputIds, longArrayOf(1, size.toLong()))
        val tensorAttentionMask =
            OrtUtil.reshape(attentionMask, longArrayOf(1, attentionMask.size.toLong()))
        val tensorTypeIds = OrtUtil.reshape(typeIds, longArrayOf(1, typeIds.size.toLong()))

        val result = session.run(
            mapOf(
                "input_ids" to OnnxTensor.createTensor(env, tensorInput),
                "attention_mask" to OnnxTensor.createTensor(env, tensorAttentionMask),
                "token_type_ids" to OnnxTensor.createTensor(env, tensorTypeIds),
            ),
        )

        val outputTensor: OnnxTensor = result.get(0) as OnnxTensor

        val floatArray = outputTensor.floatBuffer.array()
        val shapeSize = outputTensor.info.shape[2].toInt()
        val meanArray = FloatArray(shapeSize)
        for (i in 0 until shapeSize) {
            var sum = 0f
            for (j in inputIds.indices) {
                sum += floatArray[j * size + i]
            }

            meanArray[i] = sum / size
        }

        return meanArray.map { it.toDouble() }
    }


    companion object {
        /**
         * Create a new instance of [STSemantic] with default model.
         * We use official model: [all-MiniLM-L6-v2](https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2)
         * We can use [optimum](https://github.com/huggingface/optimum) to transform the model to onnx.
         */
        fun create(context: Context): STSemantic {
            val assetManager: AssetManager = context.assets

            val tokenizerStream: InputStream = assetManager.open("model/tokenizer.json")
            val onnxStream: InputStream = assetManager.open("model/model.onnx")

            val tokenizer = HuggingFaceTokenizer.newInstance(tokenizerStream, mapOf())
            val ortEnv = OrtEnvironment.getEnvironment()
            val sessionOptions = OrtSession.SessionOptions()

            val onnxPathAsByteArray = ByteArray(onnxStream.available())
            onnxStream.read(onnxPathAsByteArray)

            val session = ortEnv.createSession(onnxPathAsByteArray, sessionOptions)

            return STSemantic(tokenizer, session, ortEnv)
        }
    }
}