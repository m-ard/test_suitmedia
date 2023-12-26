package com.test.suitmedia.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.suitmedia.network.ApiConfig
import com.test.suitmedia.response.DataItem
import com.test.suitmedia.response.RegresResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivityViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<DataItem>>()
    val listUser: LiveData<List<DataItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 1
    private val itemsPerPage = 10
    private var totalPages = Int.MAX_VALUE

    companion object {
        private const val TAG = "ThirdActivityViewModel"
    }

    init {
        findUser()
    }

    fun findUser() {
        if (currentPage <= totalPages) {
            _isLoading.value = true

            ApiConfig.getApiService().getUsers(1, 10)
                .enqueue(object : Callback<RegresResponse> {
                    override fun onResponse(
                        call: Call<RegresResponse>,
                        response: Response<RegresResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            val newData = response.body()?.data
                            if (newData != null) {
                                val currentList = _listUser.value.orEmpty().toMutableList()
                                currentList.addAll(newData)
                                _listUser.postValue(currentList)
                                currentPage++
                                totalPages = response.body()?.totalPages ?: totalPages
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<RegresResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "onFailure: ${t.message}")
                    }
                })
        }
    }
}
