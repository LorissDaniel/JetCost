package it.lorisdaniel.jetcosttest.model

data class GoogleSearchResponse(
    var queries: Queries,
    var items: List<Item> = arrayListOf()
)