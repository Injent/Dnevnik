package com.injent.dnevnik.data

import com.injent.dnevnik.domain.model.ReportingPeriod
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.domain.model.UserContext
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface Dnevnik {

    @GET("users/me/context")
    suspend fun getUserContext(@Header("Access-Token") token: String): Response<UserContext>

    @GET("persons/{personId}/reporting-periods/{period}/avg-mark")
    suspend fun getAverageMarks(@Header("Access-Token") token: String, @Path("personId") personId: Long, @Path("period") period: String): Response<String>

    @GET("edu-groups/{eduGroup}/reporting-periods")
    suspend fun getReportingPeriods(@Header("Access-Token") token: String, @Path("eduGroup") eduGroup: Long): Response<List<ReportingPeriod>>

    @GET("edu-groups/{group}/persons")
    suspend fun getClassmates(@Header("Access-Token") token: String, @Path("group") group: Long): Response<List<Student>>

    @GET("users/{user}")
    suspend fun getUserProfile(@Header("Access-Token") token: String, @Path("user") userId: Long) : Response<Student>
}