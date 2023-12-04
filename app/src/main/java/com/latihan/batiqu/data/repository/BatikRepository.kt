package com.latihan.batiqu.data.repository

import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.data.local.BatikData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class BatikRepository {
    private val dummyBatik = mutableListOf<Batik>()

    init {
        if (dummyBatik.isEmpty()) {
            BatikData.dummyBatiks.forEach {
                dummyBatik.add(it)
            }
        }
    }

    fun getBatikById(batikId: Int): Batik {
        return dummyBatik.first {
            it.id == batikId
        }
    }

    fun getFavoriteBatik(): Flow<List<Batik>> {
        return flowOf(dummyBatik.filter { it.isFavorite })
    }

    fun searchBatik(query: String) = flow {
        val data = dummyBatik.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateBatik(batikId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyBatik.indexOfFirst { it.id == batikId }
        val result = if (index >= 0) {
            val batik = dummyBatik[index]
            dummyBatik[index] = batik.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: BatikRepository? = null

        fun getInstance(): BatikRepository =
            instance ?: synchronized(this) {
                BatikRepository().apply {
                    instance = this
                }
            }
    }
}