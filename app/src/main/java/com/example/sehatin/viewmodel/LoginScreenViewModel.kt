package com.example.sehatin.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.ChangePasswordResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.LoginResponse
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<ResultResponse<LoginResponse>>(ResultResponse.None)
    val loginState: StateFlow<ResultResponse<LoginResponse>> = _loginState

    private val _forgotState =
        MutableStateFlow<ResultResponse<LoginResponse>>(ResultResponse.None)
    val forgotState: StateFlow<ResultResponse<LoginResponse>> = _forgotState

    private val _reqOtpState = MutableStateFlow<ResultResponse<OtpResponse>>(ResultResponse.None)
    val reqOtpState: StateFlow<ResultResponse<OtpResponse>> = _reqOtpState


    private val _userState =
        MutableStateFlow<ResultResponse<GetUserResponse>>(ResultResponse.None)
    val userState: StateFlow<ResultResponse<GetUserResponse>> = _userState


    private val _changePasswordState =
        MutableStateFlow<ResultResponse<ChangePasswordResponse>>(ResultResponse.None)
    val changePasswordState: StateFlow<ResultResponse<ChangePasswordResponse>> = _changePasswordState

    private val _resetPasswordState =
        MutableStateFlow<ResultResponse<ChangePasswordResponse>>(ResultResponse.None)
    val resetPasswordState: StateFlow<ResultResponse<ChangePasswordResponse>> = _resetPasswordState

    var newPasswordValue by mutableStateOf("")
        private set
    var newPasswordError by mutableStateOf("")

    var confirmPasswordValue by mutableStateOf("")
        private set
    var confirmPasswordError by mutableStateOf("")

    var oldPasswordValue by mutableStateOf("")
        private set
    var oldPasswordError by mutableStateOf("")


    var passwordValue by mutableStateOf("")
        private set

    var passwordError by mutableStateOf("")
        private set


    var otpValue by mutableStateOf("")
        private set

    var otpError by mutableStateOf("")
        private set

    var emailValue by mutableStateOf("")
        private set
    var emailError by mutableStateOf("")
        private set



    fun resetEmailAndPassword() {
        emailValue = ""
        passwordValue = ""
        emailError = ""
        passwordError = ""
    }

    fun resetChangePasswordState() {
        _changePasswordState.value = ResultResponse.None
    }


    fun resetRequesOtpState() {
        _reqOtpState.value = ResultResponse.None
    }
    private fun validateConfirmPassword(): Boolean {
        val confirmPassword = confirmPasswordValue.trim()
        return when {
            confirmPassword.isBlank() -> {
                confirmPasswordError = "Please fill password field"
                false
            }

            confirmPassword != newPasswordValue -> {
                confirmPasswordError = "Passwords do not match"
                false
            }

            else -> {
                confirmPasswordError = ""
                true
            }
        }
    }

    fun setNewPassword(value: String) {
        newPasswordValue = value
        validateNewPassword()
    }

    fun setConfirmPassword(value: String) {
        confirmPasswordValue = value
        validateConfirmPassword()
    }
    fun setOldPassword(value: String) {
        oldPasswordValue = value
    }



    private fun validateNewPassword(): Boolean {
        val newPassword = newPasswordValue.trim()
        val containsLetter = newPassword.any { it.isLetter() }
        val containsDigit = newPassword.any { it.isDigit() }

        return when {
            newPassword.isBlank() -> {
                newPasswordError = "Please fill password field"
                false
            }

            newPassword.length < 6 -> {
                newPasswordError = "Password must be at least 6 characters long"
                false
            }

            !containsLetter -> {
                newPasswordError = "Password must contain at least one letter"
                false
            }

            !containsDigit -> {
                newPasswordError = "Password must contain at least one digit"
                false
            }

            else -> {
                newPasswordError = ""
                true
            }
        }
    }


    fun resetPassword() {
        if (validateNewPassword()) {
            viewModelScope.launch {
                try {
                    _resetPasswordState.value = ResultResponse.Loading
                    loginRepository.resetPassword(newPasswordValue)
                        .collect { result ->
                            _resetPasswordState.value = result
                        }
                } catch (e: Exception) {
                    _resetPasswordState.value = ResultResponse.Error("Reset password failed: ${e.message}")
                }
            }
        } else {
            _resetPasswordState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun changePassword() {
        if (validateConfirmPassword()) {
            viewModelScope.launch {
                try {
                    _changePasswordState.value = ResultResponse.Loading
                    loginRepository.changePassword(oldPasswordValue, newPasswordValue)
                        .collect { result ->
                            _changePasswordState.value = result
                        }
                } catch (e: Exception) {
                    _changePasswordState.value = ResultResponse.Error("Change password failed: ${e.message}")
                }
            }
        } else {
            _changePasswordState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun loginUser() {
        if (validateEmail() && validatePassword()) {
            viewModelScope.launch {
                try {
                    _loginState.value = ResultResponse.Loading
                    loginRepository.loginUser(emailValue, passwordValue)
                        .collect { result ->
                            _loginState.value = result
                        }

//                    resetEmailAndPassword()
                } catch (e: Exception) {
                    _loginState.value = ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _loginState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun forgotPass(){
        viewModelScope.launch {
            try {
                _forgotState.value = ResultResponse.Loading
                loginRepository.forgotPass(otpValue, emailValue)
                    .collect { result ->
                        _forgotState.value = result
                    }
            } catch (e: Exception) {
                _loginState.value = ResultResponse.Error("Otp verification failed: ${e.message}")
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                _userState.value = ResultResponse.Loading
                loginRepository.getUser()
                    .collect { result ->
                        _userState.value = result
                    }
            } catch (e: Exception) {
                _userState.value = ResultResponse.Error("Failed to get user: ${e.message}")
            }
        }
    }

    fun setPersonalizeCompleted() {
        viewModelScope.launch {
            loginRepository.setPersonalizeFilled()
        }
    }

    fun checkFilled(): Boolean{
        var isFilled = false
        viewModelScope.launch {
             isFilled = loginRepository.checkFilled()
        }
        return isFilled
    }

    private fun validateEmail(): Boolean {
        val email = emailValue.trim()
        return if (email.isBlank()) {
            emailError = "Email is required"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email address"
            false
        } else {
            emailError = ""
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = passwordValue.trim()
        val containsLetter = password.any { it.isLetter() }
        val containsDigit = password.any { it.isDigit() }

        return when {
            password.isBlank() -> {
                passwordError = "Please fill password field"
                false
            }

            password.length < 6 -> {
                passwordError = "Password must be at least 6 characters long"
                false
            }

            !containsLetter -> {
                passwordError = "Password must contain at least one letter"
                false
            }

            !containsDigit -> {
                passwordError = "Password must contain at least one digit"
                false
            }

            else -> {
                passwordError = ""
                true
            }
        }
    }

    fun isUserLoggedIn() = loginRepository.isUserLoggedIn()

    fun isPersonalizeFilled() = loginRepository.isPersonalizeFilled()

    fun getPersonalized(): Boolean {
        var isFilled = false
        viewModelScope.launch {
            isPersonalizeFilled().collect { value ->
                isFilled = value
            }
        }
        return isFilled
    }

    fun logout() {
        viewModelScope.launch {

            loginRepository.logout()
            resetUserState()
            resetLoginState()
        }
    }

    fun resetLoginState() {
        _loginState.value = ResultResponse.None
    }

    fun resetUserState() {
        _userState.value = ResultResponse.None
    }

    fun setPassword(value: String) {
        passwordValue = value
        validatePassword()
    }

    fun setEmail(value: String) {
        emailValue = value
        validateEmail()
    }

    fun setOtp(value: String) {
        otpValue = value
        validateOtp()
    }

    fun requestOtp() {
        viewModelScope.launch {
            try {
                _reqOtpState.value = ResultResponse.Loading
                loginRepository.generateOtp(emailValue)
                    .collect { result ->
                        _reqOtpState.value = result
                    }
            } catch (e: Exception) {
                _reqOtpState.value = ResultResponse.Error("Failed to generate OTP: ${e.message}")
            }
        }
    }

    private fun validateOtp(): Boolean {
        val otp = otpValue.trim()
        return if (otp.isBlank()) {
            otpError = "Otp is required"
            false
        } else if (otp.length != 6) {
            otpError = "Invalid Otp"
            false
        } else {
            otpError = ""
            true
        }
    }

}