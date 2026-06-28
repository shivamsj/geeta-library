package com.example.librarymanager.data.repository

import com.example.librarymanager.data.model.Member
import com.example.librarymanager.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRepository {
    fun load(onResult: (Result<List<Member>>) -> Unit) {
        ApiClient.service.getMembers().enqueue(callback(onResult))
    }

    fun save(member: Member, onResult: (Result<Unit>) -> Unit) {
        val call = if (member.id.isBlank()) ApiClient.service.createMember(member)
        else ApiClient.service.updateMember(member.id, member)
        call.enqueue(object : Callback<Member> {
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                if (response.isSuccessful) onResult(Result.success(Unit))
                else onResult(Result.failure(apiError(response.code())))
            }

            override fun onFailure(call: Call<Member>, throwable: Throwable) = onResult(Result.failure(throwable))
        })
    }

    fun delete(id: String, onResult: (Result<Unit>) -> Unit) {
        ApiClient.service.deleteMember(id).enqueue(deleteCallback(onResult))
    }

    private fun callback(onResult: (Result<List<Member>>) -> Unit) = object : Callback<List<Member>> {
        override fun onResponse(call: Call<List<Member>>, response: Response<List<Member>>) {
            val body = response.body()
            if (response.isSuccessful && body != null) onResult(Result.success(body))
            else onResult(Result.failure(apiError(response.code())))
        }

        override fun onFailure(call: Call<List<Member>>, throwable: Throwable) = onResult(Result.failure(throwable))
    }

    private fun deleteCallback(onResult: (Result<Unit>) -> Unit) = object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) onResult(Result.success(Unit))
            else onResult(Result.failure(apiError(response.code())))
        }

        override fun onFailure(call: Call<Void>, throwable: Throwable) = onResult(Result.failure(throwable))
    }

    private fun apiError(code: Int) = IllegalStateException("Member request failed (HTTP $code)")
}
