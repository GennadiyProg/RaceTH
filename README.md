Welkom to nash project RaceTH!!!

## Первые действия при установке
Воспользуйтесь [Intellij IDEA](https://www.jetbrains.com/ru-ru/idea/download/#section=windows) для работы с проектом. <br><br>

При первом запуске и открытии .java файла среда
предложит вам выбрать версию JDK для работы с проектом как на скриншоте ниже. 
Нажмите кнопку Setup SDK и выберите Java SDK нужной версии 
(Мы используем [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)). <br>
![](./rdmscrs/setup_sdk0.png?raw=true) <br>

Далее нужно настроить конфигурацию для запуска. Нажмите на кнопку Add Configuration. <br>
![](./rdmscrs/setup_sdk6.png?raw=true) <br>

В открывшемся меню нажмите на плюсик и в списке выберите Maven. <br>
![](./rdmscrs/setup_sdk7.png?raw=true) <br>

В поле Command Line прописываем compile exec:java -f pom.xml. 
Нажмите Apply и закройте окно. Теперь вы можете запускать проект в Intellij IDEA<br>
![](./rdmscrs/setup_sdk9.png?raw=true) <br>

На этом пока всё. В дальнейшем, вероятно, доработаем инструкцию.<br>

[JavaFx JDK](https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip)