package com.example.sehatin.view.screen.authentication.register.personalize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.PersonalizeResponse
import com.example.sehatin.data.repository.PersonalizeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PersonalizeViewModel(
    private val personalizeRepository: PersonalizeRepository
) : ViewModel() {

    private val _personalizeState =
        MutableStateFlow<ResultResponse<PersonalizeResponse>>(ResultResponse.None)
    val personalizeState: StateFlow<ResultResponse<PersonalizeResponse>> = _personalizeState

    var usernameValue by mutableStateOf("")
        private set

    var genderValue by mutableStateOf("")
        private set

    var ageValue by mutableStateOf("")
        private set

    var heightValue by mutableFloatStateOf(0f)
        private set

    var weightValue by mutableFloatStateOf(0f)
        private set

    var activityLevelValue by mutableStateOf("")
        private set

    var goalValue by mutableStateOf("")
        private set

    fun setPersonalizeState(value: ResultResponse<PersonalizeResponse>) {
        _personalizeState.value = value
    }

    fun setUsername(value: String) {
        usernameValue = value
    }

    fun setGender(value: String) {
        genderValue = value
    }

    fun setAge(value: String) {
        ageValue = value
    }

    fun setHeight(value: Float) {
        heightValue = value
    }

    fun setWeight(value: Float) {
        weightValue = value
    }

    fun setActivityLevel(value: String) {
        activityLevelValue = value
    }

    fun setGoal(value: String) {
        goalValue = value
    }

    fun inputName() {
        if (usernameValue.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeName(usernameValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputAge() {
        if (ageValue.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeAge(ageValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputGender() {
        if (genderValue.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeGender(genderValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputHeight() {
        if (heightValue > 0) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeHeight(heightValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputWeight() {
        if (weightValue > 0) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeWeight(weightValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputActivity() {
        if (activityLevelValue.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeActivityLevel(activityLevelValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

    fun inputGoal() {
        if (goalValue.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _personalizeState.value = ResultResponse.Loading
                    personalizeRepository.personalizeGoal(goalValue)
                        .collect { result ->
                            _personalizeState.value = result
                        }
                } catch (e: Exception) {
                    _personalizeState.value =
                        ResultResponse.Error("Registration failed: ${e.message}")
                }
            }
        } else {
            _personalizeState.value = ResultResponse.Error("Please correct the errors above.")
        }
    }

}