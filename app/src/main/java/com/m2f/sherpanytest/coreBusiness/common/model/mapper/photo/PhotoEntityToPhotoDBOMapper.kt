package com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PhotoEntity
import com.m2f.sherpanytest.sqldelight.data.PhotoDBO


class PhotoEntityToPhotoDBOMapper: Mapper<PhotoEntity, PhotoDBO> {
    override fun map(from: PhotoEntity): PhotoDBO = with(from) {
        PhotoDBO(albumId.toLong(), id.toLong(), thumbnailUrl, title, url)
    }
}