package com.example.librarymanager.data.network

import android.content.Context
import com.example.librarymanager.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var tokenStore: TokenStore
    private val _sessionExpired = MutableStateFlow(false)
    val sessionExpired = _sessionExpired.asStateFlow()
    private val _currentUser = MutableStateFlow<SessionUser?>(null)
    val currentUser = _currentUser.asStateFlow()

    lateinit var service: ApiService
        private set

    fun initialize(context: Context) {
        tokenStore = TokenStore(context.applicationContext)
        _currentUser.value = tokenStore.user
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        }
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().apply {
                    tokenStore.token?.let { header("Authorization", "Bearer $it") }
                }.build()
                val response = chain.proceed(request)
                if (response.code == 401 && tokenStore.token != null) {
                    clearToken()
                    _sessionExpired.value = true
                }
                response
            }
            .addInterceptor(logging)
            .build()

        service = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val isSignedIn: Boolean get() = ::tokenStore.isInitialized && !tokenStore.token.isNullOrBlank()

    fun saveSession(response: AuthResponse) {
        tokenStore.save(response)
        _currentUser.value = SessionUser(response.userId, response.name, response.email, response.role)
    }
    fun clearToken() {
        tokenStore.clear()
        _currentUser.value = null
    }
    fun consumeSessionExpiry() { _sessionExpired.value = false }
}
