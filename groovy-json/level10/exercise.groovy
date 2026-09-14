import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

println "\n================ EXERCISE 1 ================"

def projectMap = data.projects.collectEntries {
    [(it.id): it.name]
}

println projectMap

println "\n================ EXERCISE 2 ================"

def allTestCases = data.projects
    .collectMany { projectItem -> projectItem.testSuites }
    .collectMany { suiteItem -> suiteItem.testCases }

def testCaseMap = allTestCases.collectEntries {
    [(it.id): it.name]
}

println testCaseMap
