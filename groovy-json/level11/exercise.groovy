import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def allTestCases = data.projects.collectMany { projectItem ->
    projectItem.testSuites.collectMany { suite ->
        suite.testCases
    }
}

def result = allTestCases.findAll { testCaseItem ->

    testCaseItem.automated &&
    testCaseItem.priority == "P1" &&
    testCaseItem.executions &&
    testCaseItem.executions.any { executionItem ->
        executionItem.status == "PASSED"
    } &&
    testCaseItem.estimatedMinutes <= 6

}

result.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}