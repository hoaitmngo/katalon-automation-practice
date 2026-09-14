
import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

println "\n================ EXERCISE 0 ================"

def projectNames = data.projects.collect { projectItem ->
    projectItem.name
}

println projectNames


println "\n================ EXERCISE 1 ================"

def projectIdNames = data.projects.collect { projectItem ->
    "${projectItem.id}: ${projectItem.name}"
}

println projectIdNames


println "\n================ EXERCISE 1 ================"

def projectIds = data.projects.collect { projectItem ->
    projectItem.id
}

println projectIds


println "\n================ EXERCISE 2 ================"

def memberNames = data.organization.teams
        .collectMany { teamItem ->
            teamItem.members
        }
        .collect { memberItem ->
            memberItem.name
        }

println memberNames

// Define variables
def allSuites = data.projects.collectMany { projectItem ->
    projectItem.testSuites
}

def allTestCases = allSuites.collectMany { suiteItem ->
    suiteItem.testCases
}


println "\n================ EXERCISE 3 ================"

def testCaseIds = allTestCases.collect { testCaseItem ->
    testCaseItem.id
}

println testCaseIds


println "\n================ EXERCISE 4 ================"

def automatedTestNames = allTestCases
        .findAll { testCaseItem ->
            testCaseItem.automated
        }
        .collect { testCaseItem ->
            testCaseItem.name
        }

println automatedTestNames

println "\n================ EXERCISE 5 ================"


def apiTestCases = allTestCases.findAll { testCaseItem ->
    testCaseItem.api != null
}

def apiEndpoints = apiTestCases.collect { testCaseItem ->
    testCaseItem.api.endpoint
}

println apiEndpoints

println "\n========== 6. PROJECT SUMMARIES =========="

def projectSummaries = data.projects.collect { projectItem ->

    [
        id    : projectItem.id,
        name  : projectItem.name,
        budget: projectItem.budget
    ]
}

projectSummaries.each { summaryItem ->
    println summaryItem
}

println "\n================ EXERCISE 7 ================"

def testCaseSummaries = data.projects.collectMany { projectItem ->

    projectItem.testSuites.collectMany { suiteItem ->

        suiteItem.testCases.collect { testCaseItem ->

            [
                testCase : testCaseItem.id,
                project  : projectItem.name,
                automated: testCaseItem.automated
            ]
        }
    }
}

testCaseSummaries.each { summaryItem ->
    println summaryItem
}