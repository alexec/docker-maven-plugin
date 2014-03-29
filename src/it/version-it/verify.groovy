def path = new File(basedir, "build.log");

path.eachLine { String l ->
    if (l.matches(~/"Version"/)) {
        exit
    }
}

exit 1