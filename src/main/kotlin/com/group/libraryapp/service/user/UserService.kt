package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    private val userRepository: UserRepository
){

    @Transactional
    fun saveUser(UserCreateRequest: UserCreateRequest) {
        val user = User(UserCreateRequest.name, UserCreateRequest.age)
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.from(it) }
    }

    /**
     * jpa findByid 는 Optional 로 반환한다.
     * 이것을 변경할 수 없기 때문에 확장함수를 사용할 수 있다.
     */
    @Transactional
    fun updateUser(userUpdateRequest: UserUpdateRequest) {
        val user = userRepository.findByIdOrThrow(userUpdateRequest.id)
        user.updateName(userUpdateRequest.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }
}