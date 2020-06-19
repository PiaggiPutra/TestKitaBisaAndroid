package com.bintang.network.service

import com.bintang.entity.response.PeopleResponse
import com.bintang.entity.response.PersonDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {


  @GET("/3/person/popular?language=en")
  fun fetchPopularPeople(@Query("page") page: Int): Call<PeopleResponse>

  @GET("/3/person/{person_id}")
  fun fetchPersonDetail(@Path("person_id") id: Int): Call<PersonDetail>
}
