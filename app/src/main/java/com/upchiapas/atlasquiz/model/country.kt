package com.upchiapas.atlasquiz.model

data class Country(
    val id: String,
    val name: String,
    val capital: String,
    val continent: Continent
)