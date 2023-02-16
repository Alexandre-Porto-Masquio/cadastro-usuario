package com.apmasquio.cadastrousuario.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.apmasquio.cadastrousuario.presentation.userlist.UserListActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, UserListActivity::class.java))
        finishAfterTransition()
    }
}