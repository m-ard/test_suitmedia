package com.test.suitmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.suitmedia.R
import com.test.suitmedia.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var getName: String = ""
    private var getUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            getName = savedInstanceState.getString(EXTRA_NAME, "")
            getUserName = savedInstanceState.getString(EXTRA_USERNAME, "")
        } else {
            getName = intent.getStringExtra(EXTRA_NAME).toString()
            getUserName = intent.getStringExtra(EXTRA_USERNAME) ?: getString(R.string.select_user_name)
        }

        binding.tvName.text = getName
        binding.selectedUs.text = getUserName
        binding.btnChooseUser.setOnClickListener {
            val i = Intent(this@SecondActivity, ThirdActivity::class.java)
            i.putExtra(EXTRA_NAME, getName)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener {
            val i = Intent(this@SecondActivity, FirstActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NAME, getName)
        outState.putString(EXTRA_USERNAME, getUserName)
    }

    companion object {
        const val EXTRA_NAME = "NAME"
        const val EXTRA_USERNAME = "USERNAME"
    }
}
