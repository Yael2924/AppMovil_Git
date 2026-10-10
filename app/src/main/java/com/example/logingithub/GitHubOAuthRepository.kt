
package com.example.logingithub

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import java.io.IOException
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class GitHubDeviceCode(
    val deviceCode: String,
    val userCode: String,
    val verificationUri: String,
    val expiresIn: Int,
    val interval: Int
)

data class GitHubUser(
    val login: String,
    val name: String?,
    val avatarUrl: String?,
    val profileUrl: String
)

class GitHubOAuthRepository {

    companion object {
        // Reemplaza este valor con el Client ID de tu OAuth App.
        // El Client ID es público; nunca agregues aquí un Client Secret.
        private const val CLIENT_ID = "Ov23lik6Ca5kCYY0ti5k"

        private const val DEVICE_CODE_URL =
            "https://github.com/login/device/code"

        private const val ACCESS_TOKEN_URL =
            "https://github.com/login/oauth/access_token"

        private const val USER_URL =
            "https://api.github.com/user"

        private const val DEVICE_GRANT =
            "urn:ietf:params:oauth:grant-type:device_code"
    }

    suspend fun requestDeviceCode(): GitHubDeviceCode =
        withContext(Dispatchers.IO) {

            check(CLIENT_ID != "TU_CLIENT_ID") {
                "Configura el Client ID de GitHub."
            }

            val response = postForm(
                DEVICE_CODE_URL,
                mapOf(
                    "client_id" to CLIENT_ID,
                    "scope" to "read:user"
                )
            )

            GitHubDeviceCode(
                deviceCode = response.getString("device_code"),
                userCode = response.getString("user_code"),
                verificationUri =
                    response.getString("verification_uri"),
                expiresIn = response.getInt("expires_in"),
                interval = response.getInt("interval")
            )
        }

    suspend fun pollForAccessToken(
        deviceCode: GitHubDeviceCode
    ): String {
        var interval = deviceCode.interval.coerceAtLeast(1)
        var lastNetworkError: IOException? = null
        val deadline = System.currentTimeMillis() +
                deviceCode.expiresIn * 1000L

        while (System.currentTimeMillis() < deadline) {
            delay(interval * 1000L)

            val response = try {
                withContext(Dispatchers.IO) {
                    postForm(
                        ACCESS_TOKEN_URL,
                        mapOf(
                            "client_id" to CLIENT_ID,
                            "device_code" to deviceCode.deviceCode,
                            "grant_type" to DEVICE_GRANT
                        )
                    )
                }
            } catch (e: UnknownHostException) {
                // A temporary DNS failure should not cancel an otherwise valid
                // device authorization. Retry on the next polling interval.
                lastNetworkError = e
                delay(5000)
                continue
            } catch (e: SocketTimeoutException) {
                lastNetworkError = e
                delay(2000)
                continue
            } catch (e: ConnectException) {
                lastNetworkError = e
                delay(2000)
                continue
            }
            lastNetworkError = null

            val token = response.optString("access_token")
            if (token.isNotBlank()) {
                return token
            }

            when (response.optString("error")) {
                "authorization_pending" -> {
                    // El usuario todavía no ha autorizado.
                }

                "slow_down" -> {
                    interval += 5
                }

                "access_denied" -> {
                    throw IOException(
                        "El usuario canceló la autorización."
                    )
                }

                "expired_token" -> {
                    throw IOException(
                        "El código expiró. Inténtalo de nuevo."
                    )
                }

                "incorrect_client_credentials" -> {
                    throw IOException(
                        "Revisa el Client ID de tu OAuth App."
                    )
                }

                "device_flow_disabled" -> {
                    throw IOException(
                        "Activa Device Flow en GitHub."
                    )
                }

                else -> {
                    throw IOException(
                        "GitHub no pudo completar la autorización: " +
                                response.optString(
                                    "error",
                                    "respuesta inesperada"
                                )
                    )
                }
            }
        }

        if (lastNetworkError is UnknownHostException) {
            throw IOException(
                "Android no puede resolver github.com. Revisa la conexión a Internet o el DNS del dispositivo y vuelve a iniciar sesión.",
                lastNetworkError
            )
        }
        if (lastNetworkError != null) {
            throw IOException(
                "No se pudo conectar con GitHub antes de que expirara el código. Revisa tu conexión e inténtalo de nuevo.",
                lastNetworkError
            )
        }
        throw IOException("El código expiró. Inicia sesión nuevamente.")
    }

    suspend fun getAuthenticatedUser(
        accessToken: String
    ): GitHubUser {
        var lastNetworkError: IOException? = null
        for (attempt in 0 until 3) {
            try {
                return withContext(Dispatchers.IO) {
                    fetchAuthenticatedUser(accessToken)
                }
            } catch (e: UnknownHostException) {
                lastNetworkError = e
            } catch (e: SocketTimeoutException) {
                lastNetworkError = e
            } catch (e: ConnectException) {
                lastNetworkError = e
            }
            if (attempt < 2) {
                delay(2000L * (attempt + 1))
            }
        }

        val cause = lastNetworkError
        if (cause is UnknownHostException) {
            throw IOException(
                "Android no puede resolver github.com. Revisa la conexión a Internet o el DNS del dispositivo y vuelve a intentar.",
                cause
            )
        }
        throw IOException(
            "No se pudo conectar con GitHub después de varios intentos. Revisa tu conexión y vuelve a intentar.",
            cause
        )
    }

    private fun fetchAuthenticatedUser(
        accessToken: String
    ): GitHubUser {

        val connection =
            (URL(USER_URL).openConnection() as HttpURLConnection)

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            connection.setRequestProperty(
                "Authorization",
                "Bearer $accessToken"
            )
            connection.setRequestProperty(
                "Accept",
                "application/vnd.github+json"
            )
            connection.setRequestProperty(
                "X-GitHub-Api-Version",
                "2022-11-28"
            )
            connection.setRequestProperty(
                "User-Agent",
                "LoginGitHub-Android"
            )

            val status = connection.responseCode
            val stream = if (status in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val body = stream?.bufferedReader()?.use {
                it.readText()
            }.orEmpty()

            if (status !in 200..299) {
                throw IOException(
                    "No se pudo validar el usuario de GitHub " +
                            "(HTTP $status)."
                )
            }

            val json = JSONObject(body)

            return GitHubUser(
                login = json.getString("login"),
                name = json.optString("name")
                    .takeUnless {
                        it.isBlank() || it == "null"
                    },
                avatarUrl = json.optString("avatar_url")
                    .takeUnless {
                        it.isBlank() || it == "null"
                    },
                profileUrl = json.getString("html_url")
            )
        } finally {
            connection.disconnect()
        }
    }

    private fun postForm(
        endpoint: String,
        parameters: Map<String, String>
    ): JSONObject {
        val connection =
            URL(endpoint).openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.doOutput = true

            connection.setRequestProperty(
                "Accept",
                "application/json"
            )
            connection.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            )
            connection.setRequestProperty(
                "User-Agent",
                "LoginGitHub-Android"
            )

            val formBody = parameters.entries.joinToString("&") {
                "${encode(it.key)}=${encode(it.value)}"
            }

            connection.outputStream.use {
                it.write(formBody.toByteArray(Charsets.UTF_8))
            }

            val status = connection.responseCode
            val stream = if (status in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val body = stream?.bufferedReader()?.use {
                it.readText()
            }.orEmpty()

            if (body.isBlank()) {
                throw IOException(
                    "GitHub devolvió una respuesta vacía " +
                        "(HTTP $status)."
                )
            }

            val json = JSONObject(body)

            if (status !in 200..299) {
                throw IOException(
                    "Error HTTP $status al contactar GitHub."
                )
            }

            return json
        } finally {
            connection.disconnect()
        }
    }

    private fun encode(value: String): String =
        URLEncoder.encode(value, "UTF-8")
}
