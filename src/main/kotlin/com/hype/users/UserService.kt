package com.hype.users

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
    fun all(): List<User> = UserRepo.all()

    fun find(id: UUID): User = UserRepo.find(id)

    fun create(user: User): User {
        UserRepo.create(user)
        return UserRepo.find(user.id)
    }

    fun update(user: User): User {
        UserRepo.update(user)
        return UserRepo.find(user.id)
    }

    fun delete(id: UUID): User {
        val user = UserRepo.find(id)
        UserRepo.delete(id)
        return user
    }
}
