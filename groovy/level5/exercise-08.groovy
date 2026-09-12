boolean isSuccessful(int statusCode) {

    if (statusCode >= 200 && statusCode <= 299) {
        return true
    } else {
        return false
    }
}


String classifyResponseTime(long milliseconds) {

    if (milliseconds < 500) {
        return "FAST"
    } else if (milliseconds <= 1000) {
        return "ACCEPTABLE"
    } else {
        return "SLOW"
    }
}


// Test isSuccessful()
assert isSuccessful(200)
assert isSuccessful(201)
assert !isSuccessful(400)
assert !isSuccessful(500)


// Test classifyResponseTime()
assert classifyResponseTime(300) == "FAST"
assert classifyResponseTime(800) == "ACCEPTABLE"
assert classifyResponseTime(1500) == "SLOW"