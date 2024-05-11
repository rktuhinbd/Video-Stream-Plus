package com.rktuhinbd.assessmenttask.utils

import android.text.TextUtils
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class ErrorHandler(
    val serverError: Response? = null,
    val localError: String? = null,
    val httpError: ResponseBody? = null,
    val errorCode:Int = -1
) {


    val msg: String
    val code: Int
    val msgWithCode: String
    var technicalError: String=""
    var curl: String? = ""

    init {
        if (serverError != null) {
            code = serverError.code()
            curl = "${serverError.request().url().host()}${serverError.request().url().encodedPath()}"
            msg = if (httpError != null) {
                technicalError = getHttpError(httpError)
                technicalError
            } else {
                handleMessage(code, serverError.message())
            }
        } else {
            msg = localError ?: "Local Error!"
            code = errorCode
            technicalError = "Technical error, $msg"
        }
        msgWithCode = "Code: $code, Error: $msg"
    }

    private fun handleMessage(code: Int, message: String): String {
        technicalError = message
        if (!TextUtils.isEmpty(message)) return message

        return when (code) {
            in 400..499 -> {
                "Unauthorized"
            }
            in 500..599 -> {
                "Server Error.Try after sometime"
            }
            else -> {
                "Unknown Error!"
            }
        }
    }

    private fun getHttpError(error: ResponseBody?): String {
        return try {
            val errorMsg = error?.charStream()?.readText()
                ?.let { JSONObject(it).getString("message").toString() }
            errorMsg ?: (localError ?: handleMessage(code, ""))
        } catch (e: Exception) {
            localError ?: handleMessage(code, "")
        }
    }
}