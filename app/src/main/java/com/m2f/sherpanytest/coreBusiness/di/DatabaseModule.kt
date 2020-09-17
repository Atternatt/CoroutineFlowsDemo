package com.m2f.sherpanytest.coreBusiness.di

import android.content.Context
import com.m2f.sherpanytest.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    fun providesDatabaseDriver(context: Context): SqlDriver = AndroidSqliteDriver(Database.Schema, context, "app_database.db")
}