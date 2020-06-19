package com.bintang.mvvm_coroutines.repository

interface Repository {
  // this override property is for saving network loading status.
  var isLoading: Boolean
}
