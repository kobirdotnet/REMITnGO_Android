package com.bsel.remitngo.presentation

import android.app.Application
import android.util.Log
import com.bsel.remitngo.BuildConfig
import com.bsel.remitngo.data.api.Config
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.di.bank.BankSubComponent
import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.calculation.CalculationSubComponent
import com.bsel.remitngo.presentation.di.cancel_request.CancelRequestSubComponent
import com.bsel.remitngo.presentation.di.core.*
import com.bsel.remitngo.presentation.di.document.DocumentSubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.notification.NotificationSubComponent
import com.bsel.remitngo.presentation.di.payment.PaymentSubComponent
import com.bsel.remitngo.presentation.di.profile.ProfileSubComponent
import com.bsel.remitngo.presentation.di.query.QuerySubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent
import com.bsel.remitngo.presentation.di.settings.SettingsSubComponent
import com.bsel.remitngo.presentation.di.support.SupportSubComponent
import com.bsel.remitngo.presentation.di.transaction.TransactionSubComponent
import com.google.firebase.messaging.FirebaseMessaging

const val TOPIC_NEWS = "news"
class App : Application(), Injector {
    companion object {
        private const val TAG = "App"
    }
    override fun onCreate() {
        super.onCreate()
        subscribeToTopic(TOPIC_NEWS)
    }
    // Subscribe to a topic
    private fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to topic: $topic")
                } else {
                    Log.e(TAG, "Subscription to topic $topic failed", task.exception)
                }
            }
    }

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(Config.DYNAMIC_BASE_URL))
            .useCaseModule(UseCaseModule())
            .repositoryModule(RepositoryModule())
            .remoteDataModule(RemoteDataModule())
            .build()
    }
    override fun createRegistrationSubComponent(): RegistrationSubComponent {
        return appComponent.registrationSubComponent().create()
    }
    override fun createLoginSubComponent(): LoginSubComponent {
        return appComponent.loginSubComponent().create()
    }
    override fun createBeneficiarySubComponent(): BeneficiarySubComponent {
        return appComponent.beneficiarySubComponent().create()
    }
    override fun createBankSubComponent(): BankSubComponent {
        return appComponent.bankSubComponent().create()
    }
    override fun createCalculationSubComponent(): CalculationSubComponent {
        return appComponent.calculationSubComponent().create()
    }
    override fun createPaymentSubComponent(): PaymentSubComponent {
        return appComponent.paymentSubComponent().create()
    }
    override fun createProfileSubComponent(): ProfileSubComponent {
        return appComponent.profileSubComponent().create()
    }
    override fun createDocumentSubComponent(): DocumentSubComponent {
        return appComponent.documentSubComponent().create()
    }
    override fun createTransactionSubComponent(): TransactionSubComponent {
        return appComponent.transactionSubComponent().create()
    }
    override fun createCancelRequestSubComponent(): CancelRequestSubComponent {
        return appComponent.cancelRequestSubComponent().create()
    }
    override fun createQuerySubComponent(): QuerySubComponent {
        return appComponent.querySubComponent().create()
    }
    override fun createSettingsSubComponent(): SettingsSubComponent {
        return appComponent.settingsSubComponent().create()
    }
    override fun createSupportSubComponent(): SupportSubComponent {
        return appComponent.supportSubComponent().create()
    }
    override fun createNotificationSubComponent(): NotificationSubComponent {
        return appComponent.notificationSubComponent().create()
    }
}