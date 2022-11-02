package io.github.sanctumlabs.dig.infra.di

import io.github.sanctumlabs.dig.core.IdGenerator
import io.github.sanctumlabs.dig.core.SequenceIdGenerator
import io.github.sanctumlabs.dig.services.IdGenService
import io.github.sanctumlabs.dig.services.SnowflakeSequenceIdGenerator
import io.github.sanctumlabs.dig.services.UUIDGenerator
import org.koin.dsl.module

val idGeneratorModule = module {
    single<IdGenerator> { UUIDGenerator() }
}

val sequenceGeneratorModule = module {
    single<SequenceIdGenerator> { SnowflakeSequenceIdGenerator() }
}

val idGenServiceModule = module {
    single { IdGenService() }
}
