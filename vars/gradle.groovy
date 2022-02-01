/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
    stage('Build & Unit Test') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            gradlew clean build
            figlet 'GROOVY: BUILD & UNIT TEST'
        }
    }

    stage('Sonar') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            def scannerHome = tool 'sonar-scanner';
            withSonarQubeEnv('sonar-server') {
            bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-maven -Dsonar.sources=src -Dsonar.java.binaries=build"
	        figlet 'GROOVY: SONAR'
        }
        }
    }

    stage('Run') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            gradlew bootRun &
            sleep 20
            figlet 'GROOVY: RUN'
        }
    }

    stage('Test') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            start chrome http://localhost:8081/rest/mscovid/test?msg=testing
            figlet 'GROOVY: TEST'
        }
    }
	stage('Nexus') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            nexusPublisher nexusInstanceId: 'nexus_test', 
            nexusRepositoryId: 'test-nexus', 
            packages: [[$class: 'MavenPackage', 
            mavenAssetList: [[classifier: '', 
            extension: '', 
            filePath: 'C:/Users/jjcha/Documents/Diplomado/Repos/ejemplo-maven/build/DevOpsUsach2020-0.0.1.jar']], 
            mavenCoordinate: [artifactId: 'DevOpsUsach2020', 
            groupId: 'com.devopsusach2020', 
            packaging: 'jar', 
            version: '0.0.1']]]
	        figlet 'GROOVY: NEXUS'
        }
    }
}

return this;