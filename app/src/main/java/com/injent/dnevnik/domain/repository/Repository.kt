package com.injent.dnevnik.domain.repository

import com.injent.dnevnik.data.Result
import com.injent.dnevnik.domain.model.ReportingPeriod
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.domain.model.UserContext
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getUser(): UserContext

    suspend fun init(token: String): Result<Boolean>

    suspend fun getAverageMarks(personId: Long, periodId: String): Result<Float>

    suspend fun getReportingPeriods(): Result<List<ReportingPeriod>>

    suspend fun getClassmates(): Result<List<Student>>

    suspend fun getUserProfile(userId: Long): Result<Student>
}