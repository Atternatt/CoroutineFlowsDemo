package com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PhotoEntity
import com.m2f.sherpanytest.sqldelight.data.PhotoDBO


class PhotoDBOToPhotoEntityMapper: Mapper<PhotoDBO, PhotoEntity> {
    override fun map(from: PhotoDBO): PhotoEntity = with(from) {
        PhotoEntity(albumId.toInt(), id.toInt(), thumbnailUrl, title, url)
    }
}