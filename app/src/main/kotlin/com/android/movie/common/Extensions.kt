package com.android.movie.common

import androidx.annotation.StringRes
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.android.movie.R
import com.android.movie.common.result.Result
import retrofit2.HttpException
import java.io.IOException

fun <T> Result<T>.getErrorTitleId(): Int? = (this as? Result.Error)?.exception?.getErrorTitleId()

fun <T> Result<T>.getErrorDescriptionId(): Int? =  (this as? Result.Error)?.exception?.getErrorDescriptionId()

fun CombinedLoadStates.getErrorTitleId(): Int? = (refresh as? LoadState.Error ?: append as? LoadState.Error)?.error?.getErrorTitleId()

fun CombinedLoadStates.getErrorDescriptionId(): Int? = (refresh as? LoadState.Error ?: append as? LoadState.Error)?.error?.getErrorDescriptionId()
@StringRes
fun Throwable.getErrorTitleId(): Int {
    return when (this) {
        is IOException -> {
            R.string.connection_glitch
        }

        is HttpException -> {
            R.string.server_error
        }

        else -> {
            R.string.something_went_wrong
        }
    }
}

@StringRes
fun Throwable.getErrorDescriptionId(): Int {
    return when (this) {
        is IOException -> {
            R.string.connection_exception_description
        }

        is HttpException -> {
            R.string.server_error_description
        }

        else -> {
            R.string.something_went_wrong_description
        }
    }
}
