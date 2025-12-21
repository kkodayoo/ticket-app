package com.example.toucan_vinyl.User.Scans

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.toucan_vinyl.databinding.FragmentTicketQrDialogBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.json.JSONObject

class TicketQrDialogFragment : DialogFragment() {

    private var _binding: FragmentTicketQrDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTicketQrDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val code = requireArguments().getString("CODE")!!

        val payload = JSONObject().apply {
            put("type", "TICKET")
            put("ticket_code", code)
        }.toString()

        binding.ivQr.setImageBitmap(generateQr(payload))
    }

    private fun generateQr(text: String): Bitmap {
        val matrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 400, 400)
        return Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565).apply {
            for (x in 0 until 400)
                for (y in 0 until 400)
                    setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(code: String) =
            TicketQrDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("CODE", code)
                }
            }
    }
}
