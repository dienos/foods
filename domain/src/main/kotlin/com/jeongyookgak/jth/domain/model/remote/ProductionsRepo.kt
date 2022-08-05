package com.jeongyookgak.jth.domain.model.remote

interface ProductionsRepo {
    val categories : List<Category>
    val productions : List<Production>
}