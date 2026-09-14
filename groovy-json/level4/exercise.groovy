import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// 1. All test suites
println "\n================ EXERCISE 1 ================"

def allSuites = data.projects.collectMany { projectItem ->
    projectItem.testSuites
}

println "Total suites: ${allSuites.size()}"

// 2. All test cases
println "\n================ EXERCISE 2 ================"

def allTestCases = data.projects
    .collectMany { projectItem -> projectItem.testSuites }
    .collectMany { suiteItem -> suiteItem.testCases }

println "Total test cases: ${allTestCases.size()}"

// 3. All executions
println "\n================ EXERCISE 3 ================"

def allExecutions = allTestCases.collectMany { testCaseItem ->
    testCaseItem.executions
}

println "Total executions: ${allExecutions.size()}"

// 4. All failures
println "\n================ EXERCISE 4 ================"

def failedExecutions = allExecutions.findAll { executionItem ->
    executionItem.status == "FAILED"
}

println "Total failures: ${failedExecutions.size()}"

// 5. All environments
println "\n================ EXERCISE 5 ================"

def allEnvironments = data.projects.collectMany { projectItem ->
    projectItem.environments
}

// println "Total environments: ${allEnvironments.size()}"

// Environment names
def environmentNames = allEnvironments.collect { environmentItem ->
    environmentItem.name
}

println environmentNames

// 6. All skills
println "\n================ EXERCISE 6 ================"


def allMembers = data.organization.teams.collectMany { teamItem ->
    teamItem.members
}

def allSkills = allMembers.collectMany { memberItem ->
    memberItem.skills
}

println allSkills

// 7. Unique skills
println "\n================ EXERCISE 7 ================"

def uniqueSkills = allSkills.unique()

println uniqueSkills