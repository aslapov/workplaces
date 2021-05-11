package com.workplaces.aslapov.data

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class UriAdapter {

    @FromJson
    fun fromJson(value: String?): Uri? {
        return if (value != null) Uri.parse(value) else null
    }

    @ToJson
    fun toJson(value: Uri?): String? {
        return value?.toString()
    }
}
