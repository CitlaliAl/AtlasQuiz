package com.upchiapas.atlasquiz.data.mapper

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country
import org.json.JSONArray
import org.json.JSONObject

private const val KEY_ID = "id"
private const val KEY_NAME = "name"
private const val KEY_CAPITAL = "capital"
private const val KEY_CONTINENT = "continent"

fun JSONObject.toCountry(): Country = Country(
    id = getString(KEY_ID),
    name = getString(KEY_NAME),
    capital = getString(KEY_CAPITAL),
    continent = Continent.valueOf(getString(KEY_CONTINENT))
)

fun Country.toJson(): JSONObject = JSONObject()
    .put(KEY_ID, id)
    .put(KEY_NAME, name)
    .put(KEY_CAPITAL, capital)
    .put(KEY_CONTINENT, continent.name)

fun String.toCountries(): List<Country> {
    val array = JSONArray(this)
    return List(array.length()) { index -> array.getJSONObject(index).toCountry() }
}

fun List<Country>.toJsonString(): String =
    JSONArray(map { country -> country.toJson() }).toString()