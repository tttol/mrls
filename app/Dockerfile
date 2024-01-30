FROM public.ecr.aws/sam/build-java17:1.108.0-20240123221230 as build-image
WORKDIR "/task"
COPY src src/
COPY gradle gradle/
COPY gradlew ./
COPY gradlew.bat ./
COPY build.gradle ./
COPY settings.gradle ./
RUN ./gradlew clean assemble

FROM public.ecr.aws/docker/library/amazoncorretto:17.0.10-alpine3.19
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.8.1 /lambda-adapter /opt/extensions/lambda-adapter
EXPOSE 8080
WORKDIR /opt
COPY --from=build-image /task/build/libs/mrls-2.0.0.jar /opt
CMD ["java", "-jar", "mrls-2.0.0.jar"]