package com.test.suitmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.suitmedia.adapter.ListUserAdapter
import com.test.suitmedia.databinding.ActivityThirdBinding
import com.test.suitmedia.model.ThirdActivityViewModel
import com.test.suitmedia.response.DataItem

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var thirdActivityViewModel: ThirdActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getName = intent.getStringExtra(SecondActivity.EXTRA_NAME).toString()
        thirdActivityViewModel = ViewModelProvider(this)[ThirdActivityViewModel::class.java]

        binding.btnBack.setOnClickListener {
            val i = Intent(this@ThirdActivity, SecondActivity::class.java)
            i.putExtra(SecondActivity.EXTRA_NAME, getName)
            startActivity(i)
            finish()
        }

        thirdActivityViewModel.listUser.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        thirdActivityViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserData(listUser: List<DataItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(listUser)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_NAME = "NAME"
    }
}
