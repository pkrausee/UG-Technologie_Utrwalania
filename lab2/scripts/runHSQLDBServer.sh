#!/bin/bash

java -cp <ścieżka do hsqldb.jar> org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb
