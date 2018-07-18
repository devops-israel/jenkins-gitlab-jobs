job('tmpl_base') {
  disabled true
  displayName 'Template: Base'
  description 'Template used for all other templates and projects configured using JobDSL'

  logRotator(-1, 10, -1, -1)
  compressBuildLog()

  wrappers {
    timestamps()
    colorizeOutput()
  }
}

job('tmpl_base_sonarqube') {
  using 'tmpl_base'
  displayName 'Template: SonarQube'
  description 'Template used for all other SonarQube projects configured using JobDSL'

  blockOnUpstreamProjects()
  concurrentBuild false
}

//** ... using 'tmpl_base_pipeline' - causes NullPointer exception in Jenkins only (not CLI)
// workflowJob('tmpl_base_pipeline') {
//   disabled true
//   displayName 'Template: Base Pipeline'
//   description 'Template used for all other templates and projects configured using JobDsl'
// 
//   logRotator(-1, 10, -1, -1)
// 
//   wrappers {
//     timestamps()
//     colorizeOutput()
//   }
// }
