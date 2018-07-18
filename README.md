# Jenkins Job DSL

All the Jenkins Job definitions in one place.

Language reference: https://jenkinsci.github.io/job-dsl-plugin/

Visit the Jenkins server for the current API methods supported by the installed
Plugin version:

    http://<jenkins_server>/plugin/job-dsl/api-viewer

# Jenkins Jobs

## Generating jobs locally

* clone https://github.com/jenkinsci/job-dsl-plugin.git

* follow steps described at
  https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves#run-a-dsl-script-locally
  ( tl;dr: ./gradlew :job-dsl-core:oneJar && find job-dsl-core -name \*standalone.jar )

* move job-dsl-core/build/libs/job-dsl-core-1.xx-SNAPSHOT-standalone.jar
  to job-dsl-core.jar

* test running the standalone jar using:
  java -jar job-dsl-core.jar

Job-DSL is using Groovy, learn the basics at
- https://learnxinyminutes.com/docs/groovy/

Job-DSL documentation & reference
https://github.com/jenkinsci/job-dsl-plugin/wiki - documentation
https://jenkinsci.github.io/job-dsl-plugin/ - reference
