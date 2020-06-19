package com.bintang.network.client

import com.bintang.entity.response.PeopleResponse
import com.bintang.entity.response.PersonDetail
import com.bintang.network.ApiResponse
import com.bintang.network.service.PeopleService
import com.bintang.network.transform

class PeopleClient(private val service: PeopleService) {

  fun fetchPopularPeople(
    page: Int,
    onResult: (response: ApiResponse<PeopleResponse>) -> Unit
  ) {
    this.service.fetchPopularPeople(page).transform(onResult)
  }

  fun fetchPersonDetail(
    page: Int,
    onResult: (response: ApiResponse<PersonDetail>) -> Unit
  ) {
    this.service.fetchPersonDetail(page).transform(onResult)
  }
}
