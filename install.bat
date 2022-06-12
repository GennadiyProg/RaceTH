IF not exist "%userprofile%\Desktop\RaceTH" (md "%userprofile%\Desktop\RaceTH")

echo java --module-path .\javafx-sdk-17.0.2\lib --add-modules javafx.controls,javafx.base,javafx.fxml -jar .\RaceTH-1.0-SNAPSHOT-jar-with-dependencies.jar > "%userprofile%\Desktop\RaceTH\launch.bat"
rd "%userprofile%\Desktop\RaceTH\javafx-sdk-17.0.2" /q /s
7za x "openjfx-17.0.2_windows-x64_bin-sdk.zip" -o"%userprofile%\Desktop\RaceTH"
copy ".\RaceTH-1.0-SNAPSHOT-jar-with-dependencies.jar" "%userprofile%\Desktop\RaceTH\RaceTH-1.0-SNAPSHOT-jar-with-dependencies.jar"

.\wget "https://download.oracle.com/java/17/archive/jdk-17.0.3.1_windows-x64_bin.msi"
.\jdk-17.0.3.1_windows-x64_bin.msi
del "jdk-17.0.3.1_windows-x64_bin.msi"
