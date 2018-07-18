import common
use(common) {
    job('generator') {
        using 'tmpl_base'
        disabled false

        displayName 'Job Generator'
        description 'Generator of Jenkins Jobs'

        label 'master'

        scm {
          git {
            remote {
              credentials 'jenkins-gitlab-id'
              url 'git.example.com:infra/jenkins-jobs-generator.git'
            }
            browser {
              gitLab 'https://git.example.com/infra/jenkins-jobs-generator', '8.10'
            }
          }
        }

        triggers {
            scm('* * * * *') // yes, every minute.
            gitlabPush()
        }

        steps {
            dsl(['**/*_view.groovy', '**/*_job.groovy'])
        }

    }
}
