.\wget "https://download.oracle.com/java/17/archive/jdk-17.0.3.1_windows-x64_bin.msi"
.\wget "https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip"

7za x "openjfx-17.0.2_windows-x64_bin-sdk.zip"

chcp 65001
echo Не выключайте терминал до полной установки Java

.\jdk-17.0.3.1_windows-x64_bin.msi
del "jdk-17.0.3.1_windows-x64_bin.msi"
del "openjfx-17.0.2_windows-x64_bin-sdk.zip"

java --module-path .\javafx-sdk-17.0.2\lib --add-modules javafx.controls,javafx.base,javafx.fxml -jar .\RaceTH-1.0-SNAPSHOT-jar-with-dependencies.jar