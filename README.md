## Задача - реализовать реактивный RESTful Web Service “Калькулятор” с использованием Spring WebFlux.

#### При вызове сервиса клиент должен передать ему четыре параметра:
1. Текст JS/Python-функции №1 от 1 параметра Int;
2. Текст JS/Python-функции №2 от 1 параметра Int;
3. Количество расчетов, которые необходимо выполнить;
4. Признак выравнивания ответа (выдавать ответ в упорядоченном CSV-формате, 
либо выдавать результаты по мере их получения).


#### Сервис работает следующим образом:
1. По таймеру, через определенный (возможно указать в конфигурации) интервал, запускается итерация расчета;
2. Для каждого существующего в настоящий момент клиентского запроса производится вычисление 
результатов функции №1 и функции №2. Текст функций написан на языке 
JavaScript и должен вызываться из Вашего Java сервиса соответствующим образом. 
В качестве единственного параметра при вызове функций передается порядковый номер ее вызова, 
который уникален и отсчитывается для каждого вызова с начала. 
Функция обязательно должна возвращать значение через return;
3. Полученные результаты выдаются клиенту либо по мере вычисления, 
либо подвергаются выравниванию и выдаются в упорядоченном виде по мере возможности.

#### Пример упорядоченной выдачи результатов:
<№ итерации>, <результат функции 1>, <время расчета функции 1>, <кол-во полученных наперед результатов функции 1 (еще не выданных, в связи с медленным расчетом функции 2)>, <результат функции 2>, <время расчета функции 2>, <кол-во полученных наперед результатов функции 2 (еще не выданных, в связи с медленным расчетом функции 1)>

#### Пример неупорядоченной выдачи результатов:
<№ итерации>, <номер функции>, <результат функции>, <время расчета функции>

#### Инструкция по сборке и запуску:

Сборка осуществляется с помощью gradle из корневой папки проекта: 
```
gradlew build
java -jar build/libs/TitanTest.jar
```

Обращение к сервису происходит через url:
```
http://localhost:8080/count?code1=<код первой функции>
    &code2=<код второй функции>
    &isAligned=<признак выравнивания (0 или 1)>
    &times=<кол-во итераций>
```

#### Проверка реализованного сервиса:

Тестировать приложение возможно с помощью Swagger по следующему url:
```
http://localhost:8080/swagger-ui.html#
```
К сожалению, я не нашёл способа как заставить Swagger выводить пакеты по мере их получения, 
так что для проверки этого можно использовать сгенерированную им команду curl в командной строке или в Postman.

Длину интервала можно изменить в файле application.properties (в секундах).

#### Комментарии:

1. JS код запускается с помощью движка Nashorn, который выдаёт предупреждения о том, что скоро будет убран из JDK 
(но всё равно работает).
2. У меня не получилось сделать единый таймер для всех пользователей
(то есть два одинаковых запроса с перерывом в 3 секунды будут возвращать результаты с тем же перерывом в 3 секунды, 
а не каждый тик таймера с интервалом из конфигурации).
