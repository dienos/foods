package com.jeongyookgak.jth.domain.model.remote

interface ProductionsRepo {
    val code : String
    val categories : List<Category>
    val productions : List<Production>
}