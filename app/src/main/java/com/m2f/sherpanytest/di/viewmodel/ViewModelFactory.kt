package com.m2f.sherpanytest.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * This class is used in order to asist ViewModel Creation while injecting dependencies to it
 */
class ViewModelFactory @Inject
internal constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[modelClass]
    if (creator == null) {
      for ((key, value) in creators) {
        if (modelClass.isAssignableFrom(key)) {
          creator = value
          break
        }
      }
    }
    if (creator == null) {
      throw IllegalArgumentException("unknown model class $modelClass")
    }
    try {
      @Suppress("UNCHECKED_CAST")
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }

  }
}