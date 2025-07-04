package com.example.pavlovapp.feature_todolist.data.di

import android.content.Context
import androidx.room.Room
import com.example.pavlovapp.feature_todolist.data.local.ToDoTaskDao
import com.example.pavlovapp.feature_todolist.data.local.ToDoDatabase
import com.example.pavlovapp.feature_todolist.data.local.repo.ToDoTaskListRepoImpl
import com.example.pavlovapp.feature_todolist.data.remote.ToDoTaskApi
import com.example.pavlovapp.feature_todolist.domain.repo.ToDoTaskListRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {

    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): ToDoTaskApi {
        return retrofit.create(ToDoTaskApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://pavlovapp-fe39f-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
    }

    @Provides
    fun provideToDoDao(database: ToDoDatabase): ToDoTaskDao {
        return database.todoDao
    }

    @Singleton
    @Provides
    fun provideToDoDatabase(
            @ApplicationContext appContext: Context
        ): ToDoDatabase {
            return Room.databaseBuilder(
                appContext.applicationContext,
                ToDoDatabase::class.java,
                "todo_database"
            ).fallbackToDestructiveMigration().build()
        }

    @Provides
    @Singleton
    fun providesToDoRepo(db: ToDoDatabase, api: ToDoTaskApi, @IoDispatcher dispatcher: CoroutineDispatcher): ToDoTaskListRepo{
        return ToDoTaskListRepoImpl(db.todoDao, api, dispatcher)
    }
}