def actualPs = new File(basedir, "docker.ps").text
def expectedSubstring = "0.0.0.0:8888->3306/tcp"

println actualPs

assert actualPs.contains(expectedSubstring), "\"" + actualPs + "\" contains \"" + expectedSubstring + "\""