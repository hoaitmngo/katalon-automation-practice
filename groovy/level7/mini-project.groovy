// Input:

def executions = [
    [name: "TC001", browser: "Chrome",  status: "PASSED", duration: 320],
    [name: "TC002", browser: "Chrome",  status: "FAILED", duration: 1450],
    [name: "TC003", browser: "Firefox", status: "PASSED", duration: 620],
    [name: "TC004", browser: "Chrome",  status: "FAILED", duration: 2100],
    [name: "TC005", browser: "Firefox", status: "PASSED", duration: 410]
]
// Solution
// 1. How many failed?
def failedTests = executions.findAll {
    it.status == "FAILED"
}

println("Failed: ${failedTests.size()}")

// 2. Which browser has most failures?
def failuresByBrowser = failedTests.groupBy {
    it.browser
}

def mostFailedBrowser = failuresByBrowser.max { it.value.size() }

println("Browser with most failures: ${mostFailedBrowser.key} - with ${mostFailedBrowser.value.size()} failures")

// 3. Average execution time
def totalDuration = executions.sum {
    it.duration
}

def averageDuration = totalDuration / executions.size()

println("Average duration: ${averageDuration} ms")

// 4. Tests > 1 second
def slowTests = executions.findAll {
    it.duration > 1000
}

slowTests.each {
    println("${it.name} - ${it.duration} ms")
}

// 5. Pass rate
def passed = executions.count {
    it.status == "PASSED"
}

def total = executions.size()

def passRate = (passed / total) * 100

println("Pass Rate: ${passRate}%")
