package com.joebarker.data

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class HttpJsonRetriever : ContentRetriever {

    override suspend fun getContentFromUrl(url: String?): String {
        val content = StringBuilder()
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            if (!responseCodeIsOk(connection)) throw IOException(connection.responseMessage)
            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream, Charset.forName("UTF-8")))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null)
                content.append(line).append("\n")
        } catch (e: StackOverflowError) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
        return content.toString()
    }

    private fun responseCodeIsOk(connection: HttpURLConnection) =
        connection.responseCode == HttpURLConnection.HTTP_OK

}