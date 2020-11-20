# Golem Transcoding requestor

Golem Transcoding is a React + Spring based webapp accepting video files as input and transcoding these files into different formats using Golem.

Every input file will be converted to .mkv, .mov, .avi and .mp4 output files.

Here's a YouTube video explaining how the whole system works.

## Table of contents
* [Project structure](#project-structure)
* [Setup](#setup)
* [Licence](#licence)

## Project structure

* docker - Ubuntu 18.04 base image with ffmpeg installed on top of it. Contains scripts to build the image and upload it to Yagna repository.
* requestor-python - Golem requestor script for splitting the work into subtasks and sending them to remote nodes.
* requestor-python/core.py - basic data types used across the project.
* requestor-python/engine.py - integration with Yagna API.
* requestor-python/requestor.py - entry point of the whole program.
* requestor-python/task.py - generation of subtask details.
* requestor-python/utils.py - utils copied from yapapi examples.
* requestor-python/create-venv.sh - creates virtual environment and installs dependencies.
* requestor-python/run.sh - executes the requestor.py script in the context of activated virtual environment.
* requestor-python/output - transcoded video file storage.
* requestor-spring - backend server based on Spring Boot. Serves frontend assets and integrates with requestor script to send tasks to Golem. Responsible for keeping information about the transcoding job status.
* requestor-spring/gradle - Gradle 6.7.1 distribution.
* requestor-spring/src/main/java - all Java classes consituting the backend part.
* requestor-spring/src/main/js - frontend part of the application. Webpack + React.
* requestor-spring/src/main/resources/public - static files served by the server.
* requestor-spring/src/main/resources/application.yml - Spring app configuration.

## Setup

Golem setup. If you encounter an issue then refer to https://handbook.golem.network/requestor-tutorials/flash-tutorial-of-requestor-development for full set of instructions.

1. Install python 3
1. `pip install venv`
1. `curl -sSf https://join.golem.network/as-requestor | bash -`
2. `~/.local/bin/yagna service run`
3. `~/.local/bin/yagna payment init -r`
4. `yagna app-key create requestor`
5. Store the key for later.

Transcoding requestor setup
1. `git clone https://github.com/Edhendil/golem-transcoding.git`
1. `cd golem-transcoding/requestor-python`
1. `./create-venv.sh` 

Frontend app setup
1. `cd golem-transcoding/requestor-spring/src/main/js`
1. `yarn`
1. `yarn build:prod`

Backend app setup
1. Setup JDK 8 using AdoptJDK [website](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
1. `cd golem-transcoding/requestor-spring/src/main/resources`
1. Edit file `application.yml`
1. Set golem.scriptLocation to the absolute path of the requestor-python folder in the cloned repository. Example: `"/home/your-user/golem-transcoding/requestor-python"`.
1. Set golem.yagnaKey to the requestor key generated through the `yagna app-key create requestor` command. Example: `"a0f1a0b7af0a42bcab0361f39dfd42b4"`
1. Set golem.inputFileLocation to the absolute path of the folder where you want to store the input video files. Example: `"/home/your-user/transcoding-inputs"`. Be sure to create such folder first.
1. `cd golem-transcoding`
1. `./gradlew :bootRun`

## Licence

MIT