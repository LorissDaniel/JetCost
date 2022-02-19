package it.lorisdaniel.jetcosttest.model

data class Queries(
    var request: List<Request>,
    var nextPage: List<NextPage>?
)