package com.upchiapas.atlasquiz.data.datasource

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country

class CountriesSeedDataSource {

    private val countries: List<Country> = listOf(
        Country("asia_japon", "Japón", "Tokio", Continent.ASIA),
        Country("asia_china", "China", "Pekín", Continent.ASIA),
        Country("asia_india", "India", "Nueva Delhi", Continent.ASIA),
        Country("asia_tailandia", "Tailandia", "Bangkok", Continent.ASIA),
        Country("asia_corea_sur", "Corea del Sur", "Seúl", Continent.ASIA),
        Country("asia_vietnam", "Vietnam", "Hanói", Continent.ASIA),
        Country("asia_nepal", "Nepal", "Katmandú", Continent.ASIA),
        Country("asia_pakistan", "Pakistán", "Islamabad", Continent.ASIA),
        Country("asia_arabia_saudita", "Arabia Saudita", "Riad", Continent.ASIA),
        Country("asia_qatar", "Qatar", "Doha", Continent.ASIA),
        Country("asia_mongolia", "Mongolia", "Ulán Bator", Continent.ASIA),
        Country("asia_banglades", "Bangladés", "Daca", Continent.ASIA),
        Country("asia_camboya", "Camboya", "Nom Pen", Continent.ASIA),
        Country("asia_laos", "Laos", "Vientián", Continent.ASIA),
        Country("asia_butan", "Bután", "Timbú", Continent.ASIA),
        Country("europe_francia", "Francia", "París", Continent.EUROPE),
        Country("europe_alemania", "Alemania", "Berlín", Continent.EUROPE),
        Country("europe_italia", "Italia", "Roma", Continent.EUROPE),
        Country("europe_espana", "España", "Madrid", Continent.EUROPE),
        Country("europe_portugal", "Portugal", "Lisboa", Continent.EUROPE),
        Country("europe_grecia", "Grecia", "Atenas", Continent.EUROPE),
        Country("europe_austria", "Austria", "Viena", Continent.EUROPE),
        Country("europe_polonia", "Polonia", "Varsovia", Continent.EUROPE),
        Country("europe_suecia", "Suecia", "Estocolmo", Continent.EUROPE),
        Country("europe_noruega", "Noruega", "Oslo", Continent.EUROPE),
        Country("europe_finlandia", "Finlandia", "Helsinki", Continent.EUROPE),
        Country("europe_irlanda", "Irlanda", "Dublín", Continent.EUROPE),
        Country("europe_chequia", "Chequia", "Praga", Continent.EUROPE),
        Country("europe_belgica", "Bélgica", "Bruselas", Continent.EUROPE),
        Country("europe_hungria", "Hungría", "Budapest", Continent.EUROPE)
    )

    fun getCountries(): List<Country> = countries
}