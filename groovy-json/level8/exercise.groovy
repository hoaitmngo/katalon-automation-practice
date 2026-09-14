import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def allMembers = data.organization.teams
    .collectMany { teamItem ->
        teamItem.members
    }

def allSuites = data.projects
    .collectMany { projectItem ->
        projectItem.testSuites
    }

def allTestCases = allSuites
    .collectMany { suiteItem ->
        suiteItem.testCases
    }

def executions = allTestCases
    .collectMany { testCaseItem ->
        testCaseItem.executions
    }


// 1. Group projects by status.
println "\n================ EXERCISE 1 ================"

def projectsByStatus = data.projects.groupBy { projectItem ->
    projectItem.status
}

println projectsByStatus

// 2. Group test cases by priority.
println "\n================ EXERCISE 2 ================"

def testCasesByPriority = allTestCases.groupBy { testCaseItem ->
    testCaseItem.priority
}

println testCasesByPriority

testCasesByPriority.each { priority, testCaseList ->
    println "\nPriority: ${priority}"
    testCaseList.each { testCaseItem ->
        println "  ${testCaseItem.id} - ${testCaseItem.name}"
    }
}

// 3. Group test cases by automated status.
println "\n================ EXERCISE 3 ================"

def testCasesByAutomated = allTestCases.groupBy { testCaseItem ->
    testCaseItem.automated
}

println testCasesByAutomated

testCasesByAutomated.each { automated, testCaseList ->
    println "\nautomated = ${automated}"
    testCaseList.each { testCaseItem ->
        println "  ${testCaseItem.id} - ${testCaseItem.name}"
    }
}

// 4. Group executions by status.
println "\n================ EXERCISE 4 ================"

def executionsByStatus = executions.groupBy { executionItem ->
    executionItem.status
}

println executionsByStatus

// 5. Group executions by environment.
println "\n================ EXERCISE 5 ================"

def executionsByEnvironment = executions.groupBy { executionItem ->
    executionItem.environment
}

println executionsByEnvironment

executionsByEnvironment.each { environmentName, executionList ->
    println "Count: ${executionList.size()}"
}

// 6. Group failures by failure type.
println "\n================ EXERCISE 6 ================"

def failedExecutions = executions.findAll { executionItem ->
    executionItem.status == "FAILED"
}

def failuresByType = failedExecutions.groupBy { executionItem ->
    executionItem.failure?.type
}

println failuresByType

failuresByType.each { failureType, failureList ->
    println "\nFailure type: ${failureType}"
    failureList.each { executionItem ->
        println "  ${executionItem.environment} - ${executionItem.failure.message}"
    }
}

// 7. Group test suites by UI / API.
println "\n================ EXERCISE 7 ================"
def suitesByType = allSuites.groupBy { suiteItem ->
    suiteItem.type
}

println suitesByType

suitesByType.each { suiteType, suiteList ->
    println "\nType: ${suiteType}"
    suiteList.each { suiteItem ->
        println "  ${suiteItem.id} - ${suiteItem.name}"
    }
}

// 8. Group members by role.
println "\n================ EXERCISE 8 ================"
def membersByRole = allMembers.groupBy { memberItem ->
    memberItem.role
}

println membersByRole

membersByRole.each { roleName, memberList ->
    println "\nRole: ${roleName}"
    memberList.each { memberItem ->
        println "  ${memberItem.name}"
    }
}
