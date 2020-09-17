package com.m2f.sherpanytest.coreBusiness.arch.data.repository

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.Operation
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query

class VoidRepository<V> : GetRepository<V>, PutRepository<V>, DeleteRepository {

  override suspend fun get(query: Query, operation: Operation): V = notSupportedOperation()

  override suspend fun getAll(query: Query, operation: Operation): List<V> = notSupportedOperation()

  override suspend fun put(query: Query, value: V?, operation: Operation): V = notSupportedOperation()

  override suspend fun putAll(query: Query, value: List<V>?, operation: Operation): List<V> = notSupportedOperation()

  override suspend fun delete(query: Query, operation: Operation) = notSupportedOperation()

  override suspend fun deleteAll(query: Query, operation: Operation) = notSupportedOperation()
}

class VoidGetRepository<V> : GetRepository<V> {

  override suspend fun get(query: Query, operation: Operation): V = notSupportedOperation()

  override suspend fun getAll(query: Query, operation: Operation): List<V> = notSupportedOperation()
}

class VoidPutRepository<V> : PutRepository<V> {
  override suspend fun put(query: Query, value: V?, operation: Operation): V = notSupportedOperation()

  override suspend fun putAll(query: Query, value: List<V>?, operation: Operation): List<V> = notSupportedOperation()
}

class VoidDeleteRepository : DeleteRepository {
  override suspend fun delete(query: Query, operation: Operation) = notSupportedOperation()

  override suspend fun deleteAll(query: Query, operation: Operation) = notSupportedOperation()
}