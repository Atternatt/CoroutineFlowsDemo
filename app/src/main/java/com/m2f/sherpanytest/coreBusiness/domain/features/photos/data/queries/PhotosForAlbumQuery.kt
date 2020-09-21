package com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries

import com.m2f.sherpanytest.coreBusiness.arch.data.query.IdQuery


class PhotosForAlbumQuery(albumId: Long): IdQuery<Long>(albumId)