package com.test.task.nasa.app.presentation

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.test.task.nasa.app.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuItem
import com.test.task.nasa.app.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    private fun observeData() {
        viewModel.isLoading.observe(this, {
            val visibility = if (it) View.VISIBLE else View.GONE
            binding.progressBar.visibility = visibility
        })

        viewModel.apodData.observe(this, {
            binding.view.visibility = View.VISIBLE
            Glide
                .with(this)
                .load(it.url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivPhoto)
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.explanation
            binding.tvDate.text = getString(R.string.home_date_label).plus(it.date)
        })

        viewModel.onMessageError.observe(this, {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_date -> openDatePickerDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            viewModel.loadApod("$year-$monthOfYear-$dayOfMonth")
        }, year, month, day)
        dpd.show()
    }
}