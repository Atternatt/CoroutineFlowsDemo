package com.m2f.sherpanytest.coreBusiness.arch.data.error

class NetworkErrorException(val statusCode: Int, message: String?, throwable: Throwable?) :
    RuntimeException(message, throwable)
