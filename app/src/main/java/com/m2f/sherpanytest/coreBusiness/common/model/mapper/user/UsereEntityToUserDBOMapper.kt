package com.m2f.sherpanytest.coreBusiness.common.model.mapper.user

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.UserEntity
import com.m2f.sherpanytest.sqldelight.data.UserDBO


class UsereEntityToUserDBOMapper : Mapper<UserEntity, UserDBO> {
    override fun map(from: UserEntity): UserDBO = with(from) {
        UserDBO(id.toLong(), email, username, name)
    }
}