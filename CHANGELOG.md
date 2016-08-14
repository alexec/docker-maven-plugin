# Change Log

## [docker-maven-plugin-2.11.17](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.17) (2016-08-13)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.16...docker-maven-plugin-2.11.17)

## [docker-maven-plugin-2.11.16](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.16) (2016-07-24)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.15...docker-maven-plugin-2.11.16)

**Closed issues:**

- Cannot build after force-delete merged [\#104](https://github.com/alexec/docker-maven-plugin/issues/104)

## [docker-maven-plugin-2.11.15](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.15) (2016-07-03)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.14...docker-maven-plugin-2.11.15)

**Merged pull requests:**

- Port to docker-java 3.0.0-SNAPSHOT [\#106](https://github.com/alexec/docker-maven-plugin/pull/106) ([marcust](https://github.com/marcust))
- Clean flag \(fixees \#104\) [\#105](https://github.com/alexec/docker-maven-plugin/pull/105) ([marcust](https://github.com/marcust))

## [docker-maven-plugin-2.11.14](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.14) (2016-06-23)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.13...docker-maven-plugin-2.11.14)

**Fixed bugs:**

- Deletion of Images with Multiple Tags Fails [\#96](https://github.com/alexec/docker-maven-plugin/issues/96)

**Closed issues:**

- small typo in your title... [\#102](https://github.com/alexec/docker-maven-plugin/issues/102)

**Merged pull requests:**

- Make force image delete optional [\#103](https://github.com/alexec/docker-maven-plugin/pull/103) ([marcust](https://github.com/marcust))
- Update readme with notes regarding artifactory [\#99](https://github.com/alexec/docker-maven-plugin/pull/99) ([lorijoan](https://github.com/lorijoan))

## [docker-maven-plugin-2.11.13](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.13) (2016-05-24)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.12...docker-maven-plugin-2.11.13)

**Fixed bugs:**

- Enforcing usage of http instead of http when pushing [\#93](https://github.com/alexec/docker-maven-plugin/issues/93)
- validate goal does not support port type at expose [\#89](https://github.com/alexec/docker-maven-plugin/issues/89)

## [docker-maven-plugin-2.11.12](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.12) (2016-05-24)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.11...docker-maven-plugin-2.11.12)

**Closed issues:**

- Question: Is running multiple containers from the same image supported? [\#97](https://github.com/alexec/docker-maven-plugin/issues/97)

**Merged pull requests:**

- Add logging mojo feature [\#98](https://github.com/alexec/docker-maven-plugin/pull/98) ([marcust](https://github.com/marcust))

## [docker-maven-plugin-2.11.11](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.11) (2016-04-24)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.10...docker-maven-plugin-2.11.11)

## [docker-maven-plugin-2.11.10](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.10) (2016-03-05)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.9...docker-maven-plugin-2.11.10)

**Implemented enhancements:**

- Delete stopped containers after maven completes [\#91](https://github.com/alexec/docker-maven-plugin/issues/91)
- Goals not bound by default to lifecycle phases [\#66](https://github.com/alexec/docker-maven-plugin/issues/66)
- Improvement : docker:tree [\#43](https://github.com/alexec/docker-maven-plugin/issues/43)

**Fixed bugs:**

- volumes does not seem to do anything [\#90](https://github.com/alexec/docker-maven-plugin/issues/90)

**Merged pull requests:**

- Unit tests to increase code coverage from 33% to 86% [\#95](https://github.com/alexec/docker-maven-plugin/pull/95) ([airleks](https://github.com/airleks))
- Unit test samples [\#94](https://github.com/alexec/docker-maven-plugin/pull/94) ([airleks](https://github.com/airleks))

## [docker-maven-plugin-2.11.9](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.9) (2016-01-08)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.7...docker-maven-plugin-2.11.9)

**Merged pull requests:**

- Support docker copy of resources [\#92](https://github.com/alexec/docker-maven-plugin/pull/92) ([andyp1per](https://github.com/andyp1per))

## [docker-maven-plugin-2.11.7](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.7) (2015-12-04)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.6...docker-maven-plugin-2.11.7)

## [docker-maven-plugin-2.11.6](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.6) (2015-12-04)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.5...docker-maven-plugin-2.11.6)

## [docker-maven-plugin-2.11.5](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.5) (2015-11-19)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.4...docker-maven-plugin-2.11.5)

**Merged pull requests:**

- Introduce pull property to force pulling during builds [\#86](https://github.com/alexec/docker-maven-plugin/pull/86) ([marcust](https://github.com/marcust))

## [docker-maven-plugin-2.11.4](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.4) (2015-10-27)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.3...docker-maven-plugin-2.11.4)

**Fixed bugs:**

- Deploy does not always throw on failure [\#78](https://github.com/alexec/docker-maven-plugin/issues/78)

## [docker-maven-plugin-2.11.3](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.3) (2015-10-12)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.2...docker-maven-plugin-2.11.3)

## [docker-maven-plugin-2.11.2](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.2) (2015-10-09)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.1...docker-maven-plugin-2.11.2)

## [docker-maven-plugin-2.11.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.1) (2015-10-08)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.11.0...docker-maven-plugin-2.11.1)

**Merged pull requests:**

- Revert "Revert "Add capability to detect version"" [\#83](https://github.com/alexec/docker-maven-plugin/pull/83) ([alexec](https://github.com/alexec))
- Revert "Add capability to detect version" [\#82](https://github.com/alexec/docker-maven-plugin/pull/82) ([alexec](https://github.com/alexec))
- Add capability to detect version [\#81](https://github.com/alexec/docker-maven-plugin/pull/81) ([marcust](https://github.com/marcust))

## [docker-maven-plugin-2.11.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.11.0) (2015-10-04)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.6...docker-maven-plugin-2.11.0)

## [docker-maven-plugin-2.10.6](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.6) (2015-10-03)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.5...docker-maven-plugin-2.10.6)

**Fixed bugs:**

- Use a third-party image as a build dependency [\#80](https://github.com/alexec/docker-maven-plugin/issues/80)
- Version number for WildFly is not compatible [\#75](https://github.com/alexec/docker-maven-plugin/issues/75)

## [docker-maven-plugin-2.10.5](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.5) (2015-09-26)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.4...docker-maven-plugin-2.10.5)

## [docker-maven-plugin-2.10.4](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.4) (2015-08-25)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.3...docker-maven-plugin-2.10.4)

## [docker-maven-plugin-2.10.3](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.3) (2015-08-14)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.2...docker-maven-plugin-2.10.3)

## [docker-maven-plugin-2.10.2](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.2) (2015-08-13)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.1...docker-maven-plugin-2.10.2)

**Merged pull requests:**

- Update volumes usage sample [\#74](https://github.com/alexec/docker-maven-plugin/pull/74) ([hypery2k](https://github.com/hypery2k))

## [docker-maven-plugin-2.10.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.1) (2015-08-03)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.10.0...docker-maven-plugin-2.10.1)

## [docker-maven-plugin-2.10.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.10.0) (2015-08-02)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.9.3...docker-maven-plugin-2.10.0)

**Merged pull requests:**

- Wait for line [\#73](https://github.com/alexec/docker-maven-plugin/pull/73) ([alexec](https://github.com/alexec))
- Clean container only in clean configuration option [\#72](https://github.com/alexec/docker-maven-plugin/pull/72) ([marcust](https://github.com/marcust))
- Fix broken links & update numbers in "Competitors" [\#71](https://github.com/alexec/docker-maven-plugin/pull/71) ([bentolor](https://github.com/bentolor))

## [docker-maven-plugin-2.9.3](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.9.3) (2015-07-07)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.9.2...docker-maven-plugin-2.9.3)

**Implemented enhancements:**

- run container with   --privileged=false   -  Give extended privileges to this container [\#67](https://github.com/alexec/docker-maven-plugin/issues/67)

**Closed issues:**

- NPE with Docker 1.6.2 on Circle CI [\#70](https://github.com/alexec/docker-maven-plugin/issues/70)
- Release 2.9.1 is not available on Maven Central repos [\#69](https://github.com/alexec/docker-maven-plugin/issues/69)

## [docker-maven-plugin-2.9.2](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.9.2) (2015-06-12)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.9.1...docker-maven-plugin-2.9.2)

**Implemented enhancements:**

- Setting up projects to build from both linux and boot2docker [\#68](https://github.com/alexec/docker-maven-plugin/issues/68)

**Fixed bugs:**

- user properties not filtered in conf.yml [\#62](https://github.com/alexec/docker-maven-plugin/issues/62)
- Push broken [\#61](https://github.com/alexec/docker-maven-plugin/issues/61)
- docker:start failure on windows  [\#54](https://github.com/alexec/docker-maven-plugin/issues/54)

**Closed issues:**

-  Unsupported major.minor version 51.0 [\#65](https://github.com/alexec/docker-maven-plugin/issues/65)

## [docker-maven-plugin-2.9.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.9.1) (2015-05-24)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.9.0...docker-maven-plugin-2.9.1)

## [docker-maven-plugin-2.9.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.9.0) (2015-05-21)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.8.4...docker-maven-plugin-2.9.0)

**Implemented enhancements:**

- The container name should be configurable [\#51](https://github.com/alexec/docker-maven-plugin/issues/51)
- Image build order unknown [\#48](https://github.com/alexec/docker-maven-plugin/issues/48)
-  \* Added support for cfgPath [\#52](https://github.com/alexec/docker-maven-plugin/pull/52) ([eemmiirr](https://github.com/eemmiirr))

**Fixed bugs:**

- Exposed port via conf.yml is not mapping correctly [\#50](https://github.com/alexec/docker-maven-plugin/issues/50)
- Excluded container is still trying to find by id [\#49](https://github.com/alexec/docker-maven-plugin/issues/49)

**Closed issues:**

- Port mapping problem [\#57](https://github.com/alexec/docker-maven-plugin/issues/57)
- Fail to parse conf.yml when env is specified [\#55](https://github.com/alexec/docker-maven-plugin/issues/55)

**Merged pull requests:**

- \[cleanup\] documentation is out of sync with the default values [\#53](https://github.com/alexec/docker-maven-plugin/pull/53) ([dacrome](https://github.com/dacrome))

## [docker-maven-plugin-2.8.4](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.8.4) (2015-04-28)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.8.3...docker-maven-plugin-2.8.4)

## [docker-maven-plugin-2.8.3](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.8.3) (2015-04-28)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.8.2...docker-maven-plugin-2.8.3)

## [docker-maven-plugin-2.8.2](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.8.2) (2015-04-28)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.8.1...docker-maven-plugin-2.8.2)

## [docker-maven-plugin-2.8.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.8.1) (2015-04-27)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.7.0...docker-maven-plugin-2.8.1)

## [docker-maven-plugin-2.7.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.7.0) (2015-04-23)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.6.0...docker-maven-plugin-2.7.0)

## [docker-maven-plugin-2.6.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.6.0) (2015-04-16)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.5.0...docker-maven-plugin-2.6.0)

**Closed issues:**

- Bump docker-java to 1.2.0 [\#47](https://github.com/alexec/docker-maven-plugin/issues/47)

## [docker-maven-plugin-2.5.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.5.0) (2015-04-09)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.4.0...docker-maven-plugin-2.5.0)

**Implemented enhancements:**

- Allow configurable file filter regex to control property replacements in staged files. [\#40](https://github.com/alexec/docker-maven-plugin/issues/40)

**Closed issues:**

- Goal "start" is rebuilding the image  [\#45](https://github.com/alexec/docker-maven-plugin/issues/45)
- Error on OS X with .DS\_Store [\#44](https://github.com/alexec/docker-maven-plugin/issues/44)

**Merged pull requests:**

-  Using new version of docker-java-orchestration [\#46](https://github.com/alexec/docker-maven-plugin/pull/46) ([eemmiirr](https://github.com/eemmiirr))

## [docker-maven-plugin-2.4.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.4.0) (2015-01-29)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.3.1...docker-maven-plugin-2.4.0)

**Implemented enhancements:**

- Running vanilla images & dynamic port mappings [\#25](https://github.com/alexec/docker-maven-plugin/issues/25)

**Fixed bugs:**

- Can not instantiate value of type \[simple type, class com.alexecollins.docker.orchestration.model.Packaging\] from String value \(''\); no single-String constructor/factory method [\#35](https://github.com/alexec/docker-maven-plugin/issues/35)

**Merged pull requests:**

- Allow Docker certificate path to be specified for remote Docker daemon running behind https [\#42](https://github.com/alexec/docker-maven-plugin/pull/42) ([Freaky-namuH](https://github.com/Freaky-namuH))
- Add validate Dockerfile in the docker maven plugin [\#41](https://github.com/alexec/docker-maven-plugin/pull/41) ([Dufgui](https://github.com/Dufgui))

## [docker-maven-plugin-2.3.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.3.1) (2015-01-17)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.3.0...docker-maven-plugin-2.3.1)

## [docker-maven-plugin-2.3.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.3.0) (2014-12-20)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.2.0...docker-maven-plugin-2.3.0)

**Implemented enhancements:**

- Pushing to private registries? [\#28](https://github.com/alexec/docker-maven-plugin/issues/28)

**Fixed bugs:**

- Cannot assign configuration entry 'host' to 'class java.net.URI' [\#34](https://github.com/alexec/docker-maven-plugin/issues/34)

**Closed issues:**

- Expose container ip address as maven properties [\#37](https://github.com/alexec/docker-maven-plugin/issues/37)
- Unfiltered file URI used when building image [\#33](https://github.com/alexec/docker-maven-plugin/issues/33)

**Merged pull requests:**

- expose ip address as maven properties [\#38](https://github.com/alexec/docker-maven-plugin/pull/38) ([changgengli](https://github.com/changgengli))
- Update USAGE.md [\#36](https://github.com/alexec/docker-maven-plugin/pull/36) ([oadam](https://github.com/oadam))

## [docker-maven-plugin-2.2.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.2.0) (2014-11-16)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.1.0...docker-maven-plugin-2.2.0)

**Fixed bugs:**

- Multipart upload for build is no longer supported [\#16](https://github.com/alexec/docker-maven-plugin/issues/16)

**Closed issues:**

- request: support use of $DOCKER\_HOST environment variable [\#31](https://github.com/alexec/docker-maven-plugin/issues/31)
- client and server don't have same version \(client : 2.1, server: 1.14\) [\#29](https://github.com/alexec/docker-maven-plugin/issues/29)

## [docker-maven-plugin-2.1.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.1.0) (2014-10-14)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.0.2...docker-maven-plugin-2.1.0)

**Fixed bugs:**

- docker:clean does not remove image [\#14](https://github.com/alexec/docker-maven-plugin/issues/14)

**Closed issues:**

- Alias not possible when linking container [\#26](https://github.com/alexec/docker-maven-plugin/issues/26)
- Docker Image doesn't get tagged properly [\#23](https://github.com/alexec/docker-maven-plugin/issues/23)

## [docker-maven-plugin-2.0.2](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.0.2) (2014-10-13)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.0.1...docker-maven-plugin-2.0.2)

## [docker-maven-plugin-2.0.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.0.1) (2014-10-13)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-2.0.0...docker-maven-plugin-2.0.1)

## [docker-maven-plugin-2.0.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-2.0.0) (2014-09-20)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-1.4.0...docker-maven-plugin-2.0.0)

**Implemented enhancements:**

- Support disabling of cache during build  [\#15](https://github.com/alexec/docker-maven-plugin/issues/15)

**Fixed bugs:**

- Tagging problems [\#18](https://github.com/alexec/docker-maven-plugin/issues/18)

**Closed issues:**

- recommended to use docker-java [\#22](https://github.com/alexec/docker-maven-plugin/issues/22)
- NPE when running 'mvn -Prun-its verify` [\#17](https://github.com/alexec/docker-maven-plugin/issues/17)

**Merged pull requests:**

- Fixed typos in README.md [\#20](https://github.com/alexec/docker-maven-plugin/pull/20) ([pnuz3n](https://github.com/pnuz3n))

## [docker-maven-plugin-1.4.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-1.4.0) (2014-08-02)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-1.3.1...docker-maven-plugin-1.4.0)

**Fixed bugs:**

- Binary garbage on standard output of docker:install under Maven 3 [\#13](https://github.com/alexec/docker-maven-plugin/issues/13)

## [docker-maven-plugin-1.3.1](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-1.3.1) (2014-07-29)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-1.3.0...docker-maven-plugin-1.3.1)

**Closed issues:**

- tagging images [\#10](https://github.com/alexec/docker-maven-plugin/issues/10)
- Unrecognized field "Paused" [\#9](https://github.com/alexec/docker-maven-plugin/issues/9)

## [docker-maven-plugin-1.3.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-1.3.0) (2014-07-16)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-1.1.0...docker-maven-plugin-1.3.0)

**Implemented enhancements:**

- Add parameter to control removal of intermediate containers when building an image [\#8](https://github.com/alexec/docker-maven-plugin/issues/8)

**Fixed bugs:**

- healthCheck --\> healthChecks and ping --\> pings [\#11](https://github.com/alexec/docker-maven-plugin/issues/11)

**Closed issues:**

- Include a "skip" config option [\#12](https://github.com/alexec/docker-maven-plugin/issues/12)
- Logging output during build [\#6](https://github.com/alexec/docker-maven-plugin/issues/6)

**Merged pull requests:**

- File copy uses working directory [\#7](https://github.com/alexec/docker-maven-plugin/pull/7) ([oillio](https://github.com/oillio))

## [docker-maven-plugin-1.1.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-1.1.0) (2014-06-01)
[Full Changelog](https://github.com/alexec/docker-maven-plugin/compare/docker-maven-plugin-1.0.0...docker-maven-plugin-1.1.0)

**Closed issues:**

- Deploy to Maven central [\#3](https://github.com/alexec/docker-maven-plugin/issues/3)

## [docker-maven-plugin-1.0.0](https://github.com/alexec/docker-maven-plugin/tree/docker-maven-plugin-1.0.0) (2014-05-24)
**Merged pull requests:**

- Fix up logging to prevent logging of binary files [\#5](https://github.com/alexec/docker-maven-plugin/pull/5) ([lhallowes](https://github.com/lhallowes))
- Parameterise the source folder location [\#4](https://github.com/alexec/docker-maven-plugin/pull/4) ([vlfig](https://github.com/vlfig))
- New copy directory to Directory logic + using Alexec's versions of docker-java [\#2](https://github.com/alexec/docker-maven-plugin/pull/2) ([djsly](https://github.com/djsly))
- Minor typos and Trivial Example [\#1](https://github.com/alexec/docker-maven-plugin/pull/1) ([alrighttheresham](https://github.com/alrighttheresham))



\* *This Change Log was automatically generated by [github_changelog_generator](https://github.com/skywinder/Github-Changelog-Generator)*