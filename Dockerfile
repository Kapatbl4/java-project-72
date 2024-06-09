FROM gradle:8.6-jdk21

WORKDIR /app

copy /app .

RUN gradle installDist

CMD ./build/install/app/bin/app
