import javaposse.jobdsl.dsl.AbstractContext
import javaposse.jobdsl.dsl.helpers.*

class common {
    static {
        // Extend the scm{} DSL context with extra commands
        // https://github.com/jenkinsci/job-dsl-plugin/wiki/Extending-the-DSL
        ScmContext.metaClass.customGit { String url, String branch, Closure configure = null ->
            git {
                delegate.remote {
                    delegate.url(url)
                    delegate.credentials('jenkins-gitlab-id')
                }
                if (branch) {
                    delegate.branch(branch)
                }
                if (configure) {
                    delegate.configure(configure)
                }
            }
        }
        ScmContext.metaClass.customGit << { String url ->
            customGit(url, null)
        }
    }

    // Use the GitLab API to find a specific project based on group & name
    static Object gitlabProject( String groupName, String projectName, String token, String gitlabApiURL = 'https://gitlab.example.com/api/v3' ) {
      def groupsUrl = "${gitlabApiURL}/groups?search=${groupName}&private_token=${token}"
      def foundGroup = new URL(groupsUrl).openConnection().getContent().newReader()
      def group = new groovy.json.JsonSlurper().parse(foundGroup)
      if (group.id) {
        def projectsUrl = "${gitlabApiURL}/groups/${group.id[0]}/projects?search=${projectName}&private_token=${token}"
        def foundProject = new URL(projectsUrl).openConnection().getContent().newReader()
        def project = new groovy.json.JsonSlurper().parse(foundProject)
        return project
      }
      return []
    }

    //
    // Use the GitLab API to query all projects that have a tag in their tag_list
    //
    static List<Object> projectsWithTag( String tagName, String token, String gitlabApiURL = 'https://git.example.com/api/v3' ) {
        def projectsUrl = "${gitlabApiURL}/projects/all?private_token=${token}&per_page=20"
        def projectSearch = new URL(projectsUrl)
        def connection = projectSearch.openConnection()

        def projectResults = new groovy.json.JsonSlurper().parse(connection.getContent().newReader())

        def totalPages = connection.getHeaderFields()["X-Total-Pages"][0].toInteger()

        for (def page = 2; page <= totalPages; page++) {
          connection = new URL(projectsUrl+"&page=${page}").openConnection()
          def moreResults = new groovy.json.JsonSlurper().parse(connection.getContent().newReader())
          projectResults += moreResults
        }

        return projectResults.findAll { project -> tagName in project.tag_list }
    }
}
