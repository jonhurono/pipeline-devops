/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(String pipelineType){
	
	figlet 'Gradle'
	
	if(pipelineType == 'CI'){
		
	figlet 'Integración Continua'
		
	stage('Build & Unit Test') {
        STAGE = env.STAGE_NAME
        sh 'env'
        figlet "Stage: ${env.STAGE_NAME}"
    }

    stage('Sonar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }

    stage('Run') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }
	stage('Test') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }
	stage('UploadSnapshotJar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
	}
	}
		
else{
	figlet 'Delivery Continuo' 
	
	stage('DownloadSnapshotJar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
        }

	stage('RunSnapshotJar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }
	stage('TestSnapshotJar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }
	stage('UploadReleaseJar') {
        STAGE = env.STAGE_NAME
        figlet "Stage: ${env.STAGE_NAME}"
    }
			
}
  
}

return this;
