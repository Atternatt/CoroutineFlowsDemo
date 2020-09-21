package com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Photo


interface FetchAllPhotosInteractor {

    suspend operator fun invoke(): List<Photo>
}