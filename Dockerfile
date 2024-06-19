<<<<<<< HEAD
FROM gradle:8.6-jdk21
=======
FROM gradle:8.6-jdk20
>>>>>>> 4b2f59a5c3ad1c2ee7a5b4f47039d1e7c1f4806f

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon build

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar
