# CRS API for Java

[![Release](https://img.shields.io/github/release/otymko/crs-api-4j.svg?style=for-the-badge)](https://github.com/otymko/crs-api-4j/releases/latest)
[![Software License](https://img.shields.io/badge/license-Apache_License_2.0-blue.svg?style=for-the-badge)](/LICENSE)
[![Build status](https://img.shields.io/github/workflow/status/otymko/crs-api-4j/build?style=for-the-badge)](https://github.com/otymko/crs-api-4j/actions?workflow=build)

Библиотека взаимодействия с сервером хранилища конфигураций 1С (CRS) по протоколу HTTP.

## Возможности

Управление сервером хранилищ конфигураций 1С через объектную модель.

Доступны действия:

* Взаимодействия с пользователями хранилища (создание нового пользователя, поиск пользователя, запрос списка
  пользователей).

## Как подключить к проекту

`в процессе заполнения`

## Примеры

### Создание нового пользователя
```java
// новый клиент к серверу хранилища 1С
RepositoryClient client = new RepositoryClient("http://localhost:5000/repo/repo.1ccr", "8.3.12.1855");
try {
  // установка соединения
  client.connect("Администратор", "ПарольАдминистратора");
  // создание пользователя
  client.createUser("НовыйПользователь", "ПарольПользователя", "1");
} catch(RepositoryClientException exception) {
  // что-то пошло не так
}
```

## Как вести разработку

Разработка ведется в репозитории [otymko/crs-api-4j](https://github.com/otymko/crs-api-4j). Issue и PR нужно создавать
именно в нем.

Используется:

* Java 11
* Lombok
* JUnit 5

Прежде чем `кодить` нужно:

* Убедиться, что существует issue (или создать)
* Обсудить идею / проблему с владельцем проекта
