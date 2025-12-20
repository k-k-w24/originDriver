package com.transportcompany.api.service

import com.transportcompany.api.dto.LoginRequest
import com.transportcompany.api.dto.LoginResponse
import com.transportcompany.api.dto.UserInfo
import com.transportcompany.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
        
        return if (user != null && user.password == request.password) {
            // 簡易的な認証（実際のアプリではハッシュ化されたパスワードとJWTトークンを使用）
            val token = Base64.getEncoder().encodeToString("${user.username}:${user.password}".toByteArray())
            LoginResponse(
                success = true,
                message = "ログイン成功",
                token = token,
                user = UserInfo(
                    id = user.id,
                    username = user.username,
                    name = user.name,
                    role = user.role.name
                )
            )
        } else {
            LoginResponse(
                success = false,
                message = "ユーザー名またはパスワードが正しくありません"
            )
        }
    }
}

