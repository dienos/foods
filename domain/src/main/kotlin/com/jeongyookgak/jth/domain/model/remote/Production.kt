package com.jeongyookgak.jth.domain.model.remote

interface Production {
    var isFavorite : Boolean
    val key : String
    val categoryKey : String
    val name : String
    val price : String
}