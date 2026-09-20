# Тестовое

RESTful API для работы с отелями

## Запуск

```bash
mvn spring-boot:run
```

Приложение стартует на порту `8092`. Внешних зависимостей нет.

### Демо-данные

По умолчанию база пустая. Отель из ТЗ лежит в отдельном Liquibase-changeset под контекстом demo:

## Документация API

- Swagger UI: http://localhost:8092/property-view/swagger-ui.html
- OpenAPI: http://localhost:8092/property-view/v3/api-docs

H2-консоль: http://localhost:8092/property-view/h2-console
(JDBC URL `jdbc:h2:mem:property_view`, пользователь `sa`, пароль пустой).

## Смена базы данных

Код не содержит нативного SQL, схемой владеет только
Liquibase, настройки подключения вынесены в профили.

```bash
mvn -Ppostgres spring-boot:run -Dspring-boot.run.profiles=postgres
mvn -Pmysql    spring-boot:run -Dspring-boot.run.profiles=mysql
```

Параметры подключения задаются переменными окружения `DB_HOST`, `DB_PORT`,
`DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`.
