def results = [
    "PASSED",
    "FAILED",
    "PASSED",
    "FAILED",
    "PASSED",
    "SKIPPED"
]

def passed = results.count { it == "PASSED" }
def failed = results.count { it == "FAILED" }
def skipped = results.count { it == "SKIPPED" }

println("Passed: $passed")
println("Failed: $failed")
println("Skipped: $skipped")