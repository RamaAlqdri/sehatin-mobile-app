package com.example.sehatin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import com.example.sehatin.data.model.response.VerifyOtpResponse
import com.example.sehatin.data.repository.OtpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OtpScreenViewModel(
    private val otpRepository: OtpRepository
) : ViewModel() {
    var emailValue by mutableStateOf("")
        private set

    var otpData by mutableStateOf(
        VerifyOtpRequest(
            "", ""
        )
    )

    var otpValue by mutableStateOf("")
        private set

    private val _isValid =
        MutableStateFlow(false)
    val isValid: MutableStateFlow<Boolean> = _isValid


    fun setEmail(value: String) {
        emailValue = value
    }

    fun setOtp(value: String) {
        if (value.length <= 6) {
            otpValue = value
        }
    }

    private val _otpState = MutableStateFlow<ResultResponse<OtpResponse>>(ResultResponse.None)
    val otpState: StateFlow<ResultResponse<OtpResponse>> = _otpState

    private val _verifyOtpState =
        MutableStateFlow<ResultResponse<VerifyOtpResponse>>(ResultResponse.None)
    val verifyOtpState: StateFlow<ResultResponse<VerifyOtpResponse>> = _verifyOtpState

    fun verifyOtp() {
        if (otpValue.isNotBlank()) {
            viewModelScope.launch {
                try {
                    _verifyOtpState.value = ResultResponse.Loading
//                    delay(2000)
//                    _verifyOtpState.value =
//                        ResultResponse.Success(VerifyOtpResponse("Success", 2, "d"))
//                    _verifyOtpState.value = ResultResponse.Error("Failed to verify OTP")
                    otpRepository.verifyOtp(email = emailValue, otp = otpValue)
                        .collect { result ->
                            _verifyOtpState.value = result
                            otpData.otpCode = otpValue
                            otpData.email = emailValue
                        }
                    if (_verifyOtpState.value is ResultResponse.Success) {
                        otpData = VerifyOtpRequest(email = emailValue, otpCode = otpValue)

                    }

                } catch (e: Exception) {
                    _verifyOtpState.value =
                        ResultResponse.Error(e.localizedMessage ?: "Network error")
                }
            }
        } else {
            _verifyOtpState.value = ResultResponse.Error("Please enter OTP")

        }
    }

    fun requestOtp() {
        viewModelScope.launch {
            try {
                otpRepository.generateOtp(emailValue)
                    .collect { result ->
                        _otpState.value = result
                    }
            } catch (e: Exception) {
                _otpState.value = ResultResponse.Error("Failed to generate OTP: ${e.message}")
            }
        }
    }
}