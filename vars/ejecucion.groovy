
def call(){
	
pipeline {
    agent any
    
    environment {
	    STAGE = ''
	}

    parameters{
            choice choices: ['gradle', 'maven'], description: 'Indicar la herramienta de construccion', name: 'buildTool'
    }

    stages {
        stage('Pipeline') {
            steps {
                script {
                    STAGE = env.STAGE_NAME
                    println "Stage: ${env.STAGE_NAME}"
                    
                    if (params.buildTool ==  "gradle") {
                        gradle()
                    } else {
                        def irmaven = load 'maven.groovy'
	                maven()
                    }
                }
            }
        }		
    }

    post{
        success{
            slackSend color: 'good', message: "Build Success: [${env.USER}] [${env.JOB_NAME}] [${params.buildTool}] Ejecucion exitosa !! (Revisar en el siguiente link: ${env.BUILD_URL})"



        }
        failure{
            slackSend color: 'danger', message: "Build Failure: [${env.USER}] [${env.JOB_NAME}] [${params.buildTool}][Ejecucion fallida en stage ${STAGE}  (Revisar en el siguiente link: ${env.BUILD_URL})"
			error "Ejecucion fallida en stage ${STAGE}"
        }
    }	
}

}

return this;

