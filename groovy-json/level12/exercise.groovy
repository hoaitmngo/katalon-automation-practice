import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// define variables

def allTestCases = data.projects
    .collectMany { projectItem -> projectItem.testSuites }
    .collectMany { suiteItem -> suiteItem.testCases }

// Prepare all executions

def allExecutions = allTestCases
    .collectMany { testCaseItem -> testCaseItem.executions }

// Test case statistics

def totalTests = allTestCases.size()

def automatedTests = allTestCases.count { testCaseItem ->
    testCaseItem.automated
}

def manualTests = allTestCases.count { testCaseItem ->
    !testCaseItem.automated
}

// Priority statistics

def priorityStats = allTestCases
    .groupBy { testCaseItem -> testCaseItem.priority }
    .collectEntries { priority, testCaseList ->
        [(priority): testCaseList.size()]
    }

// Execution statistics

def executionStats = allExecutions
    .groupBy { executionItem -> executionItem.status }
    .collectEntries { status, executionList ->
        [(status.toLowerCase()): executionList.size()]
    }

// Build report

def report = [
    total: totalTests,
    automated: automatedTests,
    manual: manualTests,
    priority: priorityStats,
    execution: executionStats
]

println report
