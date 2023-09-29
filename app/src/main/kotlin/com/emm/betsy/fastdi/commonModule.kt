package com.emm.betsy.fastdi

import com.github.javafaker.Faker
import org.koin.dsl.module

val commonModule = module {
    single { Faker.instance() }
}