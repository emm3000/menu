package com.emm.betsy

import java.util.UUID

fun Int.randomUUIDWithId() = "$this -> ${UUID.randomUUID()}"