package com.emm.emm.data.datasource

import kotlinx.coroutines.flow.Flow
import menu.persondb.PersonEntity
import menu.persondb.SelectJustName

interface PersonDataSource {

    suspend fun getPersonById(id: Long): PersonEntity?

    fun getAllPersons(): Flow<List<PersonEntity>>

    suspend fun deletePersonById(id: Long)

    suspend fun insertPerson(
        firstName: String,
        lastName: String,
        id: Long? = null
    )

    fun getAllJustFirstName(): Flow<List<SelectJustName>>

}