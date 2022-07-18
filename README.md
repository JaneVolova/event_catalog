# event_catalog

REST-приложение для добавления и получения из базы данных информации о событиях (фильмов, театральных представлений, концертов).

Используется СУБД PostgreSQL. 

Для запуска приложения:
1. создать бд с именем events,
   username=postgres,
   password=postgres
2. указать в файле application.properties свой локальный порт
3. запустить EventCatalogApplication
4. заполнить таблицы тестовыми данными выполнив скрипт script.sql

Эндпоинты:
   - для получения события: localhost:8081/event/{id} (метод GET)
   - для добавления события*: localhost:8081/event (метод POST)

*образец json в файле jsonExample.md

