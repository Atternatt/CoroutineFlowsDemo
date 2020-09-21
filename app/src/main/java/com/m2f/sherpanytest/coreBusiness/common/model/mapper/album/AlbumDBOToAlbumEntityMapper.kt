package com.m2f.sherpanytest.coreBusiness.common.model.mapper.album

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.AlbumEntity
import com.m2f.sherpanytest.sqldelight.data.AlbumDBO


class AlbumDBOToAlbumEntityMapper : Mapper<AlbumDBO, AlbumEntity> {
    override fun map(from: AlbumDBO): AlbumEntity = with(from) {
        AlbumEntity(id.toInt(), title, userId.toInt())
    }
}