package com.apmasquio.cadastrousuario.presentation.userlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.apmasquio.cadastrousuario.R
import com.apmasquio.cadastrousuario.presentation.userform.UserFormActivity
import com.apmasquio.cadastrousuario.presentation.adapter.UserListAdapter
import com.apmasquio.cadastrousuario.databinding.ActivityUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity(R.layout.activity_user_list) {

    private val listViewModel: UserListViewModel by viewModels()
    private val adapter = UserListAdapter()

    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Lista de Usuários"
        binding = ActivityUserListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        observeViewModel()
        configureRecyclerView()
        setupIntentFab()
    }

    override fun onResume() {
        super.onResume()
            listViewModel.getAll()
    }

    private fun setupIntentFab() {
        val fabAddUser = binding.fabAddUser
        fabAddUser.setOnClickListener {
            val intent = Intent(this, UserFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configureRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        listViewModel.listData.observe(this) {
            adapter.update(it)
            adapter.notifyData()
        }
    }
}