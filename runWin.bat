del *.class
javac --module-path "%JAVAFX%\lib" --add-modules javafx.base,javafx.controls Main.java
java --module-path "%JAVAFX%\lib" --add-modules javafx.base,javafx.controls Main
