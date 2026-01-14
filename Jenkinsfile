pipeline {
    agent any

    tools {
        // Asegúrate de que 'Maven3' coincida con el nombre que le diste
        // en la configuración de herramientas globales de Jenkins
        maven 'Maven3'
    }

    environment {
        // El ID de la credencial 'Secret Text' que creaste en Jenkins para el token de SonarQube
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
                    // Usamos -DskipTests para no ejecutar las pruebas aquí, ya que tenemos una etapa dedicada
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Análisis Estático') {
            steps {
                dir('backend') {
                    // 'SonarQubeServer' debe coincidir con el nombre que le diste a tu servidor SonarQube
                    // en la configuración del sistema de Jenkins
                    withSonarQubeEnv('SonarQubeServer') {
                        // El comando de Maven para ejecutar el análisis
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
                    // Archiva los resultados de las pruebas para que Jenkins los muestre
                    junit 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Pruebas Funcionales') {
            steps {
                script {
                    try {
                        // 1. Iniciar solo la DB en segundo plano
                        echo 'Iniciando base de datos MongoDB...'
                        sh 'docker-compose up -d mongo'

                        // 2. Iniciar el backend en segundo plano y guardar su PID
                        echo 'Iniciando aplicación backend...'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'

                        // 3. Esperar a que la aplicación esté lista (damos un margen de 30s)
                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'

                        // 4. Ejecutar pruebas de Newman y generar reporte
                        echo 'Ejecutando pruebas funcionales con Newman...'
                        sh 'mkdir -p newman'
                        sh 'newman run Hidrored.postman_collection.json --reporters cli,junit --reporter-junit-export newman/newman-results.xml'

                    } finally {
                        // 5. Limpieza: detener la aplicación y la DB
                        echo 'Limpiando... Deteniendo la aplicación y la base de datos.'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                    // Archiva los resultados para que Jenkins los muestre
                    junit 'newman/newman-results.xml'
                }
            }
        }

        stage('Pruebas de Rendimiento') {
            steps {
                script {
                    try {
                        // 1. Iniciar solo la DB en segundo plano
                        echo 'Iniciando base de datos MongoDB...'
                        sh 'docker-compose up -d mongo'

                        // 2. Iniciar el backend en segundo plano y guardar su PID
                        echo 'Iniciando aplicación backend...'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'

                        // 3. Esperar a que la aplicación esté lista
                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'

                        // 4. Ejecutar prueba de JMeter
                        echo 'Ejecutando pruebas de rendimiento con JMeter...'
                        // Se asume que JMeter está disponible en el path del agente de Jenkins
                        sh 'jmeter -n -t jmeter-tests/hidrored_performance_test.jmx -l jmeter-tests/results.jtl'

                    } finally {
                        // 5. Limpieza: detener la aplicación y la DB
                        echo 'Limpiando... Deteniendo la aplicación y la base de datos.'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                    // Publica los resultados de rendimiento para que Jenkins los muestre
                    perfReport parsers: [JMeterParser(glob: 'jmeter-tests/results.jtl')]
                }
            }
        }
        
        stage('Pruebas de Seguridad') {
            steps {
                script {
                    try {
                        // 1. Iniciar la DB y la aplicación
                        echo 'Iniciando entorno para escaneo de seguridad...'
                        sh 'docker-compose up -d mongo'
                        sh 'nohup java -jar backend/target/*.jar > backend.log 2>&1 & echo $! > app.pid'
                        echo 'Esperando a que la aplicación inicie (30 segundos)...'
                        sh 'sleep 30'

                        // 2. Ejecutar escaneo de línea base de OWASP ZAP
                        echo 'Ejecutando escaneo de seguridad con OWASP ZAP...'
                        // Usamos --network=host para que el contenedor de ZAP pueda acceder a localhost:8080
                        // Montamos el directorio actual para que ZAP pueda escribir el reporte
                        sh 'docker run --rm --network=host -v "$(pwd)":/zap/wrk/:rw owasp/zap2docker-stable zap-baseline.py -t http://localhost:8080 -g zap_gen.conf -J report.json -r report.html'

                    } finally {
                        // 3. Limpieza
                        echo 'Limpiando entorno...'
                        sh 'kill $(cat app.pid)'
                        sh 'docker-compose down'
                    }
                }
            }
            post {
                always {
                    // Archiva el reporte HTML para su revisión
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
            // Aquí se pueden añadir pasos de limpieza o notificaciones
        }
    }
}
