package com.m2f.sherpanytest.coreBusiness.arch.data.validator.vastra.strategies.invalidation

import com.m2f.sherpanytest.coreBusiness.arch.data.validator.vastra.strategies.ValidationStrategy
import com.m2f.sherpanytest.coreBusiness.arch.data.validator.vastra.strategies.ValidationStrategyDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.validator.vastra.strategies.ValidationStrategyResult

data class InvalidationStrategyDataSource(val isValid: Boolean) : ValidationStrategyDataSource

class InvalidationStrategy : ValidationStrategy {

  override fun <T : ValidationStrategyDataSource> isValid(t: T): ValidationStrategyResult {
    when (t) {
      is InvalidationStrategyDataSource -> {
        return if (!t.isValid) ValidationStrategyResult.INVALID else ValidationStrategyResult.UNKNOWN
      }
      else -> {
        throw IllegalArgumentException("object != InvalidationStrategyDataSource")
      }
    }
  }
}