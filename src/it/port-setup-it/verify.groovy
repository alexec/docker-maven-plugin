def actualPos = "docker ps".execute().text
def expectedSubstring = "0.0.0.0:8888->3306/tcp"

assert actualPos.contains(expectedSubstring), "\"" + actualPos + "\" contains \"" + expectedSubstring + "\""