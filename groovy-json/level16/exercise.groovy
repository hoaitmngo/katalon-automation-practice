import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def result =
    data.projects
        .collectMany { projectItem ->
            projectItem.testSuites
        }
        .collectMany { suiteItem ->
            suiteItem.testCases
        }
        .findAll { testCaseItem ->

            testCaseItem.automated &&
            testCaseItem.priority == "P1" &&
            testCaseItem.executions.any { executionItem ->
                executionItem.status == "FAILED"
            }
        }
        .collect { testCaseItem ->
            testCaseItem.name
        }
        .sort()

println result
