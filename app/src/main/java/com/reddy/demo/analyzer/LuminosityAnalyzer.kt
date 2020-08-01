package com.reddy.demo.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.reddy.demo.ui.main.LumaListener
import java.nio.ByteBuffer


/**
 * Created by Reddy Tintaya on 8/1/20.
 */
class LuminosityAnalyzer (val listener: LumaListener): ImageAnalysis.Analyzer {
    fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }

}