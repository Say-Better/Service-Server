package io.say.better.support.test

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Tag("context")
@TestConstructor(autowireMode = ALL)
annotation class ContextTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Tag("develop")
@ExtendWith(MockKExtension::class)
annotation class DevelopTest
