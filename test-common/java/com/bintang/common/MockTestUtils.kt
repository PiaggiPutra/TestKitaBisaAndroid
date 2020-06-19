
package com.bintang.common

import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Person
import com.bintang.entity.entities.Tv
import com.bintang.entity.response.PersonDetail

class MockTestUtils {

  companion object {

    /** mocks a [Movie] entity. */
    fun mockMovie(
      keywords: List<Keyword> = emptyList(),
      videos: List<Video> = emptyList(),
      reviews: List<Review> = emptyList()
    ) = Movie(1, keywords, videos, reviews, "", false, "", "", ArrayList(), 123, "", "", "", "", 0f,
      0, false, 0f)

    /** mocks a [Tv] entity. */
    fun mockTv(
      keywords: List<Keyword> = emptyList(),
      videos: List<Video> = emptyList(),
      reviews: List<Review> = emptyList()
    ) = Tv(1, keywords, videos, reviews, "", 0f, 123, "", 0f, "", "", ArrayList(), ArrayList(), "",
      1, "", "")

    /** mocks a [Person] entity. */
    fun mockPerson() = Person(1, mockPersonDetail(), "", false, 123, "", 0f)

    /** mocks a list of [Person] entity. */
    fun mockMovieList(): List<Movie> {
      val movies = ArrayList<Movie>()
      movies.add(mockMovie())
      movies.add(mockMovie())
      movies.add(mockMovie())
      return movies
    }

    /** mocks a list of [Tv] entity. */
    fun mockTvList(): List<Tv> {
      val tvs = ArrayList<Tv>()
      tvs.add(mockTv())
      tvs.add(mockTv())
      tvs.add(mockTv())
      return tvs
    }

    /** mocks a list of [Person] entity. */
    fun mockPersonList(): List<Person> {
      val people = ArrayList<Person>()
      people.add(mockPerson())
      people.add(mockPerson())
      people.add(mockPerson())
      return people
    }

    /** mocks a list of [Keyword] model. */
    fun mockKeywordList(): List<Keyword> {
      val keywords = ArrayList<Keyword>()
      keywords.add(Keyword(100, "keyword0"))
      keywords.add(Keyword(101, "keyword1"))
      keywords.add(Keyword(102, "keyword2"))
      return keywords
    }

    /** mocks a list of [Video] model. */
    fun mockVideoList(): List<Video> {
      val videos = ArrayList<Video>()
      videos.add(Video("123", "video0", "", "", 0, ""))
      videos.add(Video("123", "video0", "", "", 0, ""))
      return videos
    }

    /** mocks a list of [Review] model. */
    fun mockReviewList(): List<Review> {
      val reviews = ArrayList<Review>()
      reviews.add(Review("123", "", "", ""))
      reviews.add(Review("123", "", "", ""))
      return reviews
    }

    /** mocks a [PersonDetail] model. */
    fun mockPersonDetail(): PersonDetail {
      return PersonDetail("", "", "", emptyList(), "")
    }
  }
}
