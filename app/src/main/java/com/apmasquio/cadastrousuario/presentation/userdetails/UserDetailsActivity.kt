package com.apmasquio.cadastrousuario.presentation.userdetails

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.apmasquio.cadastrousuario.R
import com.apmasquio.cadastrousuario.data.models.User
import com.apmasquio.cadastrousuario.presentation.userform.UserFormActivity
import com.apmasquio.cadastrousuario.databinding.ActivityUserDetailsBinding
import com.apmasquio.cadastrousuario.utils.Constants.USER_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity(R.layout.activity_user_details) {
    private val detailsViewModel: UserDetailsViewModel by viewModels()
    private var user: User? = null
    private val binding by lazy {
        ActivityUserDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes do Usuário"
        setContentView(binding.root)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        tryLoadUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_details_remove -> {
                user?.let {
                    detailsViewModel.delete(it)
                }
                finish()
            }

            R.id.menu_detais_edit -> {
                Intent(this, UserFormActivity::class.java).apply {
                    putExtra(USER_KEY, user)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tryLoadUser() {
        detailsViewModel.loadUser()
    }

    private fun fillFields(userCarregado: User) {
        binding.nameDetailUser.setText(userCarregado.name)
        binding.cpfDetailUser.setText(userCarregado.cpf)
        binding.cepDetailUser.setText(userCarregado.cep)
        binding.ufDetailUser.setText(userCarregado.uf)
        binding.cityDetailUser.setText(userCarregado.city)
        binding.neighborhoodDetailUser.setText(userCarregado.neighborhood)
        binding.streetDetailUser.setText(userCarregado.street)
        binding.numberDetailUser.setText(userCarregado.number)
        binding.complementDetailUser.setText(userCarregado.complement)

    }

    private fun observeViewModel() {
        detailsViewModel.userDetailsData.observe(this) {
            intent.getIntExtra(USER_KEY, -1).let { position ->
                user = it[position]
                user?.let { fillFields(it) }
            }
        }
    }
}