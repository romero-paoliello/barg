#!/bin/sh

mvn resources:resources liquibase:update -Pliquibase
