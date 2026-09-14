import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// Generate failure report

def failureReport = data.projects
    .collectMany { projectItem ->
        projectItem.testSuites.collectMany { suiteItem ->
            suiteItem.testCases.collectMany { testCaseItem ->
                testCaseItem.executions
                    .findAll { executionItem ->
                        executionItem.status == "FAILED"
                    }
                    .collect { executionItem ->
                        [
                            testCase: testCaseItem.id,
                            project: projectItem.name,
                            environment: executionItem.environment,
                            failureType: executionItem.failure?.type,
                            component: executionItem.failure?.component,
                            retry: executionItem.retry
                        ]
                    }
            }
        }
    }


// Print failure report

println "========== FAILURE REPORT =========="

failureReport.each { failureItem ->
    println failureItem
}

// Group failures by component

def failuresByComponent = failureReport.groupBy { failureItem ->
    failureItem.component
}


println "\n========== FAILURES BY COMPONENT =========="

failuresByComponent.each { component, failures ->
    println "${component}: ${failures.size()} failure(s)"
    failures.each { failureItem ->
        println "  ${failureItem.testCase} - ${failureItem.failureType}"
    }
}

// Find component with most failures

def componentWithMostFailures = failuresByComponent.max { entry ->
    entry.value.size()
}

// Print component with most failures

println "\n========== TOP FAILURE COMPONENT =========="

println "Component: ${componentWithMostFailures.key}"
println "Failure count: ${componentWithMostFailures.value.size()}"
