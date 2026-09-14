import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// 1. Projects containing at least one API suite.
println "\n================ EXERCISE 1 ================"

def projectsWithApiSuite = data.projects.findAll { projectItem ->
    projectItem.testSuites.any { suiteItem ->
        suiteItem.type == "API"
    }
}

projectsWithApiSuite.each { projectItem ->
    println "${projectItem.id} - ${projectItem.name}"
}


// 2. Projects containing at least one failed test.
println "\n================ EXERCISE 2 ================"

def projectsWithFailedTest = data.projects.findAll { projectItem ->
    projectItem.testSuites.any { suiteItem ->
        suiteItem.testCases.any { testCaseItem ->
            testCaseItem.executions.any { executionItem ->
                executionItem.status == "FAILED"
            }
        }
    }
}

projectsWithFailedTest.each { projectItem ->
    println "${projectItem.id} - ${projectItem.name}"
}


// 3. Test cases where every execution passed.
println "\n================ EXERCISE 3 ================"

def allTestCases = data.projects
    .collectMany { projectItem -> projectItem.testSuites }
    .collectMany { suiteItem -> suiteItem.testCases }

def testCasesEveryExecutionPassed = allTestCases.findAll { testCaseItem ->

    testCaseItem.executions &&
    testCaseItem.executions.every { executionItem ->
        executionItem.status == "PASSED"
    }

}

testCasesEveryExecutionPassed.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}

// 4. Test cases where any execution retried.
println "\n================ EXERCISE 4 ================"

def testCasesWithRetry = allTestCases.findAll { testCaseItem ->
    testCaseItem.executions.any { executionItem ->
        executionItem.retry > 0
    }
}

testCasesWithRetry.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}


// 5. Teams where any member knows Groovy.
def teamsWithGroovyMember = data.organization.teams.findAll { teamItem ->
    teamItem.members.any { memberItem ->
        memberItem.skills.contains("Groovy")
    }
}

teamsWithGroovyMember.each { teamItem ->
    println "${teamItem.id} - ${teamItem.name}"
}


// 6. Teams where every active member has API experience.
println "\n================ EXERCISE 6 ================"

def teamsWithApiForEveryActiveMember = data.organization.teams.findAll { teamItem ->
    def activeMembers = teamItem.members.findAll { memberItem ->
        memberItem.active == true
    }
    activeMembers &&
    activeMembers.every { memberItem ->
        memberItem.skills.contains("API")
    }
}

teamsWithApiForEveryActiveMember.each { teamItem ->
    println "${teamItem.id} - ${teamItem.name}"
}

// 7. Projects where every environment is enabled.
println "\n================ EXERCISE 7 ================"

def projectsWithAllEnvironmentsEnabled = data.projects.findAll { projectItem ->
    projectItem.environments.every { environmentItem ->
        environmentItem.enabled == true
    }
}

projectsWithAllEnvironmentsEnabled.each { projectItem ->
    println "${projectItem.id} - ${projectItem.name}"
}