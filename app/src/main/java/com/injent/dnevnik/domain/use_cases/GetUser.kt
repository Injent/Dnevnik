package com.injent.dnevnik.domain.use_cases

import com.injent.dnevnik.domain.repository.Repository

class GetUser(
    private val repository: Repository
) {
    operator fun invoke() = repository.getUser()
}