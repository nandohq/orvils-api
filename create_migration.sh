#!/bin/bash

FINAL_FOLDER="$(pwd)/src/main/resources/db/migration/"

echo "#==============================================================#"
echo "|              Let's create a migration file now?              |"
echo "#==============================================================#"
echo "| What's the migration file name?                              |"
echo "|--------------------------------------------------------------|"
read -p "  " FILE_NAME

if [ -z "$FILE_NAME" ]
then
    echo "#--------------------------------------------------------------#"
    echo "| Please, provide a valid file name!!                          |"
    echo "#==============================================================#"
    exit 1
fi

FINAL_FILE="V$(date +%Y%m%d%H%M%S)__$FILE_NAME.sql"
FINAL_PATH="$FINAL_FOLDER$FINAL_FILE"

if [ -e "$FINAL_PATH" ]
then
    echo "#--------------------------------------------------------------#"
    echo "| The migration file '$FINAL_FILE' already exists!!            |"
    echo "#==============================================================#"
    exit 1
fi

touch "$FINAL_PATH"

echo "#--------------------------------------------------------------#"
echo "| The migration file was created succesfully!!                 |"
echo "#==============================================================#"