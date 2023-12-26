package com.test.suitmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.test.suitmedia.R
import com.test.suitmedia.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {

            binding.btnCheck.setOnClickListener {
                val palindromeInput = binding.palindromeInputText.text.toString()

                if (palindromeInput.isNotEmpty()) {
                    val isPalindrome = checkPalindrome(palindromeInput)

                    val message = if (isPalindrome) {
                        getString(R.string.is_palindrome)
                    } else {
                        getString(R.string.not_palindrome)
                    }

                    showResultDialog(message)
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Input palindrome is empty.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }

        }

        binding.btnNext.setOnClickListener {
            val nameInput = binding.nameInputText.text.toString()

            if (nameInput.isNotEmpty()) {
                val i = Intent(this@FirstActivity, SecondActivity::class.java)
                i.putExtra(EXTRA_NAME, nameInput)
                startActivity(i)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Input name cannot be empty! ")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }


    private fun checkPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s+".toRegex(), "").lowercase()

        return cleanText == cleanText.reversed()
    }

    private fun showResultDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }

    companion object{
        const val EXTRA_NAME = "NAME"
    }
}
