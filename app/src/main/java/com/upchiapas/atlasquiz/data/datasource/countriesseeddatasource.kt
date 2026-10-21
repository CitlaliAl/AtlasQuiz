package com.upchiapas.atlasquiz.data.datasource

import android.content.res.AssetManager
import com.upchiapas.atlasquiz.data.mapper.toCountries
import com.upchiapas.atlasquiz.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountriesSeedDataSource(
    private val assets: AssetManager
) {
    private val seedCountries: List<Country> by lazy { readSeedFile() } //by lazy lee el archivo una sola vez y guarda el resultado

    suspend fun getCountries(): List<Country> = withContext(Dispatchers.IO) { //withContext(Dispatchers.IO) hace la lectura de disco fuera del hilo principal
        seedCountries
    }

    private fun readSeedFile(): List<Country> =
        assets.open(SEED_FILE_NAME).bufferedReader().use { reader -> reader.readText() }
            .toCountries()

    private companion object {
        const val SEED_FILE_NAME = "countries_seed.json"
    }
}