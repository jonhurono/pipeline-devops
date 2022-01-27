/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
    stage('Build & Unit Test') {
        STAGE = env.STAGE_NAME
        sh 'env'
        println "Stage: ${env.STAGE_NAME}"
        sh './gradlew clean build'
    }

    stage('Sonar') {
        STAGE = env.STAGE_NAME
        println "Stage: ${env.STAGE_NAME}"
    }

    stage('Run') {
        STAGE = env.STAGE_NAME
        println "Stage: ${env.STAGE_NAME}"
    }

    stage('Test') {
        STAGE = env.STAGE_NAME
        println "Stage: ${env.STAGE_NAME}"
        }

	stage('Nexus') {
        STAGE = env.STAGE_NAME
        println "Stage: ${env.STAGE_NAME}"
    }
    
}

return this;