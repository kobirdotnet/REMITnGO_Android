package com.bsel.remitngo.presentation.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.ChangePasswordBottomSheet
import com.bsel.remitngo.databinding.ActivityMainBinding
import com.bsel.remitngo.presentation.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private lateinit var changePassword: String

    private val changePasswordBottomSheet: ChangePasswordBottomSheet by lazy { ChangePasswordBottomSheet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main,
                R.id.nav_my_profile,
                R.id.nav_documents,
                R.id.nav_transaction_history,
                R.id.nav_cancellation,
                R.id.nav_generate_query,
                R.id.nav_settings,
                R.id.nav_support
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerView = navView.getHeaderView(0)

        val customerName = headerView.findViewById<TextView>(R.id.customerName)
        val customerEmailAddress = headerView.findViewById<TextView>(R.id.customerEmailAddress)
        val customerPhoneNumber = headerView.findViewById<TextView>(R.id.customerPhoneNumber)

        val drawerClose = headerView.findViewById<ImageView>(R.id.drawerClose)

        val referralBonus = headerView.findViewById<TextView>(R.id.referralBonus)
        val referralBonusTxt = headerView.findViewById<TextView>(R.id.referralBonusTxt)
        val referralCodeTxt = headerView.findViewById<TextView>(R.id.referralCodeTxt)
        val referralCode = headerView.findViewById<TextView>(R.id.referralCode)
        val inviteReferralCode = headerView.findViewById<TextView>(R.id.inviteReferralCode)

        val transfer = headerView.findViewById<LinearLayout>(R.id.transfer)
        val myProfile = headerView.findViewById<LinearLayout>(R.id.myProfile)
        val kycDocument = headerView.findViewById<LinearLayout>(R.id.kyc_document)
        val transactionHistory = headerView.findViewById<LinearLayout>(R.id.transaction_history)
        val cancelRequest = headerView.findViewById<LinearLayout>(R.id.cancel_request)
        val generateQuery = headerView.findViewById<LinearLayout>(R.id.generate_query)
        val beneficiary = headerView.findViewById<LinearLayout>(R.id.beneficiary)
        val settings = headerView.findViewById<LinearLayout>(R.id.settings)
        val support = headerView.findViewById<LinearLayout>(R.id.support)
        val logOut = headerView.findViewById<LinearLayout>(R.id.logOut)

        customerName.text = "Mohammad Kobirul Islam"
        customerEmailAddress.text = "kobirdotnet@gmail.com"
        customerPhoneNumber.text = "+8801535111573"

        referralBonus.text = "Share your unique id with your friends and families and get discount."
        referralBonusTxt.text =
            "You will receive GBP 5.00 and your friend will receive GBP 5.00 on their first transfer. Minimum send requirements and conditions apply."
        referralCodeTxt.text = "Your Unique ID: "
        referralCode.text = "CM007788"
        inviteReferralCode.text = "Share Now"

        inviteReferralCode.setOnClickListener {
            val url =
                "I am recommending to you send money abroad using the new REMITnGO mobile app. Your first transaction is completely FREE - just use the code " + referralCode.text.toString() + " when you sign up. The app is free to download, get it now from https://app.remitngo.com"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "REMITnGO"))
        }

        drawerClose.setOnClickListener { drawerLayout.close() }

        transfer.setOnClickListener {
            navController.navigate(R.id.nav_main)
            drawerLayout.close()
        }
        myProfile.setOnClickListener {
            navController.navigate(R.id.nav_my_profile)
            drawerLayout.close()
        }
        kycDocument.setOnClickListener {
            navController.navigate(R.id.nav_documents)
            drawerLayout.close()
        }
        transactionHistory.setOnClickListener {
            navController.navigate(R.id.nav_transaction_history)
            drawerLayout.close()
        }
        cancelRequest.setOnClickListener {
            navController.navigate(R.id.nav_cancellation)
            drawerLayout.close()
        }
        generateQuery.setOnClickListener {
            navController.navigate(R.id.nav_generate_query)
            drawerLayout.close()
        }
        settings.setOnClickListener {
            navController.navigate(R.id.nav_settings)
            drawerLayout.close()
        }
        support.setOnClickListener {
            navController.navigate(R.id.nav_support)
            drawerLayout.close()
        }

        logOut.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Are you sure you want to log out?")
            builder.setMessage("You'll need to login again before sending a transfer.")
            builder.setPositiveButton("LOG OUT") { _: DialogInterface, _: Int ->
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("CANCEL") { _: DialogInterface, _: Int -> }
            val dialog = builder.create()
            dialog.show()
        }

        changePassword = intent.getStringExtra("changePassword").toString()
        Log.i("info", "changePassword: $changePassword")
        if (changePassword == "12345") {
            changePasswordBottomSheet.show(supportFragmentManager, changePasswordBottomSheet.tag)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (!isFinishing && !isDestroyed) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("App termination")
                builder.setMessage("Do you want to close the app?")
                builder.setPositiveButton("EXIT") { _, _ ->
                    ActivityCompat.finishAffinity(this)
                }
                builder.setNegativeButton("STAY") { _, _ ->
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyBoard(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyBoard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

}