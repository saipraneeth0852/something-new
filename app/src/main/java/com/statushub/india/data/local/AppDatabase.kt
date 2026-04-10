package com.statushub.india.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val category: String, // e.g. "Motivation", "God", "General"
    val language: String // e.g. "en", "hi", "te"
)

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes WHERE category = :category")
    fun getQuotesByCategory(category: String): Flow<List<Quote>>

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)
    
    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuoteCount(): Int
}

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
