package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.RequireDocumentAdapter
import com.bsel.remitngo.data.interfaceses.OnRequireDocumentListener
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentData
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.databinding.RequireDocumentLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModel
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class RequireDocumentBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    var itemSelectedListener: OnRequireDocumentListener? = null

    private lateinit var binding: RequireDocumentLayoutBinding

    private lateinit var requireDocumentBehavior: BottomSheetBehavior<*>

    private lateinit var requireDocumentAdapter: RequireDocumentAdapter

    private val uploadRequireDocumentBottomSheet: UploadRequireDocumentBottomSheet by lazy { UploadRequireDocumentBottomSheet() }

    private lateinit var totalAmount: String
    private lateinit var benId: String
    private lateinit var customerId: String
    private lateinit var currentDate: String
    private lateinit var reasonId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.require_document_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        requireDocumentBehavior = BottomSheetBehavior.from(view.parent as View)
        requireDocumentBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        requireDocumentBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }
        binding.btnLater.setOnClickListener { paymentLater("paymentLater") }

        binding.btnUpload.setOnClickListener {
            if (!uploadRequireDocumentBottomSheet.isAdded) {
                uploadRequireDocumentBottomSheet.requireDocument(
                    totalAmount,
                    benId,
                    customerId,
                    currentDate,
                    reasonId
                )
                uploadRequireDocumentBottomSheet.show(
                    childFragmentManager,
                    uploadRequireDocumentBottomSheet.tag
                )
            }
        }

        paymentViewModel.requireDocMsg("3")

        val requireDocumentItem = RequireDocumentItem(
            agentId = 8082,
            amount = totalAmount.toDouble(),
            beneficiaryId = benId.toInt(),
            customerId = customerId.toInt(),
            entryDate = currentDate,
            purposeOfTransferId = reasonId.toInt(),
            transactionType = 1
        )
        paymentViewModel.requireDocument(requireDocumentItem)

        observeRequireDocMsgResult()
        observeRequireDocumentResult()

        return bottomSheet
    }

    private fun paymentLater(paymentLater: String) {
        itemSelectedListener?.onRequireDocumentSelected(paymentLater)
        dismiss()
    }

    fun requireDocument(
        requireAmount: String,
        requireBenId: String,
        requireCustomerId: String,
        requireCurrentDate: String,
        requireReasonId: String
    ) {
        totalAmount = requireAmount
        benId = requireBenId
        customerId = requireCustomerId
        currentDate = requireCurrentDate
        reasonId = requireReasonId
    }

    private fun observeRequireDocMsgResult() {
        paymentViewModel.requireDocMsgResult.observe(this) { result ->
            if (result!!.code == "000") {
                if (result!!.data != null) {
                    for (msg in result!!.data!!) {
                        if (msg!!.id == 1) {
                            binding.msgOne.text = msg.name
                        }
                        if (msg!!.id == 2) {
                            binding.msgTwo.text = msg.name
                        }
                    }
                }
            }
        }
    }

    private fun observeRequireDocumentResult() {
        paymentViewModel.requireDocumentResult.observe(this) { result ->
            if (result!!.code == "000") {
                if (result!!.data != null) {
                    binding.requireDocumentRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    requireDocumentAdapter = RequireDocumentAdapter()
                    binding.requireDocumentRecyclerView.adapter = requireDocumentAdapter
                    requireDocumentAdapter.setList(result.data as List<RequireDocumentData>)
                    requireDocumentAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireDocumentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}