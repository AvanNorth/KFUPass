package ru.pskda.kfupass.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pskda.kfupass.android.domain.entity.TokenList
import ru.pskda.kfupass.android.domain.usecase.TokenUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private var _validTokens: MutableLiveData<Result<TokenList>> = MutableLiveData()
    val validTokens: LiveData<Result<TokenList>> = _validTokens

    private var _localTokens: MutableLiveData<Result<TokenList>> = MutableLiveData()
    val localTokens: LiveData<Result<TokenList>> = _localTokens

    fun checkTokens(tokens: TokenList) = viewModelScope.launch {
        try {
            _validTokens.value = Result.success(tokenUseCase.checkTokens(tokens))
        } catch (e: Exception) {
            _validTokens.value = Result.failure(e)
        }
    }

    fun getLocalTokens() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
            }
            _localTokens.value = Result.success(tokenUseCase.getTokens())
        } catch (e: Exception) {
            _validTokens.value = Result.failure(e)
        }
    }
}