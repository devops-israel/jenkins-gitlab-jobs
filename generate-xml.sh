#!/bin/sh

# Set an environment variable GITLAB_JENKINS_TOKEN before starting this script.
# Get your token from GitLab at https://git.example.com/profile/account
# Use:
#   export GITLAB_JENKINS_TOKEN=your_token_here
#
# DO NOT CHANGE THIS LINE! Read above.
export GITLAB_JENKINS_TOKEN="${GITLAB_JENKINS_TOKEN:=xxx_set_a_proper_gitlab_token_xxx}"

mkdir -p `dirname $0`/xml
cd `dirname $0`/xml
ln -fs ../common.groovy .
java -jar ../job-dsl-core.jar ../a_template_job.groovy ../*_job.groovy ../*_view.groovy
