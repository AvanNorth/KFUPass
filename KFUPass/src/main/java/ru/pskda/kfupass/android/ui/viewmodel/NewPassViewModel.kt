package ru.pskda.kfupass.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.pskda.kfupass.android.data.model.TokenListResponse
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList
import ru.pskda.kfupass.android.domain.usecase.TokenUseCase
import javax.inject.Inject

@HiltViewModel
class NewPassViewModel @Inject constructor(
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private var _savedToken: MutableLiveData<Result<Long>> = MutableLiveData()
    val savedToken: LiveData<Result<Long>> = _savedToken

    fun saveToken(token: Token) = viewModelScope.launch {
        try {
            _savedToken.value = Result.success(tokenUseCase.saveToken(token))
        } catch (e: Exception) {
            _savedToken.value = Result.failure(e)
        }
    }
}