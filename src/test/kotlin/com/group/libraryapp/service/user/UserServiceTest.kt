package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장이 정상적으로 동작한다.")
    fun saveUserTest() {
        //given
        val userCreateRequest = UserCreateRequest("전윤환", null)
        //when
        userService.saveUser(userCreateRequest)
        //then
        val results = userRepository.findAll()
        assertThat(results.size == 1)
        assertThat(results[0].name == "전윤환")
        assertThat(results[0].age).isNull()
    }
    /**
     * 코틀린에서는 null 이 아닐것이라고 생각하고 가져옴
     * 따라서 자바 코드에서 @nullable 어노테이션을 붙여야됨
     */

    @Test
    @DisplayName("유저 조회가 정상적으로 동작한다.")
    fun getUsersTest() {
        //given
        userRepository.saveAll(
            listOf(
                User(null,"A", null, Collections.emptyList()),
                User(null,"B", 27, Collections.emptyList()
            )
        ))
        //when
        val results = userService.getUsers()
        //then
        assertThat(results).hasSize(2)

        assertThat(results)
            .extracting("name", "age")
            .containsExactly(
                tuple("A", null),
                tuple("B", 27)
            )
    }

    @Test
    @DisplayName("유저 이름 수정이 정상적으로 동작한다.")
    fun updateUserNameTest() {
        //given
        val savedUser = userRepository.save( User(null,"A", null, Collections.emptyList()))
        val request = UserUpdateRequest(savedUser.id!!, "B")

        //when
        userService.updateUserName(request)

        //then
        val result = userRepository.findById(savedUser.id).get()
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제 기능이 정상적으로 동작한다.")
    fun deleteUserTest() {
        //given
        userRepository.save( User(null,"A", null, Collections.emptyList()))
        //when
        userService.deleteUser("A")
        //then
        val results = userRepository.findAll()
        assertThat(results).isEmpty()
    }
}