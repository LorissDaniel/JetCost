package it.lorisdaniel.jetcosttest.model

data class NextPage(
    var title: String? = null,
    var totalResults: String? = null,
    var searchTerms: String? = null,
    var count: Int? = null,
    var startIndex: Int? = null,
    var inputEncoding: String? = null,
    var outputEncoding: String? = null,
    var safe: String? = null,
    var cx: String? = null,
    var searchType: String? = null
)