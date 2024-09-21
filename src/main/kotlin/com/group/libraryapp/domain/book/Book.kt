package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Book(
    val name: String,

    val type: String,

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

    companion object {
        fun fixture(
            name: String = "책 이름",
            type: String = "Computer",
            id: Long? = null,
            ): Book {
                return Book(
                    name = name,
                    type = type,
                    id = id,
                )
            }
        }
}