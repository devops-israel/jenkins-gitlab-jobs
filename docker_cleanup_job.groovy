import common
use(common) {
    job('docker-cleaner') {
        using 'tmpl_base'
        disabled false

        displayName 'Docker Cleaner'
        description 'Periodically clean stopped containers and untagged images'

        triggers {
            cron('H * * * *') // yes, every hour, but fizzy (not on minute 0)
        }

        steps {
            shell '''set -e
                    |existed_containers=$(docker ps -aq --filter status=exited)
                    |[ -n "$existed_containers" ] && docker rm $exited_containers
                    |dangling_images=$(docker images -q --filter dangling=true)
                    |[ -n "$dangling_images" ] && docker rmi $dangling_images
                    |'''.stripMargin()
        }

    }
}
