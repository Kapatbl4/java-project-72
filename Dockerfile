FROM gradle:8.4-jdk20

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon build

CMD app/build/install/app/bin/app
