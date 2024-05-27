package com.example.auth.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseModel: ViewModel() {

    protected val _error = MutableStateFlow("")
    // read only value
    val error = _error.asStateFlow()


    protected val _loading = MutableStateFlow(false)
    // read only value
    val loading = _loading.asStateFlow()

}