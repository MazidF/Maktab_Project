package com.example.maktabplus.utils

import com.example.maktabplus.data.model.image.Image
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.google.gson.JsonObject

object Mapper {

    fun jsonToImage(jsonObject: JsonObject): Image {
        return Image(
            id = jsonObject["id"].asString,
            author = jsonObject["author"].asString,
            url = jsonObject["download_url"].asString
        )
    }

    fun jsonToMovie(jsonObject: JsonObject): Movie {
        return Movie(
            id = jsonObject["id"].asInt,
            title = jsonObject["title"].asString,
            poster = jsonObject["poster_path"].asString
        )
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