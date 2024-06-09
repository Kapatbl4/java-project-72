FROM gradle:8.6-jdk21

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon build

CMD ./build/install/app/bin/app
