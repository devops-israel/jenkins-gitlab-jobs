import common
use(common) {
    common.projectsWithTag("jenkins", GITLAB_JENKINS_TOKEN).each { project ->
        def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')

        pipelineJob(project_name_canonical) {
            // using 'tmpl_base_pipeline' - causes NullPointer exception in Jenkins only (not CLI)
            disabled false

            displayName project.name_with_namespace
            description "Build job for ${project.path_with_namespace}: ${project.description}"
            customWorkspace project.path_with_namespace

            triggers {
              gitlabPush()
            }

            definition {
                cpsScm {
                    scriptPath "Jenkinsfile"
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
                } // cpsScm
            } // definition
        } // workflowJob
    } // projectsWithTag.each
} // use(common)
