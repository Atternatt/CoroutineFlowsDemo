package com.m2f.sherpanytest.coreBusiness.arch.data.datasource.network

import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.NetworkErrorException
import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import io.ktor.client.features.*
import io.ktor.http.*

object DefaultGenericNetworkErrorMapper : Mapper<ClientRequestException, Exception> {

  override fun map(from: ClientRequestException): Exception {
    when (from.response.status) {
      HttpStatusCode.NotFound -> {
        throw DataNotFoundException(from.message, from.cause)
      }
      else -> throw NetworkErrorException(from.response.status.value, from.message, from.cause)
    }
  }
}