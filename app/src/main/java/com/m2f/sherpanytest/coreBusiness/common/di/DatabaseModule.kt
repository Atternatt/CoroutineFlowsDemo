package com.m2f.sherpanytest.coreBusiness.common.di

import android.content.Context
import com.m2f.sherpanytest.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesSqlDriver(@ApplicationContext context: Context): SqlDriver = AndroidSqliteDriver(Database.Schema, context, "sherpanyTest.db")

    @Provides
    @Singleton
    fun providesDatabase(driver: SqlDriver): Database = Database.invoke(driver)
}