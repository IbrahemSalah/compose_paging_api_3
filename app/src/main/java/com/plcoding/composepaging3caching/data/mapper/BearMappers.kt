package com.plcoding.composepaging3caching.data.mapper

import com.plcoding.composepaging3caching.data.BeerDTO
import com.plcoding.composepaging3caching.data.database.BeerEntity
import com.plcoding.composepaging3caching.domain.Beer

fun BeerDTO.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}


fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        desc = description,
        firstBrewed = firstBrewed,
        url = imageUrl
    )
}