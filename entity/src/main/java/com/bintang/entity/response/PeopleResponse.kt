package com.bintang.entity.response

import com.bintang.entity.NetworkResponseModel
import com.bintang.entity.entities.Person

data class PeopleResponse(
  val page: Int,
  val results: List<Person>,
  val total_results: Int,
  val total_pages: Int
) : NetworkResponseModel
