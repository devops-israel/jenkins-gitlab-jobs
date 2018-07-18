#!groovy

node('master') {
    stage 'checkout'
      checkout scm

    stage 'job-dsl'
      withCredentials([
        [$class: 'StringBinding', credentialsId: 'gitlab-api-token', variable: 'GITLAB_JENKINS_TOKEN']
      ]) {
// BUG: environment variable for GITLAB_JENKINS_TOKEN doesn't work with jobDsl 1.50 pipeline step
        jobDsl targets: [ '**/*_job.groovy', '**/*_view.groovy' ].join('\n'),
               removedJobAction: 'DELETE',
               removedViewAction: 'DELETE',
               lookupStrategy: 'JENKINS_ROOT'
      }
}
