package com.emm.betsy.data.datasource.person

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import menu.persondb.PersonEntity
import menu.persondb.SelectJustName

class PersonDataSourceImpl(
    private val db: EmmDatabase
): PersonDataSource {

    private val queries = db.personQueries

    override suspend fun getPersonById(id: Long): PersonEntity? {
        return withContext(Dispatchers.IO) {
            queries.getPersonById(id).executeAsOneOrNull()
        }
    }

    override fun getAllPersons(): Flow<List<PersonEntity>> {
        return queries.getAllPersons().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deletePersonById(id: Long) {
        return withContext(Dispatchers.IO) {
            queries.deletePersonById(id)
        }
    }

    override suspend fun insertPerson(firstName: String, lastName: String, id: Long?) {
        return withContext(Dispatchers.IO) {
            queries.insertPerson(id, firstName, lastName)
        }
    }

    override fun getAllJustFirstName(): Flow<List<SelectJustName>> {
        return queries.selectJustName().asFlow().mapToList(Dispatchers.IO)
    }

}