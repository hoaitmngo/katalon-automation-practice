def results = [
    [name: "Login",    status: "PASSED", duration: 450],
    [name: "Search",   status: "FAILED", duration: 1200],
    [name: "Checkout", status: "FAILED", duration: 800],
    [name: "Logout",   status: "PASSED", duration: 300]
]

def total = results.size()

def passed = results.count {
    it.status == "PASSED"
}

def failed = results.count {
    it.status == "FAILED"
}

def passRate = (passed / total) * 100

def failedTests = results
    .findAll { it.status == "FAILED" }
    .collect { it.name }

def slowTests = results
    .findAll { it.duration > 1000 }
    .collect { it.name }


println("Total: $total")
println("Passed: $passed")
println("Failed: $failed")
println("Pass Rate: ${passRate}%")

println("")
println("Failed Tests:")
failedTests.each {
    println("- $it")
}

println("")
println("Slow Tests:")
slowTests.each {
    println("- $it")
}