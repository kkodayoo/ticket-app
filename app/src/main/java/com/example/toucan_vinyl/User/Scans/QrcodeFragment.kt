package com.example.toucan_vinyl.User.Scans

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toucan_vinyl.Data.Api.TicketApiClient
import com.example.toucan_vinyl.databinding.FragmentQrcodeBinding
import kotlinx.coroutines.launch

class QrcodeFragment : Fragment() {

    private var _binding: FragmentQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadTickets()
    }

    private fun loadTickets() {
        lifecycleScope.launch {
            try {
                val response = TicketApiClient.apiService.getAllTickets()
                val tickets = response.body() ?: emptyList()

                binding.rvTickets.layoutManager = LinearLayoutManager(requireContext())
                binding.rvTickets.adapter = TicketListAdapter(tickets) { ticket ->
                    TicketQrDialogFragment.newInstance(ticket.ticket_code)
                        .show(parentFragmentManager, "qr_dialog")
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat tiket", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
