package com.latihan.batiqu.di

import com.latihan.batiqu.data.repository.BatikRepository

object Injection {
    fun provideRepository(): BatikRepository {
        return BatikRepository.getInstance()
    }
}
