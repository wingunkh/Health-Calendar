package com.example.khg

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.khg.databinding.StartBinding
import java.util.*

class Start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=StartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent=Intent(this,Diary::class.java)
            startActivity(intent) //인텐트를 이용한 액티비티 교체
        }

        val anim=AnimationUtils.loadAnimation(this,R.anim.blink)
        binding.textView2.startAnimation(anim)

    }
}