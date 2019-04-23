package dev.carrion.internationalbusinessman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.carrion.internationalbusinessman.ui.BusinessViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO Fragments and navigation

class MainActivity : AppCompatActivity() {

    val viewModel: BusinessViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
