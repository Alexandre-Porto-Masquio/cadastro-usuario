package com.apmasquio.cadastrousuario.presentation.userform

import com.apmasquio.cadastrousuario.R
import com.apmasquio.cadastrousuario.databinding.ActivityUserFormBinding
import com.apmasquio.cadastrousuario.utils.Constants.USER_KEY
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.apmasquio.cadastrousuario.data.models.User
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFormActivity :
    AppCompatActivity(R.layout.activity_user_form) {
    private val formViewModel: UserFormViewModel by viewModels()
    private lateinit var user: User
    private lateinit var formBinding: ActivityUserFormBinding
    private lateinit var spinnerUf: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var ufAdapter: ArrayAdapter<String>
    private lateinit var cityAdapter: ArrayAdapter<String>
    private var ufSelectedSpinnerItem: String = ""
    private var citySelectedSpinnerItem: String = ""
    private var userId = 0L
    private val thisContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Cadastrar Usuário"
        formBinding = ActivityUserFormBinding.inflate(layoutInflater)
        val formView = formBinding.root
        setContentView(formView)

        observeViewModel()
        spinnerUf = formBinding.spinnerUfUserForm
        spinnerCity = formBinding.spinnerCityUserForm

        configureSaveButton()
        ufConfigureSpinnerListener()
        cityConfigureSpinnerListener()
        formViewModel.getUfs()

        intent.getParcelableExtra<User>(USER_KEY)?.let { loadedUser ->
            user = loadedUser
            userId = loadedUser.id
            fillForm(loadedUser)
        }

    }

    private fun observeViewModel() {
        formViewModel.ufListFormData.observe(this) {
            ufPopulateSpinner()
        }
        formViewModel.cityListFormData.observe(this) {
            cityPopulateSpinner()
        }
        formViewModel.finishFormData.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    private fun ufPopulateSpinner() {
        ufAdapter = formViewModel.ufListFormData.value?.let {
            ArrayAdapter(
                thisContext,
                android.R.layout.simple_spinner_item,
                it
            )
        }!!
        ufAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        configSpinnerAdapter(spinnerUf, ufAdapter)
    }

    private fun cityPopulateSpinner() {
        cityAdapter = formViewModel.cityListFormData.value?.let {
            ArrayAdapter(
                thisContext,
                android.R.layout.simple_spinner_item,
                it
            )
        }!!
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        configSpinnerAdapter(spinnerCity, cityAdapter)
    }

    private fun configSpinnerAdapter(spinner: Spinner, adapter: ArrayAdapter<String>) {
        spinner.adapter = adapter
    }

    private fun configureSaveButton() {
        val saveButton = formBinding.btSaveUserForm
        saveButton.setOnClickListener {

            if (validateFields(validateList())) {
                val update = userId > 0 && ::user.isInitialized
                formViewModel.saveOrUpdate(createUser(), update)
            } else {
                Toast.makeText(
                    this,
                    "Por favor, preencha os campos necessários.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun ufConfigureSpinnerListener() {
        spinnerUf.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ufSelectedSpinnerItem = parent?.getItemAtPosition(position).toString()
                formViewModel.getCities(ufSelectedSpinnerItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                ufSelectedSpinnerItem = ""
            }
        }
    }

    private fun cityConfigureSpinnerListener() {
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                citySelectedSpinnerItem = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                citySelectedSpinnerItem = ""
            }
        }
    }

    private fun validateList() : List<TextInputEditText> {
        with (formBinding) {
            return listOf(
                textNameUserForm,
                textCepUserForm,
                textCpfUserForm,
                textNeighborhoodUserForm,
                textStreetUserForm,
                textNumberUserForm
            )
        }
    }

    private fun validateFields(validateList : List<TextInputEditText>): Boolean {
        var bool = true

        for (inputText in validateList) {
            inputText.text?.let {
                if (it.isEmpty()) {
                    inputText.error = "este campo é obrigatório"
                    bool = false
                }
            }
        }

        if (ufSelectedSpinnerItem.isEmpty() || citySelectedSpinnerItem.isEmpty()) {
            bool = false
        }

        return bool
    }

    private fun createUser(): User {
        val fieldName = formBinding.textNameUserForm
        val name = fieldName.text.toString()

        val fieldCpf = formBinding.textCpfUserForm
        val cpf = fieldCpf.text.toString()

        val fieldCep = formBinding.textCepUserForm
        val cep = fieldCep.text.toString()

        val uf = ufSelectedSpinnerItem

        val city = citySelectedSpinnerItem

        val fieldNeighborhood = formBinding.textNeighborhoodUserForm
        val neighborhood = fieldNeighborhood.text.toString()

        val fieldStreet = formBinding.textStreetUserForm
        val street = fieldStreet.text.toString()

        val fieldNumber = formBinding.textNumberUserForm
        val number = fieldNumber.text.toString()

        val fieldComplement = formBinding.textComplementUserForm
        val complement = fieldComplement.text.toString()

        return User(
            id = userId,
            name = name,
            cpf = cpf,
            cep = cep,
            uf = uf,
            city = city,
            neighborhood = neighborhood,
            street = street,
            number = number,
            complement = complement
        )
    }

    private fun fillForm(loadedUser: User) {
        formBinding.textNameUserForm.setText(loadedUser.name)
        formBinding.textCpfUserForm.setText(loadedUser.cpf)
        formBinding.textCepUserForm.setText(loadedUser.cep)
        formViewModel.ufListFormData.value?.let { spinnerUf.setSelection(it.indexOf(loadedUser.uf)) }
        formViewModel.ufListFormData.value?.let { spinnerUf.setSelection(it.indexOf(loadedUser.city)) }
        formBinding.textNeighborhoodUserForm.setText(loadedUser.neighborhood)
        formBinding.textStreetUserForm.setText(loadedUser.street)
        formBinding.textNumberUserForm.setText(loadedUser.number)
        formBinding.textComplementUserForm.setText(loadedUser.complement)
    }


}
