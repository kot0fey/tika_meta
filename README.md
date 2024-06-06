Собранный .zip файл находится в target\releases\.

# Процесс установки elasticsearch
1. Скачать архив https://www.elastic.co/downloads/elasticsearch
2. Распаковать его содержимое
3. Создать пользователя cmd: *.\elasticsearch-users useradd adminn -p adminn -r superuser*
   
# Процесс установки для локально установленного elasticsearch
1. Скачать из данного репозитория плагин target/releases/tika-test-1.0-SNAPSHOT.zip
2. Зайти в папку elasticsearch-v.v.v/bin
3. Установить плагин cmd: *.\elasticsearch-plugin install file:///"*путь к плагину*"*
5. Запустить elasticsearch cmd: *.\elasticsearch*
