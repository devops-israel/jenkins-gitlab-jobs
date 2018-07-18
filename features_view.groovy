listView("Feature Branches") {
    description "Show progress of Feature branch jobs"
    filterBuildQueue true
    jobs {
        regex '.*features'
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
