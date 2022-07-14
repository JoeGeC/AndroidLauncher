package com.joebarker.data

interface ContentRetriever {
    suspend fun getContentFromUrl(url: String?) : String
}
