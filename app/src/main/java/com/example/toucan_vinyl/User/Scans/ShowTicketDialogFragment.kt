package com.example.toucan_vinyl.User.Scans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.toucan_vinyl.Data.Model.TicketModel
import com.example.toucan_vinyl.databinding.FragmentShowTicketDialogBinding
import java.io.Serializable

class ShowTicketDialogFragment : DialogFragment() {

    private var _binding: FragmentShowTicketDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowTicketDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ticket = requireArguments()
            .getSerializable("ticket") as TicketModel

        binding.tvArtist.text = ticket.nama_artist
        binding.tvConcert.text = ticket.nama_konser
        binding.tvTour.text = ticket.nama_festival ?: "-"
        binding.tvDate.text = ticket.tanggal_konser
        binding.tvLocation.text =
            "${ticket.lokasi_kota}, ${ticket.lokasi_negara}"
        binding.tvTicketCode.text = ticket.ticket_code
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(ticket: TicketModel) =
            ShowTicketDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ticket", ticket as Serializable)
                }
            }
    }
}
