package com.example.maktabplus.utils

import com.example.maktabplus.data.model.Image
import com.google.gson.JsonObject

object Mapper {

    fun jsonToImage(jsonObject: JsonObject): Image {
        return Image(
            id = jsonObject["id"].asString,
            author = jsonObject["author"].asString,
            url = jsonObject["download_url"].asString
        )
    }
}