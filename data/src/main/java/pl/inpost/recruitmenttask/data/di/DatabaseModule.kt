package pl.inpost.recruitmenttask.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.database.AppDatabase
import pl.inpost.recruitmenttask.data.database.converter.DateTimeConverter
import pl.inpost.recruitmenttask.data.database.converter.ListConverter

private const val DB_NAME = "recruitment"

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DB_NAME
    )
        .addTypeConverter(ListConverter())
        .addTypeConverter(DateTimeConverter())
        .build()

    @Provides
    fun provideShipmentDAO(database: AppDatabase) = database.shipmentDao()

}