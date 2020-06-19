package com.bintang.network.service

import com.bintang.entity.response.KeywordListResponse
import com.bintang.entity.response.ReviewListResponse
import com.bintang.entity.response.VideoListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TvService {


  @GET("/3/tv/{tv_id}/keywords")
  fun fetchKeywords(@Path("tv_id") id: Int): Call<KeywordListResponse>


  @GET("/3/tv/{tv_id}/videos")
  fun fetchVideos(@Path("tv_id") id: Int): Call<VideoListResponse>

  @GET("/3/tv/{tv_id}/reviews")
  fun fetchReviews(@Path("tv_id") id: Int): Call<ReviewListResponse>
}
