package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
){


    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `save book`() {
        // given
        val request = BookRequest("이상한 나라의 엘리스")
        // when
        val result = bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()

        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("이상한 나라의 엘리스")
    }

    @Test
    fun `loan book` () {
        // given
        bookRepository.save(Book(name = "이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("test", null))
        val request = BookLoanRequest("test", "이상한 나라의 엘리스")

        // when
        val result = bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()

        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isFalse()
    }

    @Test
    @DisplayName("책이 대출 되어있으면 실패한다.")
    fun `loan book fail` () {
        // given
        bookRepository.save(Book(name = "이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "이상한 나라의 엘리스", false))
        val request = BookLoanRequest("test", "이상한 나라의 엘리스")

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message.let {
            assertThat(it).isEqualTo("진작 대출되어 있는 책입니다")
        }
    }

    @Test
    fun `return book` () {
        //given
        bookRepository.save(Book(name = "이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "이상한 나라의 엘리스", false))
        val request = BookReturnRequest("test", "이상한 나라의 엘리스")

        //when
        val result = bookService.returnBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isTrue()
    }
}