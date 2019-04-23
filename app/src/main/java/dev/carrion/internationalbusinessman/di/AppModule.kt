package dev.carrion.internationalbusinessman.di

import androidx.room.Room
import dev.carrion.data.Repository
import dev.carrion.data.local.BusinessManDatabase
import dev.carrion.data.local.LocalDataSource
import dev.carrion.data.network.BusinessManApi
import dev.carrion.data.network.NetworkDataSource
import dev.carrion.internationalbusinessman.ui.BusinessViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    single { BusinessManApi.create() }

    single { Room.databaseBuilder(androidContext(), BusinessManDatabase::class.java, "businessman").fallbackToDestructiveMigration().build()}

    single { get<BusinessManDatabase>().rateDao() }

    single { get<BusinessManDatabase>().transactionDao() }

    single { LocalDataSource(get(),get())}

    single { NetworkDataSource(get()) }

    single { Repository(get(), get())}

    viewModel { BusinessViewModel(get()) }

}