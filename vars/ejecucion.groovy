
def call(){
	
pipeline {
    agent any
    
    environment {
	    STAGE = ''
	}

    parameters{
            choice choices: ['gradle', 'maven'], description: 'Indicar la herramienta de construccion', name: 'buildTool', string(name: 'STAGE', defaultValue: "")
    }

    stages {
        stage('Pipeline') {
            steps {
                script {
                    env.STAGE_NAME = null
                    env.PSTAGE = null

                    if(params.stage.length() == 0){
                        println "Todo"
                        env.PSTAGE = "Todo"

                        if(params.buildTool == "gradle"){
                            gradle()
                        }
                        else{
                            maven()
                        }
                    }

                    else{
                        println "Selectivo"
                        def stages = params.stage.split(";")
                        for(i=0;i<stages.size();i++){
                            env.STAGE = null
                            env.PSTAGE = stages [i]
                            if(params.buildTool == "gradle"){
                                gradle();
                            }
                            else{
                                maven();
                            }
                            if(env.STAGE == null){
                                break;
                            }
                        }
                    }
                }
            }
        }		
    }

    post{
        success{
            script{
                if(env.STAGE == null && env.PSTAGE != 'Todo'){
                    slackSend color: 'danger', message: "Build Failure: [${env.USER}] [${env.JOB_NAME}] [${params.buildTool}][Ejecucion fallida en stage ${PSTAGE}  (Revisar en el siguiente link: ${env.BUILD_URL})"
			error "Ejecucion fallida en stage ${PSTAGE}"
                }
                else{
                    slackSend color: 'good', message: "Build Success: [${env.USER}] [${env.JOB_NAME}] [${params.buildTool}] Ejecucion exitosa !! (Revisar en el siguiente link: ${env.BUILD_URL})"
                }
            }
        }
        failure{
            slackSend color: 'danger', message: "Build Failure: [${env.USER}] [${env.JOB_NAME}] [${params.buildTool}][Ejecucion fallida en stage ${STAGE}  (Revisar en el siguiente link: ${env.BUILD_URL})"
			error "Ejecucion fallida en stage ${STAGE}"
        }
    }
}
}

return this;