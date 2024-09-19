package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
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
        return userRepository.findAll().map { UserResponse(it) }
    }

    @Transactional
    fun updateUser(userUpdateRequest: UserUpdateRequest) {
        val user = userRepository.findById(userUpdateRequest.id).orElseThrow { IllegalArgumentException("해당 유저가 존재하지 않습니다.") }
        user.updateName(userUpdateRequest.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name).orElseThrow { IllegalArgumentException("해당 유저가 존재하지 않습니다.") }
        userRepository.delete(user)
    }
}