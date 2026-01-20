package com.smtm.pickle.data.common.extension

import android.util.Base64
import org.json.JSONObject

/** Access Token 존재 여부 파악 */
fun String.isExpired(
    bufferSeconds: Long = 30L
): Boolean {
    return try {
        val parts = split(".")
        if (parts.size != 3) return true

        val payload = String(
            Base64.decode(
                parts[1],
                Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
            )
        )

        val json = JSONObject(payload)
        val exp = json.getLong("exp") // seconds

        val now = System.currentTimeMillis() / 1000
        now >= (exp - bufferSeconds)
    } catch (_: Exception) {
        true // 파싱 실패 = 만료로 간주
    }
}
