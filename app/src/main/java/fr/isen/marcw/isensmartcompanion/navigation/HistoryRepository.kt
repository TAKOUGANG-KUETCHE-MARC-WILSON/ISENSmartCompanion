package fr.isen.marcw.isensmartcompanion.navigation

class HistoryRepository(private val historyDao: HistoryDao) {
    suspend fun insertHistory(question: String, response: String) {
        historyDao.insertHistory(HistoryEntity(question = question, response = response))
    }

    suspend fun getAllHistory(): List<HistoryEntity> {
        return historyDao.getAllHistory()
    }

    suspend fun deleteHistoryById(id: Int) {
        historyDao.deleteHistoryById(id)
    }

    suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}
