#!groovy
node {
   stage('Preparation') {
      git 'https://github.com/hstreb/spark-rest.git'
   }
   stage('Compile') {
      sh "./gradlew clean compileJava"
   }
   stage('Unit test') {
      sh "./gradlew test"
   }
   stage('Build') {
      sh "./gradlew installDist"
   }
   stage('Results') {
      junit 'build/**/TEST-*.xml'
   }
}
