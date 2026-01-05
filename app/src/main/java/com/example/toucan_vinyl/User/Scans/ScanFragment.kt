package com.example.toucan_vinyl.User.Scans

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.Manifest
import com.example.toucan_vinyl.databinding.FragmentScanBinding
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.toucan_vinyl.Data.Api.TicketApiClient
import com.example.toucan_vinyl.Data.Model.TicketModel
import kotlinx.coroutines.launch

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var isScanning = true
    private lateinit var cameraExecutor: ExecutorService

    //Bisa semua format
    //private var scanner = BarcodeScanning.getClient()

    //Khusus hanya format QR Code
    private var scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
    )

    // Launcher untuk izin modern
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                Toast.makeText(context, "Izin kamera diperlukan", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (hasCameraPermission()) {
            startCamera()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Hapus binding & matikan scanner saat view dihancurkan untuk mencegah memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scanner?.close()
        cameraExecutor.shutdown()
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build().apply {
                    setAnalyzer(cameraExecutor) { imageProxy ->
                        val mediaImage = imageProxy.image ?: return@setAnalyzer imageProxy.close()
                        val image = InputImage.fromMediaImage(
                            mediaImage, imageProxy.imageInfo.rotationDegrees
                        )

                        scanner.process(image).addOnSuccessListener { barcodes ->
                            if (barcodes.isNotEmpty() && isScanning) {
                                isScanning = false

                                val rawValue =
                                    barcodes[0].rawValue ?: return@addOnSuccessListener

                                handleQrResult(rawValue)
                            }
                        }.addOnCompleteListener { imageProxy.close() }
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e("TabScan", "Gagal mulai kamera", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun handleQrResult(rawValue: String) {
        try {
            val json = org.json.JSONObject(rawValue)

            if (json.getString("type") == "TICKET") {
                val ticketCode = json.getString("ticket_code")

                activity?.runOnUiThread {
                    binding.tvScanResult.text = "Memeriksa tiket..."
                }

                fetchTicketDetail(ticketCode)
            } else {
                showError("QR tidak valid")
            }

        } catch (e: Exception) {
            showError("Format QR salah")
        }
    }

    private fun fetchTicketDetail(ticketCode: String) {
        lifecycleScope.launch {
            try {
                val response = TicketApiClient.apiService.getTicketByCode("eq.$ticketCode")

                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    val ticket = response.body()!![0]

                    showTicketDialog(ticket)
                } else {
                    showError("Tiket tidak ditemukan")
                }

            } catch (e: Exception) {
                Log.e("SCAN_API_ERROR", "Fetch ticket failed", e)
                showError("Gagal menghubungi server")
            }
        }
    }

    private fun showError(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            binding.tvScanResult.text = message
            isScanning = true
        }
    }

    private fun showTicketDialog(ticket: TicketModel) {
        ShowTicketDialogFragment.newInstance(ticket).show(parentFragmentManager, "show_ticket")
    }

}