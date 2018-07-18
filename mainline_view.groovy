import common
listView("Mainline Branches") {
    description "Show progress of mainline branch jobs"
    filterBuildQueue true
    jobs {
        common.projectsWithTag("jenkins", GITLAB_JENKINS_TOKEN).each { project ->
            def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')
            if (project_name_canonical ==~ /^infra.*/) {
              return
            }
            name project_name_canonical
        }
    }
    columns {
        status()
        weather()
        name()
        lastDuration()
        lastSuccess()
        lastFailure()
        buildButton()
    }
}
