package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDatabase() : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.apply {
                    insert(Task("Wash the dishes"))
                    insert(Task("Do the laundry"))
                    insert(Task("Buy groceries", important = true))
                    insert(Task("Prepare food", completed = true))
                    insert(Task("Call mom"))
                    insert(Task("Visit grandma", completed = true))
                    insert(Task("Repair my bike"))
                    insert(Task("Call Elon Musk"))
                }
            }
        }
    }
}