import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// Define variables

// All test suites
def allSuites = data.projects.collectMany { projectItem ->
    projectItem.testSuites
}


// All test cases
def allTestCases = allSuites.collectMany { suiteItem ->
    suiteItem.testCases
}


// All environments
def allEnvironments = data.projects.collectMany { projectItem ->
    projectItem.environments
}


// All team members
def allMembers = data.organization.teams.collectMany { teamItem ->
    teamItem.members
}

// Solution

println "\n================ EXERCISE 1 ================"

def activeProjects = data.projects.findAll { projectItem ->
    projectItem.status == "ACTIVE"
}

activeProjects.each { projectItem ->
    println projectItem.name
}


// 2. Projects with budget > 100000

println "\n================ EXERCISE 2 ================"

def expensiveProjects = data.projects.findAll { projectItem ->
    projectItem.budget > 100000
}

expensiveProjects.each { projectItem ->
    println "${projectItem.name} - ${projectItem.budget}"
}


println "\n================ EXERCISE 3 ================"

def p1TestCases = allTestCases.findAll { testCaseItem ->
    testCaseItem.priority == "P1"
}

p1TestCases.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}


println "\n================ EXERCISE 4 ================"


def automatedTests = allTestCases.findAll { testCaseItem ->
    testCaseItem.automated == true
}

automatedTests.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}


println "\n================ EXERCISE 5 ================"

def manualTests = allTestCases.findAll { testCaseItem ->
    testCaseItem.automated == false
}

manualTests.each { testCaseItem ->
    println "${testCaseItem.id} - ${testCaseItem.name}"
}

println "\n================ EXERCISE 6 ================"

def apiSuites = allSuites.findAll { suiteItem ->
    suiteItem.type == "API"
}

apiSuites.each { suiteItem ->
    println "${suiteItem.id} - ${suiteItem.name}"
}

println "\n================ EXERCISE 7  ================"

def enabledEnvironments = allEnvironments.findAll { environmentItem ->
    environmentItem.enabled == true
}

enabledEnvironments.each { environmentItem ->
    println "${environmentItem.name} - ${environmentItem.url}"
}

println "\n================ EXERCISE 8 ================"

def activeMembers = allMembers.findAll { memberItem ->
    memberItem.active == true
}

activeMembers.each { memberItem ->
    println "${memberItem.id} - ${memberItem.name}"
}

println "\n================ EXERCISE 9 ================"

def experiencedEngineers = allMembers.findAll { memberItem ->
    memberItem.experienceYears >= 5
}

experiencedEngineers.each { memberItem ->
    println "${memberItem.name} - ${memberItem.experienceYears} years"
}

println "\n================ EXERCISE 10 ================"

def groovyMembers = allMembers.findAll { memberItem ->
    memberItem.skills.contains("Groovy")
}

groovyMembers.each { memberItem ->
    println "${memberItem.name}"
}