# Unitap Business Cards API

## Эндпоинты

### POST /api/v1/register

Регистрация нового пользователя.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "super-secret-password"
}
```

**Response (201 Created):**
```json
{
  "id": "abc123",
  "email": "user@example.com",
  "role": "USER"
}
```

**Ошибки:**
- 400 Bad Request — Некорректные данные формы


---

### GET /api/v1/admin/users

Получить список всех зарегистрированных пользователей (доступно только админам).

**Headers:**

Authorization: Bearer <access_token>

**Response (200 OK):**
```json
[
  {
    "id": "123",
    "email": "admin@example.com",
    "role": "ADMIN"
  },
  {
    "id": "456",
    "email": "user@example.com",
    "role": "USER"
  }
]
```

**Ошибки:**
- 403 Forbidden — Недостаточно прав


---

### DELETE /api/v1/admin/users/{id}

Удалить пользователя по ID (доступно только админам).

**Path Parameter:**
- `id` — идентификатор пользователя

**Headers:**

Authorization: Bearer <access_token>


**Response (204 No Content):**  
Пользователь успешно удалён.

**Ошибки:**
- 403 Forbidden — Недостаточно прав
- 404 Not Found — Пользователь не найден

### Получение визитки
- **GET** `/api/v1/card/{userId}`
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
- **GET** `/api/v1/card/{userId}/qr`
    - Описание: Генерирует QR-код со ссылкой на визитку пользователя
    - Параметры:
        - `userId` - идентификатор пользователя
    - Ответ:
        - `image/png`: Массив байтов PNG изображения QR-кода
        - Содержит ссылку: `/api/v1/card/{userId}`