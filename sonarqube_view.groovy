import common
listView("SonarQube") {
    description "Show progress of SonarQube jobs"
    filterBuildQueue true
    jobs {
        regex '.*sonarqube$'
        // alternatively also:
        // common.projectsWithTag("jenkins-sonarqube", GITLAB_JENKINS_TOKEN).each { project ->
        //     def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')
        //     name project_name_canonical
        // }
    }
    columns {
        status()
        weather()
        name()
        lastDuration()
        lastSuccess()
        lastFailure()
        // no need for this: buildButton()
    }
}
