package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    @Test
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
}