package ru.andronov.translate.app.adapters

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.andronov.translate.app.usecases.TranslationHistory
import ru.andronov.translate.app.usecases.TranslationHistoryRepository
import java.util.Date

class RoomRepository(private val dao: TranslationHistoryDao) : TranslationHistoryRepository {

    override suspend fun insertHistory(historyRecord: TranslationHistory) =
        withContext(Dispatchers.IO) {
            dao.insertHistory(
                TranslationHistoryDto(
                    historyRecord.id,
                    historyRecord.sourceText,
                    historyRecord.translatedText,
                    historyRecord.timestamp
                )
            )
        }

    override fun getTranslationHistory(): Flow<List<TranslationHistory>> {
        return dao.getTranslationHistory()
            .map { historyDtoList ->
                historyDtoList.map { dto ->
                    TranslationHistory(
                        id = dto.id,
                        sourceText = dto.sourceText,
                        translatedText = dto.translatedText,
                        timestamp = dto.timestamp
                    )
                }
            }
    }
}

@Database(entities = [TranslationHistoryDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): TranslationHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface TranslationHistoryDao {
    @Insert
    suspend fun insertHistory(history: TranslationHistoryDto)

    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC LIMIT 5")
    fun getTranslationHistory(): Flow<List<TranslationHistoryDto>>
}

@Entity(tableName = "translation_history")
data class TranslationHistoryDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sourceText: String,
    val translatedText: String,
    val timestamp: Long = Date().time,
)