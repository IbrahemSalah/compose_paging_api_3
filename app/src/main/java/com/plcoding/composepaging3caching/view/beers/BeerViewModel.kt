package com.plcoding.composepaging3caching.view.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.plcoding.composepaging3caching.data.database.BeerEntity
import com.plcoding.composepaging3caching.data.mapper.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val pager: Pager<Int, BeerEntity>
) : ViewModel() {

    val beerPagingFlow = pager.flow.map { value: PagingData<BeerEntity> ->
        value.map { it.toBeer() }
    }.cachedIn(viewModelScope)

}