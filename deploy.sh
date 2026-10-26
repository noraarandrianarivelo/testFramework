#!/bin/bash

# =========================
# CONFIG
# =========================
APP_NAME="test"
SRC_DIR="src/main/java"
WEBAPP_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"

TOMCAT_HOME="/home/noraa/tomcat/tomcat10"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"
FRAMEWORK_JAR="$LIB_DIR/framework.jar"

# sécurité : le framework doit déjà être présent dans lib/
if [ ! -f "$FRAMEWORK_JAR" ]; then
  echo "❌ ERREUR: $FRAMEWORK_JAR introuvable. Compile et copie le framework dans lib/ d'abord."
  exit 1
fi

echo "===== CLEAN BUILD ====="
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR/classes"

echo "===== COMPILATION DES CONTROLEURS (avec tous les jars de lib/ en classpath) ====="

# Construit le classpath avec TOUS les jars presents dans lib/
# (framework.jar, servlet-api.jar, spring-*.jar, postgresql.jar, ...)
CLASSPATH=$(find "$LIB_DIR" -name "*.jar" | tr '\n' ':')

find "$SRC_DIR" -name "*.java" > sources.txt
javac -parameters -cp "$CLASSPATH" -d "$BUILD_DIR/classes" @sources.txt
rm sources.txt

echo "===== PREPARATION WEBAPP (index.jsp + web.xml) ====="

TOMCAT_WEBAPPS="$TOMCAT_HOME/webapps"
APP_DIR="$TOMCAT_WEBAPPS/$APP_NAME"

rm -rf "$APP_DIR"
cp -r "$WEBAPP_DIR" "$APP_DIR"

echo "===== DEPLOY DES JARS (framework.jar + spring-*.jar + postgresql.jar, WEB-INF/lib) ====="
# servlet-api.jar est deja fourni par Tomcat, on ne le duplique pas dans WEB-INF/lib

mkdir -p "$APP_DIR/WEB-INF/lib"
find "$LIB_DIR" -name "*.jar" ! -name "servlet-api.jar" -exec cp {} "$APP_DIR/WEB-INF/lib/" \;

echo "===== DEPLOY CONTROLEURS (WEB-INF/classes, NON jarrés) ====="
# Important : les contrôleurs restent en .class "exploded" et non dans un jar,
# car le scan par réflexion (recupererClasses) lit un vrai dossier sur disque
# via classLoader.getResource(...), pas une entrée jar.

mkdir -p "$APP_DIR/WEB-INF/classes"
cp -r "$BUILD_DIR/classes/." "$APP_DIR/WEB-INF/classes/"

echo "===== CLEAN TOMCAT CACHE (OPTIONNEL) ====="

rm -rf "$TOMCAT_HOME/work/Catalina/localhost/$APP_NAME"

echo "===== DEPLOY DONE ====="
echo "URL: http://localhost:8080/$APP_NAME/"
