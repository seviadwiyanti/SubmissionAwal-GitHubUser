package com.dicoding.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainViewModel : ViewModel() {

    companion object{
        private const val TAG = "MainActivity"
        private const val type = "login"
    }

    private val _items = MutableLiveData<ItemsItem>()
    val items: LiveData<ItemsItem> = _items

    private val _itemListUser = MutableLiveData<List<ItemsItem>>()
    val itemListUser: LiveData<List<ItemsItem>> = _itemListUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init  {
        findItems()
    }

    fun findItems() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getItemsItem(type)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()?.items
                    if (items != null && items.isNotEmpty()) {
                        _items.value = items[0]
                        _itemListUser.value = items as List<ItemsItem>?
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}