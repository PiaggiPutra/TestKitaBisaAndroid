package com.bintang.entity.response

import com.bintang.entity.Keyword
import com.bintang.entity.NetworkResponseModel

data class KeywordListResponse(
  val id: Int,
  val keywords: List<Keyword>
) : NetworkResponseModel
