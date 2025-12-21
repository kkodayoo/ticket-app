package com.example.toucan_vinyl.User.Payment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.Data.AppDatabase
import com.example.toucan_vinyl.Data.Model.MenuItem
import com.example.toucan_vinyl.Data.entity.PaymentEntity
import com.example.toucan_vinyl.User.Scans.ScanFragment
import com.example.toucan_vinyl.User.Scans.TicketscanFragment
import com.example.toucan_vinyl.databinding.FragmentPaymentBinding
import com.example.toucan_vinyl.utils.dp
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var paymentAdapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        setupMenu()
        setupPaymentList()
        loadPayments()
    }

    private fun setupMenu() {
        val menuItems = listOf(
            MenuItem("Edit Profile", R.drawable.ic_edit, PaymentFormFragment()),
            MenuItem("Account Settings", R.drawable.ic_user, PaymentFormFragment()),
            MenuItem("Payment Methods", R.drawable.ic_email, PaymentFormFragment()),
            MenuItem("Tickets", R.drawable.ic_info, TicketscanFragment()),
            MenuItem("Privacy & Security", R.drawable.ic_more, PaymentFormFragment()),
            MenuItem("About Application", R.drawable.ic_info, PaymentFormFragment())
        )

        val adapter = UserMenuAdapter(menuItems) { item ->
            navigateTo(item.fragment)
        }

        binding.rvMenu.adapter = adapter
        binding.rvMenu.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.bottom = 12.dp
            }
        })

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.menu_item_divider)!!)
        binding.rvMenu.addItemDecoration(divider)
    }

    private fun setupPaymentList() {
        paymentAdapter = PaymentAdapter(listOf()) { payment ->
            deletePayment(payment)
        }
        binding.rvPayment.adapter = paymentAdapter
    }

    private fun loadPayments() {
        lifecycleScope.launch {
            val list = db.paymentDao().getAll()
            paymentAdapter.updateData(list)
        }
    }

    private fun deletePayment(payment: PaymentEntity) {
        lifecycleScope.launch {
            db.paymentDao().delete(payment)
            loadPayments()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
