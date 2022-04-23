package com.example.maktabplus.utils

import com.example.maktabplus.data.model.image.Image
import com.example.maktabplus.data.model.movie.*
import com.google.gson.JsonObject

object Mapper {

    fun jsonToImage(jsonObject: JsonObject): Image {
        return Image(
            id = jsonObject["id"].asString,
            author = jsonObject["author"].asString,
            url = jsonObject["download_url"].asString
        )
    }

    fun jsonToMovieResponse(jsonObject: JsonObject): MovieResponse {
        val list = jsonObject["results"].asJsonArray.map {
            val json = it.asJsonObject
            Movie(
                id = json["id"].asInt,
                title = json["title"].asString,
                poster = json["poster_path"].asString
            )
        }
        return MovieResponse(list)
    }

    fun jsonToGenreResponse(jsonObject: JsonObject): GenreResponse {
        val list = jsonObject["genres"].asJsonArray.map {
            val json = it.asJsonObject
            Genre(
                id = json["id"].asInt,
                name = json["name"].asString,
            )
        }
        return GenreResponse(list)
    }

    fun jsonToMovieDetail(jsonObject: JsonObject): MovieDetail {
        return MovieDetail(
            movieId = jsonObject["id"].asInt,
            overview = jsonObject["overview"].asString,
            backdrop = jsonObject["backdrop_path"].asString,
            voteAverage = jsonObject["vote_average"].asFloat,
            voteCount = jsonObject["vote_count"].asInt,
        )
    }
}

