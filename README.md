# Библиотека для отправки логов в elk стэк

  <ol>
    <li><a href="#Описание">Описание</a></li>
    <li><a href="#Подключение">Подключение</a></li>
    <li><a href="#Настройка">Настройка</a></li>
    <li><a href="#Использование">Использование</a></li>
    <li><a href="#Примеры">Примеры</a></li>
  </ol>


---
## Описание

Библиотека elk-logger предназначена для отправки логов на elk сервер. 

Преимущество данной библиотеки заключается в том, что при отправке логов на elk сервер все поля ваших объектов и ваши переменные будут индексироваться для быстрого поиска в Elasticsearch.

Например: объект **request**

```json
{
  "shape": "circle",
  "color": "yellow"
}
```

Будет представлен в Elasticsearch как два индексируемых поля
```text
request.shape:circle
request.color:yellow
```

Переменная
```text
String greeting = "Hello world!"
```

Будет представлена в Elasticsearch как индексируемое поле
```text
greeting:"Hello world!"
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

## Настройка:

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

Для отправки логов на сервер logstash вам необходимо переопределить настройки

```yaml
logging-api:
  logstash:
    host: localhost
    port: 5300
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

При создании нового объекта **new ElkLog** или **ElkLog.builder** необходимо передать имя логируемого класса или тип класса для дальнейшего анализа логов.

```text
ElkLog elkLog = new ElkLog(getClass().getName());
ElkLog.builder(getClass())
```

Отображение в Elasticsearch:
```text
logger_name:classFullName
```

Класс ElkLog имеет несколько методов, которые будут часто использоваться при создании логов:
```text
.addMessage("someMessage")
.addRequest(request)
.addResponse(response)
```

Отображение в Elasticsearch:
```text
message:someMessage
request.firstFieldName:firstFieldValue
request.secondFieldName:secondFieldValue
response.firstFieldName:firstFieldValue
response.secondFieldName:secondFieldValue
```

Остальные поля и объекты добавляются стандартным методом:
```text
.addField(fieldName, fieldValue)
```

Отображение в Elasticsearch:
```text
fieldName:fieldValue
```

Для замены значений в полях представлены методы:
```text
.setMessage("newMessage")
.setField(fieldName, fieldValue)
```

Уровень лога можно установить с помощью метода:
```text
.setLevel(Level level)
```

Для отправки логов представлены методы:
```text
.print()            // отправка логов на сервер Elk и в консоль
.printToElk()       // отправка логов только на сервер Elk
.printToConsole()   // отправка логов только в консоль. В Elastic лог будет отображаться в поле message:
```

Заметка: в консоли поля и объекты будут выводиться в том же порядке, в котором они были добавлены в объект ElkLog


## Примеры

### Создание лога с помощью ElkLog.builder и отправка его на сервер Elk 

```text
ElkLog.builder(getClass())
    .setLevel(Level.ERROR)
    .addMessage("Mail sent failed")
    .addField(LogFieldName.EXCEPTION_NAME, e.getClass().getName())
    .addField(LogFieldName.EXCEPTION_MESSAGE, e.getMessage())
    .addRequest(mailRequest)
    .print();
```

#### Сохранение лога в Elastic
```text
request.templateFields.name:Дмитрий Стеба request.subject:Рассмотрение заявки на участие в обучающем курсе request.urlsMap: - request.destinationEmail:pturskiy@clevertec.ru request.templateName:FILLING_APPLICATION_TEMPLATEs request.applicationId:1 level:ERROR level_value:40,000 port:54,531 traceId:52dbc02c8aa5b6c3 exceptionMessage:No enum constant ru.clevertec.mail.api.enums.HtmlTemplate.FILLING_APPLICATION_TEMPLATEs @version:1 message:Mail sent failed @timestamp:Jul 27, 2021 @ 10:38:32.747 host:192.168.56.1 logger_name:ru.clevertec.mail.api.service.impl.KafkaListenerService exceptionName:ru.clevertec.mail.api.exception.HtmlTemplateNameNotFoundException thread_name:org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1 spanId:08074779c57fa524 _id:6Izm5noBTDr2KtQOqtmc _type:_doc _index:testindex-2021.07.27 _score: -
```

#### Сохранение лога в консоль
```text
10:38:32.747 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] ERROR r.c.m.a.s.impl.KafkaListenerService - message=Mail sent failed, exceptionName=ru.clevertec.mail.api.exception.HtmlTemplateNameNotFoundException, exceptionMessage=No enum constant ru.clevertec.mail.api.enums.HtmlTemplate.FILLING_APPLICATION_TEMPLATEs, request=MailRequest(templateName=FILLING_APPLICATION_TEMPLATEs, subject=Рассмотрение заявки на участие в обучающем курсе, urlsMap=null, applicationId=1, destinationEmail=pturskiy@clevertec.ru, templateFields={name=Дмитрий Стеба})
```
---
### Создание лога с помощью нового экземпляра класса и оправка его на сервер Elk

```text
ElkLog elkLog = new ElkLog(getClass());
elkLog.addMessage("Send processed template to email")
    .addField(LogFieldName.SENDER_EMAIL, senderEmail)
    .addField(LogFieldName.SENDER_EMAIL_ALIAS, senderEmailAlias)
    .addField(LogFieldName.DESTINATION_EMAIL, destinationEmail)
    .addField(LogFieldName.EMAIL_FOR_REPLY, emailForReply)
    .addField(LogFieldName.EMAIL_SUBJECT, emailSubject)
    .addField(LogFieldName.TEMPLATE_NAME, templateName)
    .print();
```

#### Сохранение лога в Elastic
```text
level:INFO level_value:20,000 port:54,531 traceId:a384311c9352d08a senderEmailAlias:Clevertec destinationEmail:pturskiy@clevertec.ru @version:1 message:Send processed template to email @timestamp:Jul 27, 2021 @ 10:35:27.983 emailForReply:info@clevertec.ru senderEmail:xradept.test@clevertec.ru emailSubject:Рассмотрение заявки на участие в обучающем курсе templateName:FILLING_APPLICATION_TEMPLATE host:192.168.56.1 logger_name:ru.clevertec.mail.api.service.impl.MailApiService thread_name:org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1 spanId:d81bb8dac4e00bb8 _id:5Yzj5noBTDr2KtQO2Nnn _type:_doc _index:testindex-2021.07.27 _score: -
```

#### Сохранение лога в консоль
```text
10:35:27.983 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  r.c.m.a.service.impl.MailApiService - message=Send processed template to email, senderEmail=xradept.test@clevertec.ru, senderEmailAlias=Clevertec, destinationEmail=pturskiy@clevertec.ru, emailForReply=info@clevertec.ru, emailSubject=Рассмотрение заявки на участие в обучающем курсе, templateName=FILLING_APPLICATION_TEMPLATE
```
---
### Переиспользование экземпляра класса для редактирования и отправки логов на сервер Elk
```text
elkLog.setMessage("Mail was sent successfully").print();
```

#### Сохранение лога в Elastic
```text
level:INFO level_value:20,000 port:54,531 traceId:a384311c9352d08a senderEmailAlias:Clevertec destinationEmail:pturskiy@clevertec.ru @version:1 message:Mail was sent successfully @timestamp:Jul 27, 2021 @ 10:35:33.589 emailForReply:info@clevertec.ru senderEmail:xradept.test@clevertec.ru emailSubject:Рассмотрение заявки на участие в обучающем курсе templateName:FILLING_APPLICATION_TEMPLATE host:192.168.56.1 logger_name:ru.clevertec.mail.api.service.impl.MailApiService thread_name:org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1 spanId:d81bb8dac4e00bb8 _id:5ozj5noBTDr2KtQO7tnT _type:_doc _index:testindex-2021.07.27 _score: -
```

#### Сохранение лога в консоль
```text
10:35:33.589 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  r.c.m.a.service.impl.MailApiService - message=Mail was sent successfully, senderEmail=xradept.test@clevertec.ru, senderEmailAlias=Clevertec, destinationEmail=pturskiy@clevertec.ru, emailForReply=info@clevertec.ru, emailSubject=Рассмотрение заявки на участие в обучающем курсе, templateName=FILLING_APPLICATION_TEMPLATE
```

Повторное использование экземпляра класса ElkLog удобно при добавлении или изменении уже существующих полей объекта перед отправкой лога на сервер Elk

