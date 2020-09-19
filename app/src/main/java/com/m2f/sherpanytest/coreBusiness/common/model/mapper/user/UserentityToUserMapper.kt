package com.m2f.sherpanytest.coreBusiness.common.model.mapper.user

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.UserEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.User


class UserentityToUserMapper: Mapper<UserEntity, User> {
    override fun map(from: UserEntity): User = with(from) {
        User(email, id, name, username)
    }
}