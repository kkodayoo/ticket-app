package com.example.toucan_vinyl.User.Payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toucan_vinyl.Data.AppDatabase
import com.example.toucan_vinyl.databinding.FragmentPaymentListBinding
import com.example.toucan_vinyl.User.Payment.PaymentListAdapter
import kotlinx.coroutines.launch

class PaymentList : Fragment() {

    private var _binding: FragmentPaymentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: PaymentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        setupRecyclerView()
        loadPayments()
    }

//    private fun setupRecyclerView() {
//        adapter = PaymentListAdapter(emptyList())
//
//        binding.rvPaymentTable.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = this@PaymentList.adapter
//            addItemDecoration(
//                DividerItemDecoration(
//                    requireContext(),
//                    DividerItemDecoration.VERTICAL
//                )
//            )
//        }
//    }

    private fun setupRecyclerView() {
        adapter = PaymentListAdapter(emptyList())

        binding.rvPaymentTable.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@PaymentList.adapter
            setHasFixedSize(true)

            val divider = DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
            divider.setDrawable(
                androidx.core.content.ContextCompat.getDrawable(
                    requireContext(),
                    com.example.toucan_vinyl.R.drawable.divider_light
                )!!
            )
            addItemDecoration(divider)
        }
    }


    private fun loadPayments() {
        lifecycleScope.launch {
            val payments = db.paymentDao().getAll()
            adapter.updateData(payments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
