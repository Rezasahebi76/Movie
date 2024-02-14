package com.android.movie.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: MovieDispatchers)

enum class MovieDispatchers {
    Default,
    Main,
    IO,
}