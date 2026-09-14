import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// Define varibles 
def allSuites = data.projects
    .collectMany { projectItem ->
        projectItem.testSuites
    }

def allTestCases = allSuites
    .collectMany { suiteItem ->
        suiteItem.testCases
    }

def allExecutions = allTestCases
    .collectMany { testCaseItem ->
        testCaseItem.executions
}

// 1. Total project budget
def totalBudget = data.projects.inject(0) { accumulator, projectItem ->
    accumulator + projectItem.budget
}

println "Total budget: $totalBudget"

// 2. Total retry count
def totalRetry = allExecutions.inject(0) { accumulator, executionItem ->
    accumulator + executionItem.retry
}

println "Total retry: $totalRetry"

// 3. Total execution duration
def totalDuration = allExecutions.inject(0.0) { accumulator, executionItem ->
    accumulator + executionItem.durationSeconds
}

println "Total duration: $totalDuration seconds"

// 4. Execution status counters
def executionStatusCounters = allExecutions.inject([
    PASSED: 0,
    FAILED: 0,
    SKIPPED: 0
]) { counters, executionItem ->
    counters[executionItem.status]++
    counters
}

println "Execution status: $executionStatusCounters"

// 5. Test-case priority counters
def priorityCounters = allTestCases.inject([
    P1: 0,
    P2: 0,
    P3: 0
]) { counters, testCaseItem ->
    counters[testCaseItem.priority]++
    counters
}

println "Priority counters: $priorityCounters"

// 6. Number of tests per project
def testsPerProject = data.projects.inject([:]) { result, projectItem ->
    def testCaseCount = projectItem.testSuites
        .collectMany { suiteItem ->
            suiteItem.testCases
        }
        .size()
    result[projectItem.id] = testCaseCount
    result
}

println "Tests per project: $testsPerProject"
