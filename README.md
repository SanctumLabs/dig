# DIG

[![.github/workflows/build.yml](https://github.com/SanctumLabs/dig/actions/workflows/build.yml/badge.svg)](https://github.com/SanctumLabs/dig/actions/workflows/build.yml)
[![Test](https://github.com/SanctumLabs/dig/actions/workflows/test.yml/badge.svg)](https://github.com/SanctumLabs/dig/actions/workflows/test.yml)
[![Code Scanning](https://github.com/SanctumLabs/dig/actions/workflows/codeql.yml/badge.svg)](https://github.com/SanctumLabs/dig/actions/workflows/codeql.yml)
[![Docker](https://github.com/SanctumLabs/dig/actions/workflows/docker.yml/badge.svg)](https://github.com/SanctumLabs/dig/actions/workflows/docker.yml)
[![Lint](https://github.com/SanctumLabs/dig/actions/workflows/lint.yml/badge.svg)](https://github.com/SanctumLabs/dig/actions/workflows/lint.yml)

A simple distributed ID generator built in Kotlin & [Ktor](https://ktor.io/) web framework.

## Getting Started

Ensure you have [gradle](https://gradle.org/) setup or you can use the [gradle wrapper](./gradlew) (
or [gradle wrapper for windows](./gradlew.bat) if on windows platform) to setup and install dependencies. Also ensure
you have Java setup on your local development machine

## Running the application

You can run the application with `./gradlew run` which will start the application on port 8080(this can be
changed [here](./src/main/resources/application.conf))
Alternatively, you can build a Jar file and run it directly from the Jar file.

Once you have a running application, simply make a HTTP GET request to get an ID:

``` bash
$ curl --request GET --url http://localhost:8080/dig
{"id":"7003240899912138752"}
```

## Building the application

You can build the application with `./gradlew build` which will create a jar file that can be found in the build/libs
directory(not pushed to VCS).
Once you have a Jaf file built, you can run the application with:

``` bash
java -jar dig-all.jar
```

## Building for Docker

To build for [Docker](https://www.docker.com/), you will need to have Docker installed locally.
The application can also be built to be run in a Docker container. There is a [Dockerfile](./Dockerfile) already
setup & one can manually build a docker image with:

``` bash
docker build -t <IMAGE_NAME> .
```

> Note the `.` at the end of the command indicating that the root directory is being used.

Alternatively, you can build a docker image using gradle:

``` bash
./gradlew buildImage
```

> This uses the ktor plugin to handle building a docker image.

## Running tests

Tests can be run with the `./gradlew test` command

## Lint checks

Linting has been setup with [detekt](https://detekt.dev/) and can be used with the command `./gradlew detekt`

## Continuous Integration & Continuous Deployment(CI/CD)

[Github Actions](https://docs.github.com/en/actions) is used to perform CI/CD for this project and the workflows can be
found in the
directory [workflows](./.github/workflows). However, this is not limited to Github Actions as other setups also exist
such as
[Gitlab](https://docs.gitlab.com/ee/ci/) & [Bitbucket Pipelines](https://bitbucket.org/product/features/pipelines) whose
config can be found [here](./.gitlab-ci.yml)
& [here](./bitbucket-pipelines.yml) respectively.

## Built with

- Kotlin - Programming Language
- [Ktor](https://ktor.io/) -  Web Framework
- [Gradle](https://gradle.org/) - build system
- [Detekt](https://detekt.dev/) -  Linter
