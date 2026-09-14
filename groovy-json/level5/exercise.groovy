import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// Define variables

def allTestCases = data.projects
    .collectMany { projectItem -> projectItem.testSuites }
    .collectMany { suiteItem -> suiteItem.testCases }

def executions = allTestCases.collectMany { testCaseItem ->
    testCaseItem.executions
}

println "Total executions: ${executions.size()}"

// 1. All failed executions
println "\n================ EXERCISE 1 ================"

def failedExecutions = executions.findAll { executionItem ->
    executionItem.status == "FAILED"
}

println "\n1. Failed executions:"
failedExecutions.each { executionItem ->
    println "${executionItem.environment} - ${executionItem.status}"
}

// 2. All passed executions
println "\n================ EXERCISE 2 ================"

def passedExecutions = executions.findAll { executionItem ->
    executionItem.status == "PASSED"
}

println "\n2. Passed executions: ${passedExecutions.size()}"

// 3. Retry > 0
println "\n================ EXERCISE 3 ================"

def retriedExecutions = executions.findAll { executionItem ->
    executionItem.retry > 0
}

println "\n3. Retried executions:"
retriedExecutions.each { executionItem ->
    println "${executionItem.environment} - retry=${executionItem.retry}"
}

// 4. Failed in STAGING
println "\n================ EXERCISE 4 ================"

def stagingFailures = executions.findAll { executionItem ->
    executionItem.status == "FAILED" &&
    executionItem.environment == "STAGING"
}

println "\n4. STAGING failures:"
stagingFailures.each { executionItem ->
    println "${executionItem.failure.type} - ${executionItem.failure.message}"
}

// 5. AssertionError failures
println "\n================ EXERCISE 5 ================"

def assertionFailures = executions.findAll { executionItem ->
    executionItem.status == "FAILED" &&
    executionItem.failure?.type == "AssertionError"
}

println "\n5. AssertionError failures:"
assertionFailures.each { executionItem ->
    println "${executionItem.failure.type} - ${executionItem.failure.message}"
}

// 6. customer-service failures
println "\n================ EXERCISE 6 ================"

def customerServiceFailures = executions.findAll { executionItem ->
    executionItem.status == "FAILED" &&
    executionItem.failure?.component == "customer-service"
}

println "\n6. customer-service failures:"
customerServiceFailures.each { executionItem ->
    println executionItem.failure.message
}

// 7. Message contains "unavailable"
println "\n================ EXERCISE 7 ================"

def unavailableFailures = executions.findAll { executionItem ->
    executionItem.status == "FAILED" &&
    executionItem.failure?.message?.contains("unavailable")
}

println "\n7. Unavailable failures:"
unavailableFailures.each { executionItem ->
    println executionItem.failure.message
}

// 8. Test cases with at least one failure
println "\n================ EXERCISE 8 ================"

def testCasesWithFailures = allTestCases.findAll { testCaseItem ->
    testCaseItem.executions.any { executionItem ->
        executionItem.status == "FAILED"
    }
}

println "\n8. Test cases with failures:"
testCasesWithFailures.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}

// 9. Latest execution failed
println "\n================ EXERCISE 9 ================"

def testCasesWithLatestFailure = allTestCases.findAll { testCaseItem ->
    testCaseItem.executions &&
    testCaseItem.executions.last().status == "FAILED"
}

println "\n9. Latest execution failed:"
testCasesWithLatestFailure.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}

// 10. Never failed
println "\n================ EXERCISE 10 ================"

def testCasesThatNeverFailed = allTestCases.findAll { testCaseItem ->
    !testCaseItem.executions.any { executionItem ->
        executionItem.status == "FAILED"
    }
}

println "\n10. Never failed:"
testCasesThatNeverFailed.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}