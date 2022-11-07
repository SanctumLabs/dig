package io.github.sanctumlabs.dig.api

import io.github.sanctumlabs.dig.core.IdGenerator
import io.github.sanctumlabs.dig.core.SequenceIdGenerator
import io.github.sanctumlabs.dig.infra.plugins.configureRouting
import io.github.sanctumlabs.dig.services.IdGenService
import io.github.sanctumlabs.dig.services.SnowflakeSequenceIdGenerator
import io.github.sanctumlabs.dig.services.UUIDGenerator
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DigRestApiTest : KoinTest {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private val mock: IdGenService by lazy {
        declareMock()
    }

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single { IdGenService() }
                    single<IdGenerator> { UUIDGenerator() }
                    single<SequenceIdGenerator> { SnowflakeSequenceIdGenerator() }
                })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should inject id generator service`() {
        assertNotNull(get<IdGenService>())
    }

    @Test
    fun `should respond with id generated`() = testApplication {
        val generatedId = "123456789"

        every {
            mock.generateId()
        } returns generatedId

        val expectedResponse = IdResponseDto(id = generatedId)

        application {
            configureRouting()
        }

        client.get("/dig").apply {
            assertEquals(HttpStatusCode.OK, status)

            val responseBody = body<IdResponseDto>()
            assertEquals(expectedResponse, responseBody)
        }
    }
}
