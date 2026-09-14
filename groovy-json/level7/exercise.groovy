import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// define variables

def allMembers = data.organization.teams.collectMany { teamItem ->
    teamItem.members
}

def allSuites = data.projects.collectMany { projectItem ->
    projectItem.testSuites
}

def allTestCases = allSuites.collectMany { suiteItem ->
    suiteItem.testCases
}

def allExecutions = allTestCases.collectMany { testCaseItem ->
    testCaseItem.executions
}


// 1. Highest-budget project first.
println "\n================ EXERCISE 1 ================"

def projectsByHighestBudget = data.projects.sort { projectItem ->
    -projectItem.budget
}

projectsByHighestBudget.each { projectItem ->
    println "${projectItem.name} - ${projectItem.budget}"
}


// 2. Team members by experience.
println "\n================ EXERCISE 2 ================"
def membersByExperience = allMembers.sort { memberItem ->
    memberItem.experienceYears
}

membersByExperience.each { memberItem ->
    println "${memberItem.name} - ${memberItem.experienceYears} years"
}

// 3. Test cases by estimated time.
println "\n================ EXERCISE 3 ================"

def testCasesByEstimatedTime = allTestCases.sort { testCaseItem ->
    testCaseItem.estimatedMinutes
}

testCasesByEstimatedTime.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.estimatedMinutes} minutes"
}

// 4. Executions by duration.

def executionsByDuration = allExecutions.sort { executionItem ->
    executionItem.durationSeconds
}

executionsByDuration.each { executionItem ->
    println "${executionItem.environment} - ${executionItem.durationSeconds}s"
}

// 5. Failed executions by retry count.
println "\n================ EXERCISE 5 ================"

def failedExecutions = allExecutions.findAll { executionItem ->
    executionItem.status == "FAILED"
}

def failedExecutionsByRetry = failedExecutions.sort { executionItem ->
    executionItem.retry
}

failedExecutionsByRetry.each { executionItem ->
    println "${executionItem.environment} - retry=${executionItem.retry}"
}

// 6. Projects alphabetically.
println "\n================ EXERCISE 6 ================"
def projectsAlphabetically = data.projects.sort { projectItem ->
    projectItem.name
}

projectsAlphabetically.each { projectItem ->
    println projectItem.name
}

// 7. Test cases by priority.
println "\n================ EXERCISE 7 ================"

def priorityOrder = [
    P1: 1,
    P2: 2,
    P3: 3
]

def testCasesByPriority = allTestCases.sort { testCaseItem ->
    priorityOrder[testCaseItem.priority]
}

testCasesByPriority.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.priority} - ${testCaseItem.name}"
}