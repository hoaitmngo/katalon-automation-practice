import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def teams = data.organization.teams

def allMembers = teams
    .collectMany { teamItem ->
        teamItem.members
    }

def projects = data.projects

def allSuites = projects
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

// Statistics

def totalTeams = teams.size()

def totalProjects = projects.size()

def totalSuites = allSuites.size()

def totalTestCases = allTestCases.size()

def automatedTests = allTestCases.count { testCaseItem ->
    testCaseItem.automated
}

def totalExecutions = allExecutions.size()

// Quality statistics

def passed = allExecutions.count { executionItem ->
    executionItem.status == "PASSED"
}

def failed = allExecutions.count { executionItem ->
    executionItem.status == "FAILED"
}

def skipped = allExecutions.count { executionItem ->
    executionItem.status == "SKIPPED"
}

def passRate = totalExecutions ?
    (passed * 100.0 / totalExecutions).round(2) :
    0

def automationRate = totalTestCases ?
    (automatedTests * 100.0 / totalTestCases).round(2) :
    0

// Top risks

def topRisks = projects
    .collect { projectItem ->

        def projectTestCases = projectItem.testSuites
            .collectMany { suiteItem ->
                suiteItem.testCases
            }

        def projectFailedTests = projectTestCases.count { testCaseItem ->
            testCaseItem.executions.any { executionItem ->
                executionItem.status == "FAILED"
            }
        }

        [
            project: projectItem.name,
            failedTests: projectFailedTests,
            priority: projectItem.priority
        ]
    }
    .findAll { riskItem ->
        riskItem.failedTests > 0
    }
    .sort { riskItem ->
        -riskItem.failedTests
    }

// Failure components

def failureComponents = allExecutions
    .findAll { executionItem ->
        executionItem.status == "FAILED"
    }
    .groupBy { executionItem ->
        executionItem.failure?.component
    }
    .collectEntries { component, failureList ->
        [(component): failureList.size()]
    }


// Engineer workload
def engineers = allMembers
    .collect { memberItem ->
        def ownedTests = allTestCases.count { testCaseItem ->
            testCaseItem.owner == memberItem.id
        }
        [
            name: memberItem.name,
            ownedTests: ownedTests
        ]
    }
    .findAll { engineerItem ->
        engineerItem.ownedTests > 0
    }
    .sort { engineerItem ->
        -engineerItem.ownedTests
    }

// Build QA Dashboard

def qaDashboard = [
    organization: data.organization.name,
    statistics: [
        teams: totalTeams,
        projects: totalProjects,
        testSuites: totalSuites,
        testCases: totalTestCases,
        automatedTests: automatedTests,
        executions: totalExecutions
    ],
    quality: [
        passed: passed,
        failed: failed,
        skipped: skipped,
        passRate: passRate,
        automationRate: automationRate
    ],
    topRisks: topRisks,
    failureComponents: failureComponents,
    engineers: engineers
]

// Print dashboard

println "========================================"
println "             QA DASHBOARD"
println "========================================"

println qaDashboard
