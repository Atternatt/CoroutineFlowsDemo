package com.m2f.sherpanytest.coreBusiness.common.model.mapper.album

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.AlbumEntity
import com.m2f.sherpanytest.sqldelight.data.AlbumDBO


class AlbumEntityToAlbumDBOMapper: Mapper<AlbumEntity, AlbumDBO> {
    override fun map(from: AlbumEntity): AlbumDBO = with(from) {
        AlbumDBO(id.toLong(), title, userId.toLong())
    }
}