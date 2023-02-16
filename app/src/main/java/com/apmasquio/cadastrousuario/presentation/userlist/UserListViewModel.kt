package com.apmasquio.cadastrousuario.presentation.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apmasquio.cadastrousuario.data.dao.UserDao
import com.apmasquio.cadastrousuario.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    val listData = MutableLiveData<List<User>>()

    fun getAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listData.postValue(userDao.getAll())
            }
        }
    }
}