package com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
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
    FlowGetDataSource<UserDBO> {

    override fun get(query: Query): Flow<UserDBO> = when (query) {
        is UserQuery -> {
            queries.selectUserById(query.identifier).asFlow().mapToOne()
        }
        else -> throw QueryNotSupportedException()
    }

    override fun getAll(query: Query): Flow<List<UserDBO>> = when (query) {
        is UsersQuery -> {
            queries.getAllUsers().asFlow().mapToList()
        }
        else -> throw QueryNotSupportedException()
    }
}