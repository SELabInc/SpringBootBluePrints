def podMavenImage = "maven:3.6.3-jdk-11"

def mavenGoal = "deploy"
def mavenArgs = "-DskipTests"

def repoUrl = "https://nexus.selab.cloud/repository/maven-snapshot/"
def repoId = "nexus-snapshot"
def jenkinsCredentialsId = "nexus-docker-registry-cred"


pipeline {
        agent {
            kubernetes {
                defaultContainer 'docker'
                yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: jenkins
spec:
  containers:
    - name: maven
      image: ${podMavenImage}
      args:
        - "infinity"
      command:
      - "sleep"
      volumeMounts:
        - name: "cephfs-pvc"
          mountPath: /root/.m2
  volumes:
      - name: cephfs-pvc
        persistentVolumeClaim:
          claimName: cephfs-pvc
"""
            }
        }
        stages {
            stage('Maven call with credentials') {
                when {
                    anyOf {
                        not {
                            equals expected: null, actual: jenkinsCredentialsId
                        }
                        not {
                            equals expected: null, actual: repoUrl
                        }
                        not {
                            equals expected: null, actual: repoId
                        }

                    }
                }
                steps {
                    container('maven') {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'nexus-docker-registry-cred',
                                    passwordVariable: 'CREDENTIALS_PSW',
                                    usernameVariable: 'CREDENTIALS_USR')]) {

                                def mavenRepoArgs = "-DrepositoryId=" + repoId + " -Drepo.login=" + CREDENTIALS_USR + " -Drepo.pwd=" + CREDENTIALS_PSW + " -Durl=" + repoUrl

                                sh "mvn " + mavenGoal + " " + mavenRepoArgs + " " + mavenArgs
                            }

                        }
                    }
                }
            }
            stage('Maven call without credentials') {
                when {
                    anyOf {
                        equals expected: null, actual: jenkinsCredentialsId
                        equals expected: null, actual: repoUrl
                        equals expected: null, actual: repoId
                    }
                }
                steps {
                    container('maven') {
                        script {
                            sh "mvn " + mavenGoal + " " + mavenArgs
                        }
                    }
                }
            }
        }
    }
