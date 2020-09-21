package com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PhotoEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Photo


class PhotoEntityToPhotoMapper: Mapper<PhotoEntity, Photo> {
    override fun map(from: PhotoEntity): Photo = with(from) {
        Photo(albumId, id, thumbnailUrl, title, url)
    }
}