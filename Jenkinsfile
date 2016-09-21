#!groovy
node {
   stage('Preparation') {
      git 'https://github.com/hstreb/spark-rest.git'
   }
   stage('Compile') {
      sh "./gradlew clean compileJava"
   }
   stage('Unit test') {
      sh "./gradlew test jacocoTestReport"
   }
   stage('Build') {
      sh "./gradlew installDist"
   }
   stage('Results') {
      junit 'build/**/TEST-*.xml'
      publishHTML (target: [
              allowMissing: false,
              alwaysLinkToLastBuild: false,
              keepAll: true,
              reportDir: 'build/reports/jacoco/test/html/',
              reportFiles: 'index.html',
              reportName: "Jacoco Report"
      ])
   }
}
