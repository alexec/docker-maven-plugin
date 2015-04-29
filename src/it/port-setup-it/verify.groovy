
def ps =  "docker ps".execute().text

println "docker ps:"
println ps

assert ps.contains("0.0.0.0:8888->3306/tcp")