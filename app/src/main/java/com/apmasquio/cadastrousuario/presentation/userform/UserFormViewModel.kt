package com.apmasquio.cadastrousuario.presentation.userform

import androidx.lifecycle.MutableLiveData
import com.apmasquio.cadastrousuario.data.api.LocationApi
import com.apmasquio.cadastrousuario.data.dao.UserDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apmasquio.cadastrousuario.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserFormViewModel @Inject constructor(
    private val locationApi: LocationApi,
    private val userDao: UserDao
) : ViewModel() {

    val ufListFormData = MutableLiveData<MutableList<String>>()
    val cityListFormData = MutableLiveData<MutableList<String>>()
    val finishFormData = MutableLiveData<Boolean>()


    fun getUfs() {
        ufListFormData.value = listOf("").toMutableList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val ufList = mutableListOf<String>()
                val ufApiResponse = locationApi.getUfs()
                for (uf in ufApiResponse) {
                    ufList.add(uf.sigla)
                }
                ufList.sort()
                ufListFormData.postValue(ufList)
            }
        }
    }

    fun getCities(uf: String) {
        cityListFormData.value = listOf(" ").toMutableList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cityList = mutableListOf<String>()
                val cityApiResponse = locationApi.getCities(uf)
                for (city in cityApiResponse) {
                    cityList.add(city.nome)
                }
                cityList.sort()
                cityListFormData.postValue(cityList)
            }
        }
    }

    fun saveOrUpdate(
        user: User,
        update: Boolean
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (update) {
                    userDao.update(user)
                } else {
                    userDao.save(user)
                }
                finishFormData.postValue(true)
            }
        }
    }
}