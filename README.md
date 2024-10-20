# Microservice Template Spring

This project serves as template for new spring applications.

## Authentication

This template uses both Basic and Bearer Token (JWT) authentication methods. For signatures, it uses the public and
private keys in *resources/certs* folder. To generate new keys follow these steps:

```
# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

## CI/CD

This template makes use of GitHub actions for its CI/CD pipeline.

Every commit 1 GitHub app and 1 GitHub workflow (2 jobs), making 3 status checks in total, will run against your
changes and only if every check is successful, will you be able to merge your changes. Here is a rundown of those
checks.

### GitHub Action - CI CD Workflow

This workflow is made of 2 different steps and its intent is to build and test your changes and then to build a docker
image with the resulting JAR file and push it to docker hub.

#### Build and test application

This job is the **FIRST** to run on the workflow.

It sets up JDK 21 (temurin) and maven and then runs the command "mvn clean package" command to compile, test and package
the application. It then uploads the resulting JAR file, so it can be used in other jobs.

#### Build and push docker image

This job is dependent on [Build and test application](#build-and-test-application).

It downloads the JAR file artifact and build a docker image with it. After building the image it pushes it to the docker
registry.

### GitHub Application - Semantic PRs

It verifies your Pull Request title follows the conventional commit guidelines. For more information on the rules being
enforced, take a look at
the [angular commit message guidelines](https://github.com/angular/angular/blob/22b96b9/CONTRIBUTING.md#-commit-message-guidelines).

- feat: A new feature
- fix: A bug fix
- docs: Documentation only changes
- style: Changes that do not affect the meaning of the code (white-space, formatting, missing semicolons, etc...)
- refactor: A code change that neither fixes a bug nor adds a feature
- perf: A code change that improves performance
- test: Adding missing tests or correcting existing tests
- build: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
- ci: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
- chore: Changes that don't modify the source code directly but are important for maintaining the project
- revert: Revert existing code
