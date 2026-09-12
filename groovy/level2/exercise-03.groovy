def results = [
    "Login - PASSED",
    "Checkout - FAILED",
    "Search - PASSED",
    "Payment - FAILED"
]

results.findAll {
    it.contains("FAILED")
}.each {
    println(it)
}
