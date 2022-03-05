Welkom to nash project RaceTH!!!

## Первые действия при установке
Воспользуйтесь IDE [Intellij IDEA](https://www.jetbrains.com/ru-ru/idea/download/#section=windows) для работы с проектом. <br><br>

При первом запуске и открытии .java файла среда
предложит вам выбрать версию JDK для работы с проектом как на скриншоте ниже. 
Нажмите кнопку Setup SDK и выберите Java SDK нужной версии 
(Мы используем [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)). <br>
![](./rdmscrs/setup_sdk0.png?raw=true) <br><br>

После этого нажмите кнопку Run в выпадающем окне Run. <br>
![](./rdmscrs/setup_sdk1.png?raw=true) <br>

В появившемся окне нажмите Main. Программа, скорее всего, не запустится. <br>
![](./rdmscrs/setup_sdk2.png?raw=true) <br>

Теперь открываем выпадающее окно Run и нажимаем кнопку Edit Configurations <br>
![](./rdmscrs/setup_sdk3.png?raw=true) <br>

В этом окне жмакаем Modify options и выбираем Add VM options.
И теперь в только что появившемся поле прописываем: <br>
<span style="color:blue">`--module-path ".\lib\javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml`</span> <br>

![](./rdmscrs/setup_sdk4.png?raw=true) <br>

В папке lib нужно выбрать все .jar файлы, ПКМ, нижняя кнопка(что-то на подобии Use like libraries) <br>
Папку resources сделать папкой "Resoures root": <br>
![](./rdmscrs/setup_sdk5.png?raw=true) <br>

<span>`!!!При установке Postgresql пароль для root желательно выбрать "postgres"`</span>

На этом пока всё. В дальнейшем, вероятно, доработаем инструкцию.<br>

