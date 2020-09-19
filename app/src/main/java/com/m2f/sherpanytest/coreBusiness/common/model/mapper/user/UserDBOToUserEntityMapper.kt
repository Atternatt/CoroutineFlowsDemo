package com.m2f.sherpanytest.coreBusiness.common.model.mapper.user

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.UserEntity
import com.m2f.sherpanytest.sqldelight.data.UserDBO


class UserDBOToUserEntityMapper: Mapper<UserDBO, UserEntity> {
    override fun map(from: UserDBO): UserEntity = with(from) {
        UserEntity(email, userId.toInt(), name, userName)
    }
}