package com.flexicode.testmecate.View.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexicode.testmecate.DataBase.Repository.MecateRepository
import com.flexicode.testmecate.Entity.CountryEntity
import com.flexicode.testmecate.Entity.UserEntity
import com.flexicode.testmecate.Models.Country
import com.flexicode.testmecate.Models.UserData
import com.flexicode.testmecate.Models.UserState
import com.flexicode.testmecate.Network.DAO.NetworkDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: MecateRepository,
    private val networkDAO: NetworkDAO
) : ViewModel() {


    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()


    fun searchtLastName(name: String){

        if (name.isNotEmpty()){
            getListContrys(name = name)
        }else{

            _state.update {
                it.copy(
                    action = "search",
                    showAlert = true,
                    titleAlert = "Dato faltante",
                    descriptionAlert = "Por favor ingresa un nombre"
                )
            }
        }

    }

    private fun getListContrys(name: String){
        viewModelScope.launch {
            networkDAO.searchtUser(name = name).enqueue(object :
                Callback<UserData> {

                override fun onResponse(
                    call: Call<UserData>,
                    response: Response<UserData>
                ) {

                    if (response.code().equals(200)) {

                        Log.i("Response: ", response.body().toString())

                        val response = response.body() as UserData
                        _state.update {
                            it.copy(
                                action = "search",
                                showAlert = false,
                                response = response
                            )
                        }

                        val userEntity = UserEntity(
                            name = response.name ?: "",
                            count = response.count ?: 0
                        )

                        insertUser(user = userEntity, response.country)



                    } else {

                        Log.i("Response: ", "Fallo la peticion "+  response.errorBody())

                        _state.update {
                            it.copy(
                                action = "search",
                                showAlert = true,
                                titleAlert = "Ocurrio un error",
                                descriptionAlert = "Por favor, intentalo de nuevo"
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {

                    Log.i("Response: ", "Fallo la peticion")
                    _state.update {
                        it.copy(
                            showAlert = true,
                            titleAlert = "Ocurrio un error",
                            descriptionAlert = "Al parecer la respuesta no es correcta"
                        )
                    }
                }
            })
        }
    }

   private fun insertUser(user: UserEntity, country: ArrayList<Country>) {
        viewModelScope.launch {
            val userId = userRepository.insertUser(user).toInt()

            insertCountry(userId = userId, countryList = country)
        }
    }

    private fun insertCountry(userId: Int,countryList: ArrayList<Country>) {

        val countryArray : ArrayList<CountryEntity> = arrayListOf()
        countryList.forEach { country ->
            val contryEntity = CountryEntity(
                userId = userId,
                country_id = country.countryId,
                probability = country.probability
            )
            countryArray.add(contryEntity)
        }

        viewModelScope.launch {
            userRepository.insertCountries(countryArray)
        }
    }


    fun showHistory(){
        _state.update {
            it.copy(
                action = "history"
            )
        }
        getHistory()
    }

    fun getHistory(){

        viewModelScope.launch {

            _state.update {
                it.copy(
                    history =  userRepository.getAllUsers()
                )
            }

        }

    }

    fun getHistoryDB(user: UserEntity){

        val countryArray : ArrayList<Country> = arrayListOf()

        viewModelScope.launch {
          val countryList =  userRepository.getCountriesByUserId(user.id)

            countryList.forEach { country ->
                val contryEntity = Country(
                    countryId =country.country_id,
                    probability = country.probability
                )
                countryArray.add(contryEntity)
            }

            _state.update {
                it.copy(
                    action = "search",
                    showAlert = false,
                    response = UserData(count = countryArray.size, name = user.name, country = countryArray)
                )
            }

        }


    }


}