package com.example.maktabplus.utils

import com.example.maktabplus.data.model.image.Image
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.model.movie.MovieResponse
import com.google.gson.JsonObject

object Mapper {

    fun jsonToImage(jsonObject: JsonObject): Image {
        return Image(
            id = jsonObject["id"].asString,
            author = jsonObject["author"].asString,
            url = jsonObject["download_url"].asString
        )
    }

    fun jsonToMovie(jsonObject: JsonObject): MovieResponse {
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

    fun jsonToMovieDetail(jsonObject: JsonObject): MovieDetail {
        return MovieDetail(
            ownerId = jsonObject["id"].asInt,
            overview = jsonObject["overview"].asString,
            backdrop = jsonObject["backdrop_path"].asString,
            voteAverage = jsonObject["vote_average"].asFloat,
            voteCount = jsonObject["vote_count"].asInt,
        )
    }
}

