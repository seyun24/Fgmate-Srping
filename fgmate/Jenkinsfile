pipeline {
    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    environment {
        imagename = ""
        tag = "v1"
        registryCredential = ''
        dockerImage = ''
    }

    stages {
        stage('Bulid Gradle') {
            steps {
                echo 'Build Gradle'
                sh """
                ./gradlew clean build 
                """
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Bulid Docker') {
            steps {
                echo 'Build Docker'
                script {
                    dockerImage = docker.build imagename
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Push Docker') {
            steps {
                echo 'Push Docker'
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push tag
                    }
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('SSH SERVER EC2') {
            steps {
                echo 'SSH'
                sshagent (credentials: ['ubuntu']) {
                sh """
                     ssh -o StrictHostKeyChecking=no ubuntu@5 '
                            docker stop \$(docker ps -a -q) || true
                            docker rm \$(docker ps -a -q) || true
                            docker rmi \$(docker images -q) || true
                            docker pull ${imagename}:${tag}
                            docker run -d -p 9000:9000 -p 50000:50000 --name api ${imagename}:${tag} 
                        '
                    """
                }
            }
        }
    }
}
