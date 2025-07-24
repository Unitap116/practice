# Unitap Business Cards API

## Эндпоинты

### Получение визитки
- **GET** `/card/{userId}`
    - Описание: Получает визитку пользователя по его ID
    - Параметры:
        - `userId` - идентификатор пользователя
    - Ответ:
      ```json
      {
        "id": "string",
        "name": "Иван Иванов",
        "isPublic": true,
        "views": 123,
        "template": "modern",
        "contacts": {
          "phone": "+79991234567",
          "email": "ivan@example.com"
        }
      }
      ```
    - Ошибки:
        - `404 Not Found`: CardNotFoundException - если визитка не найдена
        - `403 Forbidden`: CardAccessDeniedException - если визитка не публичная (isPublic = false)

### Получение QR-кода визитки
- **GET** `/card/{userId}/qr`
    - Описание: Генерирует QR-код со ссылкой на визитку пользователя
    - Параметры:
        - `userId` - идентификатор пользователя
    - Ответ:
        - `image/png`: Массив байтов PNG изображения QR-кода
        - Содержит ссылку: `/card/{userId}`