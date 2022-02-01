/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
    stage('Compile') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            sh "mvn clean compile -e"
            figlet 'MAVEN: COMPILE'
        }
    }
    
    stage('Test') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            sh "mvn clean test -e"
            figlet 'MAVEN: TEST'
        }
    }

    stage('Jar') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            sh "mvn clean package -e"
            figlet 'MAVEN: JAR'
        }
    }

    stage('SonarQube analysis') {
        if(env.PSTAGE == env.STAGE_NAME || env.PSTAGE == "Todo"){
            env.STAGE = env.STAGE_NAME
            def scannerHome = tool 'sonar-scanner';
            withSonarQubeEnv('sonar-server') {
                bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-maven -Dsonar.sources=src -Dsonar.java.binaries=build"
                figlet 'MAVEN: SONAR'
            }
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
	        figlet 'MAVEN: NEXUS'
        }
    }
}

return this;