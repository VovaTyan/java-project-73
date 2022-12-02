FROM gradle:7.3.0-jdk16

COPY ./ .

RUN gradle installDist

CMD ./build/install/app/bin/app