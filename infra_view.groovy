import common
listView("Infra Projects") {
    description "Show a list of INFRA project jobs"
    filterBuildQueue true
    jobs {
        common.projectsWithTag("jenkins", GITLAB_JENKINS_TOKEN).each { project ->
            def project_name_canonical = project.path_with_namespace.replaceAll('/', '-')
            if (!(project_name_canonical ==~ /^infra.*/)) {
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

