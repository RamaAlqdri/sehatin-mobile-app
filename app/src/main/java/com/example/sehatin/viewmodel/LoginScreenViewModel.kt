package com.example.sehatin.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.LoginResponse
import com.example.sehatin.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<ResultResponse<LoginResponse>>(ResultResponse.None)
    val loginState: StateFlow<ResultResponse<LoginResponse>> = _loginState

    private val _userState =
        MutableStateFlow<ResultResponse<GetUserResponse>>(ResultResponse.None)
    val userState: StateFlow<ResultResponse<GetUserResponse>> = _userState


    var passwordValue by mutableStateOf("")
        private set

    var passwordError by mutableStateOf("")
        private set

    var emailValue by mutableStateOf("")
        private set
    var emailError by mutableStateOf("")
        private set


    fun loginUser() {
        if (validateEmail() && validatePassword()) {
            viewModelScope.launch {
                try {
                    _loginState.value = ResultResponse.Loading
                    loginRepository.loginUser(emailValue, passwordValue)
                        .collect { result ->
                            _loginState.value = result
                        }
//                    delay(2000)
//                    _loginState.value =
//                        ResultResponse.Success(LoginResponse(
//                            data = Data(
//                                "wakoak"
//                            ),
//                            message = "Success",
//                            statusCode = 200,
//                            timestamp = "owkeake"
//                        ))
                } catch (e: Exception) {
                    _loginState.value = ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _loginState.value = ResultResponse.Error("Please correct the errors above.")
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

    suspend fun logout() {
        loginRepository.logout()
    }

    fun setPassword(value: String) {
        passwordValue = value
        validatePassword()
    }

    fun setEmail(value: String) {
        emailValue = value
        validateEmail()
    }

}