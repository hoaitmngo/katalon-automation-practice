def responseTime = 1250

if (responseTime < 500) {
    println("FAST")
} else if (responseTime <= 1000) {
    println("ACCEPTABLE")
} else {
    println("SLOW")
}