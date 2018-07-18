import common
use(common) {
    common.projectsWithTag("jenkins-sonarqube", GITLAB_JENKINS_TOKEN).each { project ->
        def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')

        job(project_name_canonical + '-sonarqube') {
          using 'tmpl_base_sonarqube'
          disabled false
          displayName project.name_with_namespace + ' SonarQube'
          description "Build job for SonarQube of ${project.path_with_namespace}: ${project.description}"
          scm {
              git {
                  remote {
                    credentials 'jenkins-gitlab-id'
                    url project.ssh_url_to_repo
                    branch project.default_branch
                  } // remote
                  browser {
                    gitLab project.web_url, '8.10'
                  } // browser
              } // git
          } // scm

          wrappers {
              preBuildCleanup()
          }

          steps {
            copyArtifacts(project_name_canonical) {
                buildSelector {
                    upstreamBuild {
                        fallbackToLastSuccessful false
                    }
                }
            }
            shell 'mkdir -p build ; cd build ; unzip -o ../test-results.zip'
            shell './gradlew sonarqube -x test -Dsonar.host.url=$SONAR_HOST -Dsonar.scm.provider=git'
          } // steps
        }
    } // projectsWithTag.each
} // use(common)

