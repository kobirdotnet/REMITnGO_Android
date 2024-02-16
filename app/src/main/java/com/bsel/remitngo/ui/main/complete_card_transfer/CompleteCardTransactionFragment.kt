package com.bsel.remitngo.ui.main.complete_card_transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentCompleteCardTransactionBinding

class CompleteCardTransactionFragment : Fragment() {

    private lateinit var binding: FragmentCompleteCardTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_complete_card_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCompleteCardTransactionBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_card_transaction_to_nav_main)
        }

    }

}