package com.m2f.sherpanytest.coreBusiness.arch.data.validator.vastra.strategies

public enum class ValidationStrategyResult {
  UNKNOWN,
  VALID,
  INVALID
}

interface ValidationStrategyDataSource

interface ValidationStrategy {

  fun <T : ValidationStrategyDataSource> isValid(t: T): ValidationStrategyResult =
    ValidationStrategyResult.VALID
}