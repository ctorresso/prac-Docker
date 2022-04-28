job('Practica Docker') {
    description('Practica personal de Nodejs-Docker')
    scm {
        git('https://github.com/ctorresso//prac-Docker.git', 'main') { node ->
            node / gitConfigName('ctorresso')
            node / gitConfigEmail('a210216376@unison.mx')
        }
    }
    triggers {
	    cron('H/60 * * * *')
	    githubPush()
    }    
    wrappers {
            nodejs('nodejs')
    }
    steps {
	dockerBuildAndPublish {
	    repositoryName('ctorresso/nodejsapp')
	    tag('${GIT_REVISION,length=7}')
	    registryCredentials('docker-hub')
	    forcePull(false)
	    createFingerprints(false)
	    skipDecorate()
	}	
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	      slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}

