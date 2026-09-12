def tests = [
    [name: "Login", status: "PASSED"],
    [name: "Checkout", status: "FAILED"],
    [name: "Search", status: "PASSED"],
    [name: "Payment", status: "FAILED"]
]

def failedTests = tests
    .findAll { it.status == "FAILED" }
    .collect { it.name }

println(failedTests)