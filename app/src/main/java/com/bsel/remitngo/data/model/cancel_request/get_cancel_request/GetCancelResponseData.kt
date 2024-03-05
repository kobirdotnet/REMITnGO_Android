package com.bsel.remitngo.data.model.cancel_request.get_cancel_request


import com.google.gson.annotations.SerializedName

data class GetCancelResponseData(
    @SerializedName("AVSResponseCode")
    val aVSResponseCode: String?,
    @SerializedName("ActualCreditDate")
    val actualCreditDate: String?,
    @SerializedName("ActualSendAmount")
    val actualSendAmount: Double?,
    @SerializedName("ActualTotalCommission")
    val actualTotalCommission: Double?,
    @SerializedName("AdminCharge")
    val adminCharge: Double?,
    @SerializedName("AgentCode")
    val agentCode: String?,
    @SerializedName("AgentCommission")
    val agentCommission: Double?,
    @SerializedName("AgentCommissionPercent")
    val agentCommissionPercent: Double?,
    @SerializedName("AgentCommissionPercentage")
    val agentCommissionPercentage: Double?,
    @SerializedName("AgentId")
    val agentId: Int?,
    @SerializedName("ApprovedBy")
    val approvedBy: Int?,
    @SerializedName("ApprovedDate")
    val approvedDate: String?,
    @SerializedName("BAFlag_Agent")
    val bAFlagAgent: Int?,
    @SerializedName("BAFlag_PAgent")
    val bAFlagPAgent: Int?,
    @SerializedName("BSECommission")
    val bSECommission: Double?,
    @SerializedName("BSECommissionPayout")
    val bSECommissionPayout: Double?,
    @SerializedName("BSEDealRate")
    val bSEDealRate: Double?,
    @SerializedName("BaseEqivFromCurRate")
    val baseEqivFromCurRate: Double?,
    @SerializedName("BaseForexProfit")
    val baseForexProfit: Double?,
    @SerializedName("BaseProfitGainLoss")
    val baseProfitGainLoss: Double?,
    @SerializedName("BeneAccountName")
    val beneAccountName: String?,
    @SerializedName("BeneAccountNo")
    val beneAccountNo: String?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("BeneBankId")
    val beneBankId: Int?,
    @SerializedName("BeneBranchId")
    val beneBranchId: Int?,
    @SerializedName("BeneCollectionPointId")
    val beneCollectionPointId: Int?,
    @SerializedName("BeneId")
    val beneId: Int?,
    @SerializedName("BeneMobile")
    val beneMobile: String?,
    @SerializedName("BeneTypeId")
    val beneTypeId: Int?,
    @SerializedName("BeneTypeIdNo")
    val beneTypeIdNo: String?,
    @SerializedName("BeneWalletId")
    val beneWalletId: Int?,
    @SerializedName("BeneWalletNo")
    val beneWalletNo: String?,
    @SerializedName("CRAccNo")
    val cRAccNo: Int?,
    @SerializedName("CancelReasion")
    val cancelReasion: String?,
    @SerializedName("CashCommission")
    val cashCommission: Double?,
    @SerializedName("CommissionICT")
    val commissionICT: Double?,
    @SerializedName("CompanyRate")
    val companyRate: Double?,
    @SerializedName("ConfirmBy")
    val confirmBy: Int?,
    @SerializedName("ConfirmedBy")
    val confirmedBy: Int?,
    @SerializedName("ConfirmedDate")
    val confirmedDate: String?,
    @SerializedName("CustomerId")
    val customerId: Int?,
    @SerializedName("DRAccNo")
    val dRAccNo: Int?,
    @SerializedName("DepositBankName")
    val depositBankName: String?,
    @SerializedName("EntryBy")
    val entryBy: Int?,
    @SerializedName("EntryDate")
    val entryDate: String?,
    @SerializedName("EstimatedCreditDate")
    val estimatedCreditDate: String?,
    @SerializedName("ExchangeCommission")
    val exchangeCommission: Double?,
    @SerializedName("ExportedFlag")
    val exportedFlag: Boolean?,
    @SerializedName("Extra_Commission")
    val extraCommission: Double?,
    @SerializedName("ForexProfit")
    val forexProfit: Double?,
    @SerializedName("FromCountryId")
    val fromCountryId: Int?,
    @SerializedName("FromCurrencyId")
    val fromCurrencyId: Int?,
    @SerializedName("HoldedBy")
    val holdedBy: Int?,
    @SerializedName("HoledDate")
    val holedDate: String?,
    @SerializedName("ICTCharge")
    val iCTCharge: Double?,
    @SerializedName("IDNo")
    val iDNo: Int?,
    @SerializedName("IPAddress")
    val iPAddress: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Instructions")
    val instructions: String?,
    @SerializedName("IsBeneSMS")
    val isBeneSMS: Boolean?,
    @SerializedName("IsMG")
    val isMG: Boolean?,
    @SerializedName("IsMobileTransfer")
    val isMobileTransfer: Boolean?,
    @SerializedName("IsiOS")
    val isiOS: Boolean?,
    @SerializedName("MGSessionId")
    val mGSessionId: String?,
    @SerializedName("ModifiedCashCommission")
    val modifiedCashCommission: Double?,
    @SerializedName("ModifiedExchangeCommission")
    val modifiedExchangeCommission: Double?,
    @SerializedName("ModifiedNormalCommission")
    val modifiedNormalCommission: Double?,
    @SerializedName("ModifiedRate")
    val modifiedRate: Double?,
    @SerializedName("ModifiedTotalCommission")
    val modifiedTotalCommission: Double?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("NetAgentCommission")
    val netAgentCommission: Double?,
    @SerializedName("NormalCommission")
    val normalCommission: Double?,
    @SerializedName("OrderStatus")
    val orderStatus: Int?,
    @SerializedName("OrderType")
    val orderType: Int?,
    @SerializedName("OrderTypeDescription")
    val orderTypeDescription: Any?,
    @SerializedName("PRDDate")
    val pRDDate: String?,
    @SerializedName("PRDExportDate")
    val pRDExportDate: String?,
    @SerializedName("PRDNo")
    val pRDNo: Int?,
    @SerializedName("PRDStatus")
    val pRDStatus: String?,
    @SerializedName("PayOutAgentID")
    val payOutAgentID: Int?,
    @SerializedName("PayingAgentID")
    val payingAgentID: Int?,
    @SerializedName("PayingAgentNote")
    val payingAgentNote: String?,
    @SerializedName("PaymentStatus")
    val paymentStatus: Int?,
    @SerializedName("PaymentType")
    val paymentType: Int?,
    @SerializedName("PayoutAgentCommission")
    val payoutAgentCommission: Double?,
    @SerializedName("PayoutAgentCommissionPercentage")
    val payoutAgentCommissionPercentage: Double?,
    @SerializedName("PayoutCommission")
    val payoutCommission: Double?,
    @SerializedName("ProcessBy")
    val processBy: Int?,
    @SerializedName("ProcessDate")
    val processDate: String?,
    @SerializedName("ProcessNote")
    val processNote: Any?,
    @SerializedName("ProfitGainLoss")
    val profitGainLoss: Double?,
    @SerializedName("PurposeOfTransferId")
    val purposeOfTransferId: Int?,
    @SerializedName("QrValue")
    val qrValue: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("RateGroupId")
    val rateGroupId: Int?,
    @SerializedName("Rebate")
    val rebate: Double?,
    @SerializedName("RnGPaymentMethod")
    val rnGPaymentMethod: Int?,
    @SerializedName("SecurityQueueReleaseDate")
    val securityQueueReleaseDate: String?,
    @SerializedName("SecurityQueueStatus")
    val securityQueueStatus: Boolean?,
    @SerializedName("SendAmount")
    val sendAmount: Double?,
    @SerializedName("SourceOfFundId")
    val sourceOfFundId: Int?,
    @SerializedName("SpreadOfferedtoCompany")
    val spreadOfferedtoCompany: Double?,
    @SerializedName("SubAgentName")
    val subAgentName: String?,
    @SerializedName("Sum_Total")
    val sumTotal: Double?,
    @SerializedName("Sum_TotalAgent")
    val sumTotalAgent: Double?,
    @SerializedName("TargetEarning")
    val targetEarning: Double?,
    @SerializedName("Tax")
    val tax: Double?,
    @SerializedName("ToCountryId")
    val toCountryId: Int?,
    @SerializedName("ToCurrencyId")
    val toCurrencyId: Int?,
    @SerializedName("TotalAgentIct")
    val totalAgentIct: Double?,
    @SerializedName("TotalAmountGiven")
    val totalAmountGiven: Double?,
    @SerializedName("TotalCommission")
    val totalCommission: Double?,
    @SerializedName("TotalProfitByBSE")
    val totalProfitByBSE: Double?,
    @SerializedName("TransReferenceNo")
    val transReferenceNo: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionMode")
    val transactionMode: Int?,
    @SerializedName("UpdateBy")
    val updateBy: Int?,
    @SerializedName("UpdateDate")
    val updateDate: String?,
    @SerializedName("WHoldingTax")
    val wHoldingTax: Double?
)