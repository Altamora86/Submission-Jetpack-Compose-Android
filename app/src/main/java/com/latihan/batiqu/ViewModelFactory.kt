package com.latihan.batiqu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.latihan.batiqu.data.repository.BatikRepository
import com.latihan.batiqu.ui.screen.detail.DetailViewModel
import com.latihan.batiqu.ui.screen.favorite.FavoriteViewModel
import com.latihan.batiqu.ui.screen.home.HomeViewModel

class ViewModelFactory(private val batikRepository: BatikRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(batikRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(batikRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(batikRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}