node ('master'){
    def maven = tool name: 'mvn_home',type: 'maven'
    stage ('checkout the code'){
        git credentialsId: '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', url: 'https://github.com/ratnakishorech/maven-web-application-o.git'
    }
    stage ('creating the package'){
        sh '${mvn_home}/var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/mvn_home/bin/mvn clean package'
    }
    stage ('sonarqube report'){
        sh '${mvn_home}/var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/mvn_home/bin/mvn clean sonar:sonar'
    }
    stage ('deploy to tomcat'){
        deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://65.1.48.84:8083/')], contextPath: null, war: '**/*.war'
    }
}
