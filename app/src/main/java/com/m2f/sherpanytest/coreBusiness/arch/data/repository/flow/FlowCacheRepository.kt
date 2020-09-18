package com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.ObjectNotValidException
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.*
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat

class FlowCacheRepository<V>(
    private val getCache: FlowGetDataSource<V>,
    private val putCache: FlowPutDataSource<V>,
    private val deleteCache: FlowDeleteDataSource,
    private val getMain: FlowGetDataSource<V>,
    private val putMain: FlowPutDataSource<V>,
    private val deleteMain: FlowDeleteDataSource
) : FlowGetRepository<V>, FlowPutRepository<V>, FlowDeleteRepository {

    override fun get(query: Query, operation: Operation): Flow<V> {
        return when (operation) {
            is DefaultOperation -> get(query, CacheSyncOperation)
            is MainOperation -> getMain.get(query)
            is CacheOperation -> getCache.get(query)
            is MainSyncOperation -> getMain.get(query).flatMapConcat { putCache.put(query, it) }
            is CacheSyncOperation -> {
                return getCache.get(query).catch {
                    when (it) {
                        is ObjectNotValidException -> get(query, MainSyncOperation)
                        is DataNotFoundException -> get(query, MainSyncOperation)
                        else -> throw it
                    }
                }
            }
        }
    }

    override fun getAll(query: Query, operation: Operation): Flow<List<V>> {
        return when (operation) {
            is DefaultOperation -> getAll(query, CacheSyncOperation)
            is MainOperation -> getMain.getAll(query)
            is CacheOperation -> getCache.getAll(query)
            is MainSyncOperation -> getMain.getAll(query)
                .flatMapConcat { putCache.putAll(query, it) }
            is CacheSyncOperation -> {
                return getCache.getAll(query).catch {
                    when (it) {
                        is ObjectNotValidException -> getAll(query, MainSyncOperation)
                        is DataNotFoundException -> getAll(query, MainSyncOperation)
                        else -> throw it
                    }
                }
            }
        }
    }

    override fun put(query: Query, value: V?, operation: Operation): Flow<V> = when (operation) {
        is DefaultOperation -> put(query, value, MainSyncOperation)
        is MainOperation -> putMain.put(query, value)
        is CacheOperation -> putCache.put(query, value)
        is MainSyncOperation -> putMain.put(query, value).flatMapConcat { putCache.put(query, it) }
        is CacheSyncOperation -> putCache.put(query, value).flatMapConcat { putMain.put(query, it) }
    }

    override fun putAll(query: Query, value: List<V>?, operation: Operation): Flow<List<V>> =
        when (operation) {
            is DefaultOperation -> putAll(query, value, MainSyncOperation)
            is MainOperation -> putMain.putAll(query, value)
            is CacheOperation -> putCache.putAll(query, value)
            is MainSyncOperation -> putMain.putAll(query, value)
                .flatMapConcat { putCache.putAll(query, it) }
            is CacheSyncOperation -> putCache.putAll(query, value)
                .flatMapConcat { putMain.putAll(query, it) }
        }

    override fun delete(query: Query, operation: Operation): Flow<Unit> = when (operation) {
        is DefaultOperation -> delete(query, MainSyncOperation)
        is MainOperation -> deleteMain.delete(query)
        is CacheOperation -> deleteCache.delete(query)
        is MainSyncOperation -> deleteMain.delete(query).flatMapConcat { deleteCache.delete(query) }
        is CacheSyncOperation -> deleteCache.delete(query)
            .flatMapConcat { deleteMain.delete(query) }
    }

    override fun deleteAll(query: Query, operation: Operation): Flow<Unit> = when (operation) {
        is DefaultOperation -> deleteAll(query, MainSyncOperation)
        is MainOperation -> deleteMain.deleteAll(query)
        is CacheOperation -> deleteCache.deleteAll(query)
        is MainSyncOperation -> deleteMain.deleteAll(query)
            .flatMapConcat { deleteCache.deleteAll(query) }
        is CacheSyncOperation -> deleteCache.deleteAll(query)
            .flatMapConcat { deleteMain.deleteAll(query) }
    }
}