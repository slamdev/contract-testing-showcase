# Contract Testing showcase [![Build Status](https://travis-ci.org/slamdev/contract-testing-showcase.svg?branch=master)](https://travis-ci.org/slamdev/contract-testing-showcase)

## Description
### Problem
There is a legacy backend that should be refactoring. We need to make sure that the API stays the same.
### Solution
Send the same requests to both legacy and new backends and verify that responses are the same.

## Development
Project uses `Java 8` version.

Gradle's `check` task is used to verify code quality and includes static code checks ([PMD](https://pmd.github.io/),
[CheckStyle](http://checkstyle.sourceforge.net/) and [FindBugs](http://findbugs.sourceforge.net/)). This task should be 
run before pushing the code to make sure that all the checks are passed. If some check is failed, the detailed 
description can be found in the corresponding html report at `build/reports` directory. Sometimes some static code 
checks rules do not suit to the particular code block and need to be disabled. This can be done in the corresponding 
config file at `config` directory.
