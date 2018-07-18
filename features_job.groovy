import common
use(common) {
    common.projectsWithTag("jenkins-features", GITLAB_JENKINS_TOKEN).each { project ->
        def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')

        multibranchPipelineJob(project_name_canonical + '-features') {
            displayName project.name_with_namespace + ' Features'
            description "Build job for feature/* branches of ${project.path_with_namespace}: ${project.description}"

            triggers {
              periodic 5 // every five minutes, check for branches
            }

            branchSources {
                git {
                    credentialsId 'jenkins-gitlab-id'
                    includes('feature/*')
                    remote project.ssh_url_to_repo
                }
            }

            orphanedItemStrategy {
                discardOldItems {
                    daysToKeep 6
                    numToKeep 10
                }
            }

        } // workflowJob
    } // projectsWithTag.each
} // use(common)
