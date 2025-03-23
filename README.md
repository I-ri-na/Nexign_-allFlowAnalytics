# Nexign_СallFlowAnalytics
# Технические требования

## Язык
- **Java** (OpenJDK 17)

## Инструменты
- **OpenJDK 17** — версия Java, используемая для разработки и запуска приложения.
- **Maven/Gradle** — системы сборки проекта.
- **Spring Boot** — фреймворк для создания микросервисов и веб-приложений.
- **H2 Database** — встроенная база данных, используемая для хранения данных.

---

 # Задачи и условия

## Задача 1: Генерация CDR-записей
Напишите часть, эмулирующую работу коммутатора, т.е. генерирующую CDR-записи.

**Условия:**
1. Во время генерации создаются CDR-записи и сохраняются в локальную БД (H2).
2. Данные генерируются в хронологическом порядке звонков, т.е. записи по одному абоненту могут прерываться записями по другому абоненту.
3. Количество и длительность звонков определяются случайным образом.
4. Установленный список абонентов (не менее 10) хранится в локальной БД (H2).
5. Один прогон генерации создает записи сразу за 1 год.

---

## Задача 2: REST API для работы с UDR
Напишите часть, предоставляющую REST API для работы с UDR.

**Условия:**
1. Требуется REST-метод, который возвращает UDR-запись (формат предоставлен выше) по одному переданному абоненту. В зависимости от переданных в метод параметров, UDR должен составляться либо за запрошенный месяц, либо за весь тарифицируемый период.
2. Требуется REST-метод, который возвращает UDR-записи по всем нашим абонентам за запрошенный месяц.
3. Данные можно брать только из БД.

---

## Задача 3 (дополнительная): Генерация CDR-отчетов
Напишите часть, формирующую и сохраняющую CDR-отчет.

**Условия:**
1. Напишите REST-метод, который инициирует генерацию CDR-отчета и возвращает успешный ответ (или текст ошибки) + уникальный UUID запроса, когда файл будет готов.
2. CDR-файл должен генерироваться для запрошенного абонента за переданный период времени. Переданный период может не совпадать с календарными месяцами. Например, можно запросить отчет по звонкам за две недели или за полгода.
3. Данные можно брать только из БД.
4. Сгенерированный файл может быть в формате CSV или TXT и располагаться в рабочей папке сервиса, в директории `/reports`.
5. Название файла должно содержать номер пользователя и уникальный UUID запроса. Например: `79991112233_61f0c404-5cb3-11e7-907b-a6006ad3dba0.csv`.

---


