package com.apmasquio.cadastrousuario.presentation.userdetails

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
class UserDetailsViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    val userDetailsData = MutableLiveData<List<User>>()

    fun delete(item: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDao.delete(item)
            }
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDetailsData.postValue(userDao.getAll())
            }
        }
    }
}