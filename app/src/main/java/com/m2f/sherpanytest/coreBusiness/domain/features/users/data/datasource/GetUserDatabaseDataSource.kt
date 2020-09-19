package com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UsersQuery
import com.m2f.sherpanytest.sqldelight.data.UserDBO
import com.m2f.sherpanytest.sqldelight.data.UserDBOQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserDatabaseDataSource @Inject constructor(private val queries: UserDBOQueries) :
    GetDataSource<UserDBO> {

    override suspend fun get(query: Query): UserDBO = when (query) {
        is UserQuery -> {
            queries.selectUserById(query.identifier).executeAsOne()
        }
        else -> throw QueryNotSupportedException()
    }

    override suspend fun getAll(query: Query): List<UserDBO> = when (query) {
        is UsersQuery -> {
            queries.getAllUsers().executeAsList()
        }
        else -> throw QueryNotSupportedException()
    }
}