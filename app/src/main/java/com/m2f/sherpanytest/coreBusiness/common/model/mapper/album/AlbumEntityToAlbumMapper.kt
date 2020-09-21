package com.m2f.sherpanytest.coreBusiness.common.model.mapper.album

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.AlbumEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album


class AlbumEntityToAlbumMapper: Mapper<AlbumEntity, Album> {
    override fun map(from: AlbumEntity): Album = with(from) {
        Album(id, title, userId)
    }
}