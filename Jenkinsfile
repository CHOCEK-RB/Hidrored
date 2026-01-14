pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        SONAR_CREDENTIALS_ID = 'SONAR_AUTH_TOKEN'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    echo 'Instalando dependencias y construyendo el frontend...'
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    echo 'Compilando y empaquetando la aplicación backend...'
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Análisis Estático') {
            steps {
                dir('backend') {
                    withSonarQubeEnv('SonarQubeServer') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Pruebas Unitarias') {
            steps {
                dir('backend') {
                    echo 'Ejecutando pruebas unitarias (JUnit)...'
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Pruebas Funcionales') {
            steps {
                script {
                    try {
                        echo 'Iniciando base de datos MongoDB...'
                        sh 'docker-compose up -d mongo'

                        echo 'Iniciando aplicación backend...'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'

                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'

                        echo 'Ejecutando pruebas funcionales con Newman...'
                        sh 'mkdir -p newman'
                        sh 'newman run Hidrored.postman_collection.json --reporters cli,junit --reporter-junit-export newman/newman-results.xml'

                    } finally {
                        echo 'Limpiando... Deteniendo la aplicación y la base de datos.'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                    junit 'newman/newman-results.xml'
                }
            }
        }

        stage('Pruebas de Rendimiento') {
            steps {
                script {
                    try {
                        echo 'Iniciando base de datos MongoDB...'
                        sh 'docker-compose up -d mongo'

                        echo 'Iniciando aplicación backend...'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'

                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'

                        echo 'Ejecutando pruebas de rendimiento con JMeter...'
                        sh 'jmeter -n -t jmeter-tests/hidrored_performance_test.jmx -l jmeter-tests/results.jtl'

                    } finally {
                        echo 'Limpiando... Deteniendo la aplicación y la base de datos.'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                  perfReport sourceDataFiles: 'jmeter-tests/results.jtl', parsers: [[$class: 'JMeterParser', glob: 'jmeter-tests/results.jtl']]
                }
            }
        }
        
        stage('Pruebas de Seguridad') {
            steps {
                script {
                    def workspacePath = pwd()
                    try {
                        echo 'Iniciando entorno para escaneo de seguridad...'
                        sh 'docker-compose up -d mongo'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'
                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'
                        echo 'Ejecutando escaneo de seguridad con OWASP ZAP...'
                        sh "docker run --rm --network=host -v ${workspacePath}:/zap/wrk/:rw ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://localhost:8080 -g zap_gen.conf -J report.json -r report.html -I"

                    } finally {
                        echo 'Limpiando entorno...'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: 'report.html', allowEmptyArchive: true
                }
            }
        }

        stage('Desplegar Aplicación') {
            steps {
                echo 'Desplegando la aplicación con Docker Compose...'
                sh 'docker-compose up --build -d'
            }
        }
    }

    post {
        always {
            echo 'El pipeline ha finalizado.'
        }
    }
}
