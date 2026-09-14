import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// Define variables

def allTestCases = data.projects
    .collectMany { projectItem ->
        projectItem.testSuites
    }
    .collectMany { suiteItem ->
        suiteItem.testCases
    }


def allUsers = data.organization.teams
    .collectMany { teamItem ->
        teamItem.members
    }


def workloadReport = allUsers
    .collect { userItem ->

        // Only automated tests
        def ownedTests = allTestCases.findAll { testCaseItem ->
            testCaseItem.owner == userItem.id &&
            testCaseItem.automated == true
        }

        // Executions performed by this user
        def executionsPerformed = allTestCases
            .collectMany { testCaseItem ->
                testCaseItem.executions
            }
            .findAll { executionItem ->
                executionItem.executedBy == userItem.id
            }

        // Failed executions
        def failedExecutions = executionsPerformed.count { executionItem ->
            executionItem.status == "FAILED"
        }

        // Average execution duration
        def averageExecutionDuration = executionsPerformed ?
            (
                executionsPerformed.sum { executionItem ->
                    executionItem.durationSeconds
                } / executionsPerformed.size()
            ).round(2) :
            0

        [
            userId: userItem.id,
            name: userItem.name,
            ownedTests: ownedTests.size(),
            executionsPerformed: executionsPerformed.size(),
            failedExecutions: failedExecutions,
            averageExecutionDuration: averageExecutionDuration
        ]
    }
    .findAll { workloadItem ->
        workloadItem.ownedTests > 0
    }


workloadReport.each { workloadItem ->
    println workloadItem
}
