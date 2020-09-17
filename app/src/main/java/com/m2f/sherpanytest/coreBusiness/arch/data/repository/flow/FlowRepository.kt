package com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.DefaultOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.Operation
import com.m2f.sherpanytest.coreBusiness.arch.data.query.IdQuery
import com.m2f.sherpanytest.coreBusiness.arch.data.query.IdsQuery
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import kotlinx.coroutines.flow.Flow

interface FlowRepository {

  fun notSupportedQuery(): Nothing = throw UnsupportedOperationException("Query not supported")

  fun notSupportedOperation(): Nothing = throw UnsupportedOperationException("Operation not defined")
}

// Repositories
interface FlowGetRepository<V> : FlowRepository {
  fun get(query: Query, operation: Operation = DefaultOperation): Flow<V>
  fun getAll(query: Query, operation: Operation = DefaultOperation): Flow<List<V>>
}

interface FlowPutRepository<V> : FlowRepository {
  fun put(query: Query, value: V?, operation: Operation = DefaultOperation): Flow<V>
  fun putAll(query: Query, value: List<V>? = emptyList(), operation: Operation = DefaultOperation): Flow<List<V>>
}

interface FlowDeleteRepository : FlowRepository {
  fun delete(query: Query, operation: Operation = DefaultOperation): Flow<Unit>
  fun deleteAll(query: Query, operation: Operation = DefaultOperation): Flow<Unit>
}

// Extensions

fun <K, V> FlowGetRepository<V>.get(id: K, operation: Operation = DefaultOperation) = get(IdQuery(id), operation)

fun <K, V> FlowGetRepository<V>.getAll(ids: List<K>, operation: Operation = DefaultOperation) = getAll(
    IdsQuery(ids), operation)

fun <K, V> FlowPutRepository<V>.put(id: K, value: V?, operation: Operation = DefaultOperation) = put(
    IdQuery(id), value, operation)

fun <K, V> FlowPutRepository<V>.putAll(ids: List<K>, values: List<V>? = emptyList(), operation: Operation = DefaultOperation) = putAll(
    IdsQuery(ids),
    values,
    operation)

fun <K> FlowDeleteRepository.delete(id: K, operation: Operation = DefaultOperation) = delete(IdQuery(id), operation)

fun <K> FlowDeleteRepository.deleteAll(ids: List<K>, operation: Operation = DefaultOperation) = deleteAll(
    IdsQuery(ids), operation)

fun <K, V> FlowGetRepository<K>.withMapping(mapper: Mapper<K, V>): FlowGetRepository<V> = FlowGetRepositoryMapper(this, mapper)

fun <K, V> FlowPutRepository<K>.withMapping(toMapper: Mapper<K, V>, fromMapper: Mapper<V, K>): FlowPutRepository<V> = FlowPutRepositoryMapper(this, toMapper,
    fromMapper)