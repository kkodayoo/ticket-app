package com.example.toucan_vinyl.User.Payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.toucan_vinyl.Data.AppDatabase
import com.example.toucan_vinyl.Data.entity.PaymentEntity
import com.example.toucan_vinyl.databinding.FragmentPaymentFormBinding
import kotlinx.coroutines.launch

class PaymentFormFragment : Fragment() {

    private var _binding: FragmentPaymentFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Init database (pakai context dari Fragment) **/
        db = AppDatabase.getInstance(requireContext())

        /** Tombol SAVE **/
        binding.btnSaveCard.setOnClickListener {

            val cardNumber = binding.etCardNumber.text.toString().trim()
            val cardHolder = binding.etCardHolder.text.toString().trim()
            val expiry = binding.etExpiry.text.toString().trim()
            val cvv = binding.etCVV.text.toString().trim()
            val termsAccepted = binding.cbTerms.isChecked

            if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(requireContext(), "Isi semua data kartu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!termsAccepted) {
                Toast.makeText(requireContext(), "Setujui terms & conditions!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert ke database
            lifecycleScope.launch {
                val payment = PaymentEntity(
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    cvvCVC = cvv,
                    createdAt = System.currentTimeMillis()
                )

                db.paymentDao().insert(payment)

                Toast.makeText(requireContext(), "Card saved!", Toast.LENGTH_SHORT).show()

                // kembali ke fragment sebelumnya
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
