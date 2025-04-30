package com.example.sehatin.viewmodel

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.ConsultationResponse
import com.example.sehatin.data.model.response.ConsultationResponse.GetMessageResponse
import com.example.sehatin.data.model.response.ConsultationResponse.SendMessageResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.DietResponse.FoodFilterResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleClosestResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.repository.ConsultationRepository
import com.example.sehatin.data.repository.DietRepository
import com.example.sehatin.utils.getTodayUtcDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class ConsultationViewModel(
    private val consultationRepository: ConsultationRepository
) : ViewModel() {


    private val _message = MutableStateFlow(TextFieldValue(""))
    val message = _message.asStateFlow()

    fun setMessage(name: TextFieldValue) {
        _message.value = name
    }

    fun resetMessage() {
        _message.value = TextFieldValue("")
    }
    private val _sendMessageState =
        MutableStateFlow<ResultResponse<SendMessageResponse>>(ResultResponse.None)
    val sendMessageState: StateFlow<ResultResponse<SendMessageResponse>> =
        _sendMessageState.asStateFlow()

    private val _userMessagesState =
        MutableStateFlow<ResultResponse<GetMessageResponse>>(ResultResponse.None)
    val userMessagesState: StateFlow<ResultResponse<GetMessageResponse>> =
        _userMessagesState

    private val _userMessage = MutableStateFlow<GetMessageResponse?>(null)
    val userMessage = _userMessage.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var lastUserMessagesFetchTime = 0L
    private val UserMessagesCacheValidityPeriod = 5 * 60 * 1000L

    init {

        getUserMessages(forceRefresh=false)


    }

    fun sendMessage() {
        if (message.value.text.isBlank()) {
            Log.e("ConsultationViewModel", "Cannot send an empty message")
            return
        }

        viewModelScope.launch {
            try {
                _sendMessageState.value = ResultResponse.Loading
                consultationRepository.sendMessage(message.value.text)
                    .collect { result ->
                        _sendMessageState.value = result
                    }
            } catch (e: Exception) {
                Log.e("ConsultationViewModel", "Error sending message: ${e.message}")
            }
        }
    }

    fun getUserMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()

                val shouldRefresh = _userMessage.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastUserMessagesFetchTime > UserMessagesCacheValidityPeriod)
                if (shouldRefresh) {
                    _userMessagesState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    consultationRepository.getMessages()
                        .collect { result ->
                            _userMessagesState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _userMessage.value = result.data
                                    lastUserMessagesFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }



            } catch (e: Exception) {
                _userMessagesState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }


    fun refresh() {
        getUserMessages(forceRefresh = true)


    }


}