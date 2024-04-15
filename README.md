# Daiquiri Language - язык программирования на Java
## О проекте
Учебный проект создания языка программирования, имеющий синтаксис на русском языке и позволяющий совершать базовые действия для программирования.
К языку был разработан легкий редактор кода DaiquiriEditor, для визуализации работы языка.
Язык и редактор кода написаны на Java, проект собран с помощью Maven. Подробнее про разработку языка можно прочитать в [отчете о проделанной работе](https://github.com/yudls/daiquiri/blob/main/docs/daiquri.pdf) (отчет написан про первую версию языка, после этого в язык и редактор кода было добалено много дополнительных функций).

## Скриншоты
### Пример работы программы.
Решение [задачи](https://leetcode.com/problems/longest-substring-without-repeating-characters/description/) с сайта leedcode.com на языке программирования Daiquiri.
![пример работы программы](https://github.com/yudls/daiquiri/assets/119896503/c795d94a-c3ce-4f53-ab18-d58b7b914c5d)
### В редакторе кода есть кнопки для работы с файлами.
![кнопки для работы с файлами](https://github.com/yudls/daiquiri/assets/119896503/95ce12a9-a992-4789-8c5b-653710c9c186)
### Также можно открыть документацию прямо в редакторе кода и подсказку с шорткатами.
![кпопки помощь](https://github.com/yudls/daiquiri/assets/119896503/397d96cf-2cab-426e-be26-cb7beb230017)
### Открытие документации в редакторе кода.
![кпопки помощь](https://github.com/yudls/daiquiri/assets/119896503/1ee6aba2-c2a6-4611-9fe1-3ec123ec75ae)
## Документация по языку 
Документацию по языку можно посмотреть в файле [Documentation Daiquiri Language.pdf](https://github.com/yudls/daiquiri/blob/main/src/main/resources/Documentation%20Daiquiri%20Language.pdf) или открыть через редактор кода.

## Как попробовать
Чтобы программа работала на вашем компьютере должен быть установлен нужен JDK, если его ещё нет, можете установить с [официального сайта Oracle](https://www.oracle.com/java/technologies/downloads).
Чтобы запустить приложение, скачайте актуальную версию [daiquiri.jar](https://github.com/yudls/daiquiri/blob/main/versions/daiquiri.jar)
или можете самостоятельно собрать проект:
```
git clone https://github.com/yudls/daiquiri
cd daiquiri
mvn clean install
```
