import groovy.json.JsonSlurper

def data = new JsonSlurper().parse(
    new File("groovy-json/data.json")
)

// 1. Organization name
println "\n================ EXERCISE 1 ================"

println data.organization.name


// 2. Every team name
println "\n================ EXERCISE 2 ================"

data.organization.teams.each { team ->
    println team.name
}


// 3. Every project name
println "\n================ EXERCISE 3 ================"

data.projects.each { project ->
    println project.name
}


// 4. Environments of PRJ-002
println "\n================ EXERCISE 4 ================"

def project = data.projects.find {
    it.id == "PRJ-002"
}

project.environments.each { environment ->
    println environment.name
}


// 5. All test suite names
println "\n================ EXERCISE 5 ================"

data.projects.each { projectItem ->
    projectItem.testSuites.each { suite ->
        println suite.name
    }
}


// 6. Every test case ID and name
println "\n================ EXERCISE 6 ================"

data.projects.each { projectItem ->
    projectItem.testSuites.each { suite ->
        suite.testCases.each { testCase ->
            println "${testCase.id} - ${testCase.name}"
        }
    }
}



// 7. Ruby team members
println "\n================ EXERCISE 7 ================"

def rubyTeam = data.organization.teams.find {
    it.name == "Ruby"
}

rubyTeam.members.each { member ->
    println member.name
}


// 8. Alice's skills
println "\n================ EXERCISE 8 ================"

def alice = data.organization.teams
    .collect { it.lead }
    .find { it.name == "Alice Nguyen" }

alice.skills.each { skill ->
    println skill
}


// 9. Number of projects
println "\n================ EXERCISE 9 ================"

println data.projects.size()


// 10. Number of test cases
println "\n================ EXERCISE 10 ================"

def allTestCases = data.projects.collectMany { projectItem ->
    projectItem.testSuites.collectMany { suite ->
        suite.testCases
    }
}

println allTestCases.size()