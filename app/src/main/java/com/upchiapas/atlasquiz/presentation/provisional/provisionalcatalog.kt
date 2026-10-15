package com.upchiapas.atlasquiz.presentation.provisional

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country

object ProvisionalCatalog {

    val countries: List<Country> = listOf(
        asia("asia_japon", "Japón", "Tokio"),
        asia("asia_china", "China", "Pekín"),
        asia("asia_india", "India", "Nueva Delhi"),
        asia("asia_tailandia", "Tailandia", "Bangkok"),
        asia("asia_corea_sur", "Corea del Sur", "Seúl"),
        asia("asia_vietnam", "Vietnam", "Hanói"),
        asia("asia_nepal", "Nepal", "Katmandú"),
        asia("asia_pakistan", "Pakistán", "Islamabad"),
        asia("asia_arabia_saudita", "Arabia Saudita", "Riad"),
        asia("asia_qatar", "Qatar", "Doha"),
        asia("asia_mongolia", "Mongolia", "Ulán Bator"),
        asia("asia_banglades", "Bangladés", "Daca"),
        asia("asia_camboya", "Camboya", "Nom Pen"),
        asia("asia_laos", "Laos", "Vientián"),
        asia("asia_butan", "Bután", "Timbú"),
        europe("europe_francia", "Francia", "París"),
        europe("europe_alemania", "Alemania", "Berlín"),
        europe("europe_italia", "Italia", "Roma"),
        europe("europe_espana", "España", "Madrid"),
        europe("europe_portugal", "Portugal", "Lisboa"),
        europe("europe_grecia", "Grecia", "Atenas"),
        europe("europe_austria", "Austria", "Viena"),
        europe("europe_polonia", "Polonia", "Varsovia"),
        europe("europe_suecia", "Suecia", "Estocolmo"),
        europe("europe_noruega", "Noruega", "Oslo"),
        europe("europe_finlandia", "Finlandia", "Helsinki"),
        europe("europe_irlanda", "Irlanda", "Dublín"),
        europe("europe_chequia", "Chequia", "Praga"),
        europe("europe_belgica", "Bélgica", "Bruselas"),
        europe("europe_hungria", "Hungría", "Budapest")
    )

    fun countriesOf(continent: Continent): List<Country> =
        countries.filter { it.continent == continent }

    private fun asia(id: String, name: String, capital: String) =
        Country(id, name, capital, Continent.ASIA)

    private fun europe(id: String, name: String, capital: String) =
        Country(id, name, capital, Continent.EUROPE)
}