package com.bintang.entity.response

import com.bintang.entity.NetworkResponseModel
import com.bintang.entity.Review

class ReviewListResponse(
  val id: Int,
  val page: Int,
  val results: List<Review>,
  val total_pages: Int,
  val total_results: Int
) : NetworkResponseModel
