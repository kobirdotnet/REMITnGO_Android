package com.bsel.remitngo.ui.document

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bsel.remitngo.R
import com.bsel.remitngo.bottom_sheet.CategoryBottomSheet
import com.bsel.remitngo.bottom_sheet.DocumentBottomSheet
import com.bsel.remitngo.bottom_sheet.SelectDocumentsBottomSheet
import com.bsel.remitngo.databinding.FragmentUploadDocumentsBinding
import com.bsel.remitngo.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.model.Category
import com.bsel.remitngo.model.Document
import java.util.*

class UploadDocumentsFragment : Fragment(),OnDocumentItemSelectedListener {

    private lateinit var binding: FragmentUploadDocumentsBinding

    private val categoryBottomSheet: CategoryBottomSheet by lazy { CategoryBottomSheet() }
    private val documentBottomSheet: DocumentBottomSheet by lazy { DocumentBottomSheet() }

    private val selectDocumentsBottomSheet: SelectDocumentsBottomSheet by lazy { SelectDocumentsBottomSheet() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadDocumentsBinding.bind(view)

        categoryFocusListener()
        documentFocusListener()
        documentNoFocusListener()
        issueByFocusListener()
        issueDateFocusListener()
        expireDateFocusListener()

        binding.category.setOnClickListener {
            categoryBottomSheet.itemSelectedListener = this
            categoryBottomSheet.show(childFragmentManager, categoryBottomSheet.tag)
        }
        binding.document.setOnClickListener {
            documentBottomSheet.itemSelectedListener = this
            documentBottomSheet.show(childFragmentManager, documentBottomSheet.tag)
        }

        binding.issueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                        binding.issueDate.setText(formattedDate)
                    }
                }, year, month, day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.expireDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.before(Calendar.getInstance())) {
                        val formattedDate =
                            "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                        binding.expireDate.setText(formattedDate)
                    }
                }, year, month, day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.selectFile.setOnClickListener {
            selectDocumentsBottomSheet.show(childFragmentManager, selectDocumentsBottomSheet.tag)
        }

        binding.btnSaveDocument.setOnClickListener { documentFrom() }

    }

    private fun documentFrom() {
        binding.categoryContainer.helperText = validCategory()
        binding.documentContainer.helperText = validDocument()
        binding.documentNoContainer.helperText = validDocumentNo()
        binding.issueByContainer.helperText = validIssueBy()
        binding.issueDateContainer.helperText = validIssueDate()
        binding.expireDateContainer.helperText = validExpireDate()

        val validCategory = binding.categoryContainer.helperText == null
        val validDocument = binding.documentContainer.helperText == null
        val validDocumentNo = binding.documentNoContainer.helperText == null
        val validIssueBy = binding.issueByContainer.helperText == null
        val validIssueDate = binding.issueDateContainer.helperText == null
        val validExpireDate = binding.expireDateContainer.helperText == null

        if (validCategory && validDocument && validDocumentNo && validIssueBy
            && validIssueDate && validExpireDate
        ) {
            submitDocumentFrom()
        }
    }

    private fun submitDocumentFrom() {
        val category = binding.category.text.toString()
        val document = binding.document.text.toString()
        val documentNo = binding.documentNo.text.toString()
        val issueBy = binding.issueBy.text.toString()
        val issueDate = binding.issueDate.text.toString()
        val expireDate = binding.expireDate.text.toString()

    }

    //Form validation
    private fun categoryFocusListener() {
        binding.category.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.categoryContainer.helperText = validCategory()
            }
        }
    }

    private fun validCategory(): String? {
        val category = binding.category.text.toString()
        if (category.isEmpty()) {
            return "select category"
        }
        return null
    }

    private fun documentFocusListener() {
        binding.document.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.documentContainer.helperText = validDocument()
            }
        }
    }

    private fun validDocument(): String? {
        val document = binding.document.text.toString()
        if (document.isEmpty()) {
            return "select document"
        }
        return null
    }

    private fun documentNoFocusListener() {
        binding.documentNo.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.documentNoContainer.helperText = validDocumentNo()
            }
        }
    }

    private fun validDocumentNo(): String? {
        val documentNo = binding.documentNo.text.toString()
        if (documentNo.isEmpty()) {
            return "enter document no"
        }
        return null
    }

    private fun issueByFocusListener() {
        binding.issueBy.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.issueByContainer.helperText = validIssueBy()
            }
        }
    }

    private fun validIssueBy(): String? {
        val issueBy = binding.issueBy.text.toString()
        if (issueBy.isEmpty()) {
            return "enter issue by"
        }
        return null
    }

    private fun issueDateFocusListener() {
        binding.issueDate.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.issueDateContainer.helperText = validIssueDate()
            }
        }
    }

    private fun validIssueDate(): String? {
        val issueDate = binding.issueDate.text.toString()
        if (issueDate.isEmpty()) {
            return "enter issue date"
        }
        return null
    }

    private fun expireDateFocusListener() {
        binding.expireDate.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.expireDateContainer.helperText = validExpireDate()
            }
        }
    }

    private fun validExpireDate(): String? {
        val expireDate = binding.expireDate.text.toString()
        if (expireDate.isEmpty()) {
            return "enter expire date"
        }
        return null
    }

    override fun onCategoryItemSelected(selectedItem: Category) {
        binding.category.setText(selectedItem.categoryName)
    }

    override fun onDocumentItemSelected(selectedItem: Document) {
        binding.document.setText(selectedItem.documentName)
    }

}