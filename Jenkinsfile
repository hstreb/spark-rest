node {
   stage('Preparation') {
      git 'https://github.com/hstreb/spark-rest'
   }
   stage('Build') {
      sh "./gradlew clean test installDist"
   }
   stage('Results') {
      junit 'build/**/TEST-*.xml'
   }
}
