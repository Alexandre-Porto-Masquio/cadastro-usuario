package com.apmasquio.cadastrousuario.presentation.adapter

import com.apmasquio.cadastrousuario.presentation.userdetails.UserDetailsActivity
import com.apmasquio.cadastrousuario.utils.Constants.USER_KEY
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apmasquio.cadastrousuario.data.models.User
import com.apmasquio.cadastrousuario.databinding.UserItemBinding

class UserListAdapter(
    deliveries : List<User> = emptyList()
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val deliveries = deliveries.toMutableList()

    class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, position: Int) {
            //Click leads to detail screen
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, UserDetailsActivity::class.java)
                intent.putExtra(USER_KEY, position)
                binding.root.context.startActivity(intent)
            }

            val nome = binding.tvNameUserItem
            val textName = "Nome: " + user.name
            nome.text = textName

            val cpf = binding.tvCpfUserItem
            val textCpf = "CPF: " + user.cpf
            cpf.text = textCpf

            val cep = binding.tvCepUserItem
            val textCep = "CEP: " + user.cep
            cep.text = textCep

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = deliveries[position]
        holder.bind(user, position)
    }

    override fun getItemCount(): Int = deliveries.size

    fun update(deliveries: List<User>) {
        this.deliveries.clear()
        this.deliveries.addAll(deliveries)
    }
    fun notifyData() {
        notifyDataSetChanged()
    }

}
