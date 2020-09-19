package com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.PutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UsersQuery
import com.m2f.sherpanytest.sqldelight.data.UserDBO
import com.m2f.sherpanytest.sqldelight.data.UserDBOQueries
import javax.inject.Inject


class PutUserDatabaseDatasource @Inject constructor(private val queries: UserDBOQueries) :
    PutDataSource<UserDBO> {

    override suspend fun put(query: Query, value: UserDBO?): UserDBO {
        return when {
            query is UserQuery && value != null -> {
                queries.insertOrReplaceUser(value)
                value
            }
            else -> throw QueryNotSupportedException()
        }
    }

    override suspend fun putAll(query: Query, value: List<UserDBO>?): List<UserDBO> {
        return if (query is UsersQuery) {
            if (!value.isNullOrEmpty()) {
                value.map { put(UserQuery(it.userId), it) }
            } else {
                throw DataNotFoundException("trying to store an empty list")
            }
        } else {
            throw QueryNotSupportedException()
        }
    }


}