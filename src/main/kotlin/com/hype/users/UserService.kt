package com.hype.users

import org.springframework.stereotype.Service

@Service
class UserService {
    fun all(): List<User> = UserRepo.all()
}
