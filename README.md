Собранный .zip файл находится в target\releases\.

# Процесс установки elasticsearch
1. Скачать архив https://www.elastic.co/downloads/elasticsearch
2. Распаковать его содержимое
3. Создать пользователя cmd: *.\elasticsearch-users useradd \*username\* -p \*password\* -r superuser*
   
# Процесс установки плагина для локально установленного elasticsearch
1. Скачать из данного репозитория плагин target/releases/tika-test-1.0-SNAPSHOT.zip
2. Зайти в папку elasticsearch-v.v.v/bin
3. Установить плагин cmd: *.\elasticsearch-plugin install file:///"\*путь к плагину\*"*
5. Запустить elasticsearch cmd: *.\elasticsearch*

# Отправка запросов
*Все запросы делать с Basic auth. Логин и пароль задавались командой .\elasticsearch-users useradd \*username\* -p \*password\* -r superuser*
## 1. Добавление процессора.
   ### Method: PUT
   ### URL: localhost:9200/_ingest/pipeline/base64_pipeline
   ### Body:
   {
    "description": "base64 decode",
    "processors": [
        {
            "metadata_extractor":{
                "field":"base64",
                "target_field":"metadata"
            }
        }
    ]
}
## 2. Добавление документа в индекс. 
### Method: POST
### URL: http:\\localhost:9200\my_index\_doc?pipeline=base64_pipeline
### Body:
   {
  "title": "First Document",
  "description": "This is the description of the first document.",
  "timestamp": "2024-06-06T10:00:00Z",
  "base64":"dGVzdAo="
}
## 3. Просмотр добавленных документов. 
### Method: GET
### URL: localhost:9200\my_index\_search
