package com.joebarker.data

class ContentRetrieverMock(private val response: String) : ContentRetriever {

    override suspend fun getContentFromUrl(url: String?): String = response

}
