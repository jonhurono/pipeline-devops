/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(String pipelineType){
	
	figlet 'Maven'
	
	if(pipelineType == 'CI'){
		
	figlet 'Integración Continua'
		
	stage('Compile') {
        STAGE = env.STAGE_NAME
        sh "mvn clean compile -e"
	figlet "Stage: ${env.STAGE_NAME}"
    }
    
    stage('Test') {
        STAGE = env.STAGE_NAME
        sh "mvn clean test -e"
	figlet "Stage: ${env.STAGE_NAME}"
    }

    stage('Jar') {
        STAGE = env.STAGE_NAME
        sh "mvn clean package -e"
	figlet "Stage: ${env.STAGE_NAME}"
    }	
	} else (){
		
	figlet 'Delivery Continuo'
		
	stage('SonarQube analysis') {
        STAGE = env.STAGE_NAME
        def scannerHome = tool 'sonar-scanner';
        withSonarQubeEnv('sonar-server') {
        bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-maven -Dsonar.sources=src -Dsonar.java.binaries=build"
	figlet "Stage: ${env.STAGE_NAME}"
        }
    }
    
    stage('uploadNexus') {
        STAGE = env.STAGE_NAME
        echo 'uploadNexus';
        nexusPublisher nexusInstanceId: 'nexus_test', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'C:/Users/jjcha/Documents/Diplomado/Repos/ejemplo-maven/build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
	figlet "Stage: ${env.STAGE_NAME}"
    }
			
	}	

}

return this;
