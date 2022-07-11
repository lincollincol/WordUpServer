package com.linc.di

import com.linc.data.utils.XSLSManager
import org.koin.dsl.module

val utilsModule = module {
    single<XSLSManager> { XSLSManager() }
}