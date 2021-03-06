package com.example.mitmit.interests_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mitmit.DashboardActivity
import com.example.mitmit.R
import com.example.mitmit.daos.UserDao
import com.example.mitmit.models.Loisir
import com.example.mitmit.nearby_users_recyclerview.UsersActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_loisirs.*

//modification
class LoisirsActivity : AppCompatActivity(), OnLoisirClicked{

    private val loisirs = listOf<Loisir>(
        Loisir("Cuisine", R.drawable.cuisine),
        Loisir("Amitié", R.drawable.amitie),
        Loisir("Informatique", R.drawable.informatique),
        Loisir("Sport", R.drawable.sport),
        Loisir("Jeux Video", R.drawable.jeux),
        Loisir("Voyage", R.drawable.voyage),
        Loisir("Musique", R.drawable.musique)
    )

    private val loisirsOfUser = mutableListOf<String?>()

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loisirs)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        loisirs_recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@LoisirsActivity)
            adapter = LoisirsAdapter(loisirs, this@LoisirsActivity)
        }

        btnValidate?.setOnClickListener {
            val userDao = UserDao()
            userDao.editLoisirs(currentUser, loisirsOfUser)

            val flag = intent.getStringExtra("flag")
            if (flag.equals("signinActivity")) {
                val usersIntent = Intent(this, UsersActivity::class.java)
                startActivity(usersIntent)
                finish()
            } else{
                finish()
            }
        }

    }

    override fun onLoisirsClicked(loisir: Loisir?) {
        loisirsOfUser.add(loisir?.title)
    }

}