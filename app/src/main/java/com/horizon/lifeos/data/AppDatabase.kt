package com.horizon.lifeos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TransactionEntity::class,
        FocusSessionEntity::class,
        GoalEntity::class,
        TaskEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LifeOSDatabase : RoomDatabase() {
    abstract fun lifeOSDao(): LifeOSDao
}
