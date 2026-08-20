package com.samm.expense_tracker.auth

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var phoneNumber by mutableStateOf("")
        private set

    var otp by mutableStateOf("")
        private set

    var verificationId by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isLoggedIn by mutableStateOf(
        auth.currentUser != null
    )
        private set

    fun onPhoneNumberChanged(value: String) {
        phoneNumber = value
        errorMessage = null
    }

    fun onOtpChanged(value: String) {
        otp = value
        errorMessage = null
    }

    fun sendOtp(
        activity: Activity
    ) {

        if (phoneNumber.length != 10) {
            errorMessage = "Enter a valid 10-digit phone number"
            return
        }

        isLoading = true

        val fullPhoneNumber =
            "+91$phoneNumber"

        val callbacks =
            object :
                PhoneAuthProvider
                .OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(
                    credential: PhoneAuthCredential
                ) {
                    signInWithCredential(
                        credential
                    )
                }

                override fun onVerificationFailed(
                    exception: FirebaseException
                ) {
                    isLoading = false

                    errorMessage =
                        exception.message
                            ?: "Verification failed"
                }

                override fun onCodeSent(
                    id: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = id
                    isLoading = false
                }
            }

        val options =
            PhoneAuthOptions
                .newBuilder(auth)
                .setPhoneNumber(
                    fullPhoneNumber
                )
                .setTimeout(
                    60L,
                    TimeUnit.SECONDS
                )
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()

        PhoneAuthProvider.verifyPhoneNumber(
            options
        )
    }

    fun verifyOtp() {

        val id = verificationId

        if (id == null) {
            errorMessage =
                "Request OTP first"
            return
        }

        if (otp.length != 6) {
            errorMessage =
                "Enter the 6-digit OTP"
            return
        }

        isLoading = true

        val credential =
            PhoneAuthProvider.getCredential(
                id,
                otp
            )

        signInWithCredential(
            credential
        )
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential
    ) {

        auth.signInWithCredential(
            credential
        )
            .addOnCompleteListener { task ->

                isLoading = false

                if (task.isSuccessful) {

                    isLoggedIn = true
                    errorMessage = null

                } else {

                    errorMessage =
                        task.exception?.message
                            ?: "Login failed"
                }
            }
    }
}