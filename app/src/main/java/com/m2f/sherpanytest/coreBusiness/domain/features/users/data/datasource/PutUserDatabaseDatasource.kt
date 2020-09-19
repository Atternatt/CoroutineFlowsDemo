package com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UsersQuery
import com.m2f.sherpanytest.sqldelight.data.UserDBO
import com.m2f.sherpanytest.sqldelight.data.UserDBOQueries
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class PutUserDatabaseDatasource @Inject constructor(private val queries: UserDBOQueries) :
    FlowPutDataSource<UserDBO> {

    override fun put(query: Query, value: UserDBO?): Flow<UserDBO> = flow {
        when {
            query is UserQuery && value != null -> {
                queries.insertOrReplaceUser(value)
                emit(value)
            }
            else -> throw QueryNotSupportedException()
        }
    }

    override fun putAll(query: Query, value: List<UserDBO>?): Flow<List<UserDBO>> {
        if (query is UsersQuery) {
            if (!value.isNullOrEmpty()) {
                return value.asFlow()
                    .flatMapConcat { put(UserQuery(it.userId), it) }
                    .scan(listOf()) { list, item -> list + item } //we could return value but scanning the result we ensure that we get only the added items.


            } else {
                throw DataNotFoundException("trying to store an empty list")
            }
        } else {
            throw QueryNotSupportedException()
        }
    }


}