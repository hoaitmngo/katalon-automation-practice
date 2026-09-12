def users = [
    clientA: [username: "userA", password: "passA"],
    clientB: [username: "userB", password: "passB"],
    clientC: [username: "userC", password: "passC"]
]

def client = "clientB"

def user = users[client]

if (user != null) {
    println(user.username)
} else {
    println("Client not found: $client")
}