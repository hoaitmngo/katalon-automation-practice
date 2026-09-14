import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def allSuites = data.projects
    .collectMany { projectItem -> projectItem.testSuites }

def allTestCases = allSuites
    .collectMany { suiteItem -> suiteItem.testCases }

def allExecutions = allTestCases
    .collectMany { testCaseItem -> testCaseItem.executions }


// 0. Total project budget
println "\n================ EXERCISE 0 ================"

def totalBudget = data.projects.sum { projectItem ->
    projectItem.budget
}

println "Total budget: $totalBudget"


// 1. Average project budget
println "\n================ EXERCISE 1 ================"

def averageBudget = totalBudget / data.projects.size()

println "Average project budget: $averageBudget"


// 2. Total number of test cases
println "\n================ EXERCISE 2 ================"

def totalTestCases = allTestCases.size()

println "Total test cases: $totalTestCases"


// 3. Total estimated testing minutes
println "\n================ EXERCISE 3 ================"

def totalEstimatedMinutes = allTestCases.sum { testCaseItem ->
    testCaseItem.estimatedMinutes
}

println "Total estimated minutes: $totalEstimatedMinutes"


// 4. Number of automated tests
println "\n================ EXERCISE 4 ================"

def automatedTestCount = allTestCases.count { testCaseItem ->
    testCaseItem.automated == true
}

println "Automated tests: $automatedTestCount"

// 5. Automation percentage
println "\n================ EXERCISE 5 ================"

def automationPercentage =
    (automatedTestCount / totalTestCases) * 100

println "Automation percentage: ${automationPercentage.round(2)}%"


// 6. Total execution duration
println "\n================ EXERCISE 6 ================"

def totalExecutionDuration = allExecutions.sum { executionItem ->
    executionItem.durationSeconds
}

println "Total execution duration: $totalExecutionDuration seconds"


// 7. Average execution duration
println "\n================ EXERCISE 7 ================"

def averageExecutionDuration =
    totalExecutionDuration / allExecutions.size()

println "Average execution duration: ${averageExecutionDuration.round(2)} seconds"


// 8. Maximum execution duration
println "\n================ EXERCISE 8 ================"

def maxExecutionDuration = allExecutions.max { executionItem ->
    executionItem.durationSeconds
}

println "Maximum execution duration: ${maxExecutionDuration.durationSeconds} seconds"


// 9. Minimum execution duration
println "\n================ EXERCISE 9 ================"

def minExecutionDuration = allExecutions.min { executionItem ->
    executionItem.durationSeconds
}

println "Minimum execution duration: ${minExecutionDuration.durationSeconds} seconds"


// 10. Total retry count
println "\n================ EXERCISE 10 ================"

def totalRetryCount = allExecutions.sum { executionItem ->
    executionItem.retry
}

println "Total retry count: $totalRetryCount"
