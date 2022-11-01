package com.injent.dnevnik.data

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.injent.dnevnik.data.Result.Error
import com.injent.dnevnik.data.Result.Success
import com.injent.dnevnik.domain.model.ReportingPeriod
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.domain.model.UserContext
import com.injent.dnevnik.domain.repository.Repository
import java.math.RoundingMode

const val BASE_URL = "https://api.dnevnik.ru/v2/"

class RepositoryImpl(
    private var dnevnik: Dnevnik
) : Repository {

    private var token: String = ""
    private var user: UserContext? = null

    override fun getUser(): UserContext {
        return user!!
    }

    override suspend fun init(token: String): Result<Boolean> {
        this.token = token
        val body = dnevnik.getUserContext(token).body()
        Log.e("RepositoryImpl/init", "Token: $token | ${body.toString()}")
        if (body != null) {
            user = body
            return Success(true)
        }
        return Error(NullPointerException())
    }

    override suspend fun getAverageMarks(personId: Long, periodId: String): Result<Float> {
        val body = dnevnik.getAverageMarks(token, personId, periodId).body()
        return if (body != null)
            Success(body.replace(",", ".").toBigDecimal().setScale(3, RoundingMode.UP).toFloat())
        else
            Error(NullPointerException())
    }

    override suspend fun getReportingPeriods(): Result<List<ReportingPeriod>> {
        val body = dnevnik.getReportingPeriods(token, getUser().groupIds[0]).body()
        return if (body != null)
            Success(body)
        else
            Error(NullPointerException())
    }

    override suspend fun getClassmates(): Result<List<Student>> {
        val body = dnevnik.getClassmates(token, getUser().groupIds[0]).body()
        return if (body != null)
            Success(body)
        else
            Error(NullPointerException())
    }

    override suspend fun getUserProfile(userId: Long): Result<Student> {
        val body = dnevnik.getUserProfile(token, userId).body()
        return if (body != null)
            Success(body)
        else
            Error(NullPointerException())
    }
}
