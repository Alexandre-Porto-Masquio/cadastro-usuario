package com.apmasquio.cadastrousuario.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    //User
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val cpf: String,
    //Address
    val cep: String,
    val uf: String,
    val city: String,
    val neighborhood: String,
    val street: String,
    val number: String,
    val complement: String
    ) : Parcelable