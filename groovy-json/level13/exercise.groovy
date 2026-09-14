import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

def projectReports = data.projects.collect { projectItem ->

    // Get all test cases of this project
    def testCases = projectItem.testSuites
        .collectMany { suiteItem -> suiteItem.testCases }

    // Get all executions of this project
    def executions = testCases
        .collectMany { testCaseItem -> testCaseItem.executions }

    // Statistics
    def totalTests = testCases.size()

    def automatedTests = testCases.count { testCaseItem ->
        testCaseItem.automated
    }

    def totalExecutions = executions.size()

    def passed = executions.count { executionItem ->
        executionItem.status == "PASSED"
    }

    def failed = executions.count { executionItem ->
        executionItem.status == "FAILED"
    }

    // Rates
    def automationRate = totalTests ?
        (automatedTests * 100.0 / totalTests).round(2) : 0

    def passRate = totalExecutions ?
        (passed * 100.0 / totalExecutions).round(2) : 0

    // Build project report
    [
        id: projectItem.id,
        name: projectItem.name,
        testCases: totalTests,
        automatedTests: automatedTests,
        automationRate: automationRate,
        executions: totalExecutions,
        passed: passed,
        failed: failed,
        passRate: passRate
    ]
}

println projectReports
