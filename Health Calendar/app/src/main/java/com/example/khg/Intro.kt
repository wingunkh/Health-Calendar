package com.example.khg

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.khg.databinding.IntroBinding

class Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=IntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SystemClock.sleep(1000) //1초 대기
        val intent=Intent(this,Start::class.java)
        startActivity(intent) //인텐트를 이용한 액티비티 교체
    }
}