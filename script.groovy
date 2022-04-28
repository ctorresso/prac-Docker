job('Practica Docker') {
    description('Practica personal de Nodejs-Docker')
    scm {
        git('https://github.com/ctorresso//prac-Docker.git', 'master') { node ->
            node / gitConfigName('ctorresso')
            node / gitConfigEmail('a210216376@unison.mx')
        }
    }
    triggers {
	    cron('H/60 * * * *')
	    githubPush()
    }    
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell(
          echo "Hola mundo" 
          )  
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

