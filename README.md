# Библиотека для отправки логов в elk стэк

  <ol>
    <li><a href="#Описание">Описание</a></li>
    <li><a href="#Подключение">Подключение</a></li>
    <li><a href="#Настройка">Настройка</a></li>
    <li><a href="#Использование">Использование</a></li>
    <li><a href="#Пример">Пример</a></li>
  </ol>


---
## Описание

Библиотека elk-logger предназначена для отправки логов на elk сервер. 

Преимущество данной библиотеки заключается в том, что при отправке логов на elk сервер все поля ваших объектов и переменных индексируются для быстрого поиска в Logstash.

Например: объект **request**

```json
{
  "subject": "Приглашение на обучающие курсы",
  "templateName": "COURSE_INVITE_TEMPLATE"
}
```

Будет представлен в Elasticsearch как два индексируемых поля

```text
request.subject:Приглашение на обучающие курсы
request.templateName:COURSE_INVITE_TEMPLATE
```

Кроме того, с помощью данной библиотеки можно настроить вывод логов в файл и консоль.

---
## Подключение
**gradle**
```text
implementation group: 'ru.clevertec', name: 'elk-logger', version:'0.1'
```

**maven**
```text
<dependency>
    <groupId>ru.clevertec</groupId>
    <artifactId>elk-logger</artifactId>
    <version>0.1</version>
</dependency>
```

--- 

## Опции:

#### Опции по умолчанию:

```yaml
logging-api:
  elastic:
    log-level: INFO
  logstash:
    host: localhost
    port: 5300
  file:
    path: logs/myLogs.log
    log-pattern: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    log-level: INFO
  console:
    log-pattern: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    log-level: INFO
```

Следующая настройка определяет максимальный уровень логов, которые будут отправлены в logstash. 

```yaml
logging-api:
  elastic:
    log-level: INFO
```

К примеру, если log-level: INFO, то в logstash будут поступать логи уровня INFO, WARN, ERROR. Логи уровня DEBUG, TRACE будут игнорироваться.

Такое правило относится и к отправке логов в консоль и в файл


## Использование