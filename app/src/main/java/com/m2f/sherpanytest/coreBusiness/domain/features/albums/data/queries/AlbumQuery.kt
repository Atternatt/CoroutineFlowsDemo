package com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries

import com.m2f.sherpanytest.coreBusiness.arch.data.query.IdQuery


class AlbumQuery(val id: Long): IdQuery<Long>(id)