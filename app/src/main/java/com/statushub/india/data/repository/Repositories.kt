package com.statushub.india.data.repository

import com.statushub.india.data.local.Quote
import com.statushub.india.data.local.QuoteDao
import com.statushub.india.data.remote.PexelsApi
import com.statushub.india.data.remote.PexelsPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor(
    private val api: PexelsApi
) {
    // Ideally put this in local.properties or BuildConfig
    private val apiKey = com.statushub.india.util.AppConfig.PEXELS_API_KEY

    suspend fun getWallpapers(category: String, page: Int = 1, perPage: Int = 30): Result<List<PexelsPhoto>> {
        return try {
            val response = api.searchWallpapers(apiKey, category, perPage, page)
            Result.success(response.photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Singleton
class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao
) {
    fun getQuotes(category: String): Flow<List<Quote>> {
        return quoteDao.getQuotesByCategory(category)
    }

    suspend fun prePopulateQuotesIfEmpty() {
        if (quoteDao.getQuoteCount() == 0) {
            val dummyQuotes = listOf(
                Quote(text = "भगवान पर भरोसा रखो 🙏", category = "Bhakti", language = "hi"),
                Quote(text = "कर्म करो, फल की चिंता मत करो।", category = "Bhakti", language = "hi"),
                Quote(text = "Every morning is a new beginning.", category = "Good Morning", language = "en"),
                Quote(text = "सुप्रभात! आपका दिन मंगलमय हो।", category = "Good Morning", language = "hi"),
                Quote(text = "Success is not final, failure is not fatal.", category = "Motivation", language = "en"),
                Quote(text = "కష్టపడితే సాధించలేనిది ఏదీ లేదు.", category = "Motivation", language = "te")
            )
            quoteDao.insertQuotes(dummyQuotes)
        }
    }
}
