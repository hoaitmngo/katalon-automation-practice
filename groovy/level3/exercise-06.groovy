def testCases = [
    [name: "Login",    type: "UI",  priority: "High"],
    [name: "Get User", type: "API", priority: "High"],
    [name: "Search",   type: "UI",  priority: "Low"],
    [name: "Payment",  type: "API", priority: "High"]
]

def filteredTests = testCases.findAll {
    it.type == "API" && it.priority == "High"
}

filteredTests.each {
    println(it.name)
}