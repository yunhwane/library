package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Book(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    /**
     * require IllegalArgumentException을 던지는 함수
     */
    init {
        require(name.isNotBlank()) { "책 이름은 공백일 수 없습니다" }
    }


}