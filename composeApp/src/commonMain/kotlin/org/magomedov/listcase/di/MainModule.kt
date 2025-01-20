package org.magomedov.listcase.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.magomedov.listcase.data.repositories.MainRepositoryImpl
import org.magomedov.listcase.domain.repositories.MainRepository
import org.magomedov.listcase.domain.use_cases.AddTaskUseCase
import org.magomedov.listcase.domain.use_cases.ChangeStatusTaskUseCase
import org.magomedov.listcase.domain.use_cases.GetTasksUseCase
import org.magomedov.listcase.domain.use_cases.RemoveTaskUseCase
import org.magomedov.listcase.domain.use_cases.GetTaskByIdUseCase

val mainModule = module {
    singleOf(::MainRepositoryImpl) { bind<MainRepository>() }
    factoryOf(::GetTasksUseCase)
    factoryOf(::ChangeStatusTaskUseCase)
    factoryOf(::AddTaskUseCase)
    factoryOf(::RemoveTaskUseCase)
    factoryOf(::GetTaskByIdUseCase)
}