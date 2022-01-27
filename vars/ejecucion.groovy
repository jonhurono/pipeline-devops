/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

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

                    println "Pipeline"
                    
                    if (params.buildTool ==  "gradle") {
                        def ejecucion = load 'gradle.groovy'
	                    ejecucion.call()
                    } else {
                        def ejecucion = load 'maven.groovy'
	                    ejecucion.call()
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
