package com.emm.emm.di

import com.github.javafaker.Faker
import org.koin.dsl.module

val commonModule = module {
    single { Faker.instance() }
}