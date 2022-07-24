package com.linc.di

import com.linc.data.utils.XSLSManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val utilsModule = module {
    single<XSLSManager> { XSLSManager() }
    single<CoroutineDispatcher> { Dispatchers.IO }
}