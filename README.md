# Unitap Business Cards API
---
# Эндпоинты

## Authentication

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

## Profile

### Получение профиля
- **GET** `/api/v1/profile/{id}`
    - Описание: Получает профиль пользователя по его ID.
    - Параметры:
        - `id` (UUID, path): идентификатор пользователя
    - Ответ:
      ```json
      {
        "id": "uuid",
        "fullName": "Иван Иванов",
        "nickName": "ivan",
        "avatarUrl": "https://example.com/avatar.jpg",
        "phone": "+79991234567",
        "email": "ivan@example.com",
        "company": "ООО Ромашка",
        "position": "Разработчик",
        "description": "Описание профиля",
        "portfolio": "https://portfolio.example.com"
      }
      ```

### Обновление профиля
- **PATCH** `/api/v1/profile/{id}`
    - Описание: Обновляет имя и контакты пользователя
    - Параметры:
        - `id` (UUID, path): идентификатор пользователя
        - Тело запроса:
          ```json
          {
            "fullName": "Иван Иванов",
            "phoneNumber": "+79991234567",
            "email": "ivan@example.com",
            "company": "ООО Ромашка",
            "position": "Разработчик",
            "description": "Описание профиля",
            "portfolio": "https://portfolio.example.com"
          }
          ```
    - Ответ:
      ```json
      {
        "id": "uuid",
        "fullName": "Иван Иванов",
        "nickName": "ivan",
        "avatarUrl": "https://example.com/avatar.jpg",
        "phone": "+79991234567",
        "email": "ivan@example.com",
        "company": "ООО Ромашка",
        "position": "Разработчик",
        "description": "Описание профиля",
        "portfolio": "https://portfolio.example.com"
      }
      ```

### Загрузка аватарки
- **POST** `/api/v1/profile/{id}/avatar`
    - Описание: Загружает URL аватарки пользователя и сохраняет в Firestore
    - Параметры:
        - `id` (UUID, path): идентификатор пользователя
        - Тело запроса:
          ```json
          {
            "avatarUrl": "https://example.com/avatar.jpg"
          }
          ```
    - Ответ:
      ```json
      {
        "id": "uuid",
        "fullName": "Иван Иванов",
        "nickName": "ivan",
        "avatarUrl": "https://example.com/avatar.jpg",
        "phone": "+79991234567",
        "email": "ivan@example.com",
        "company": "ООО Ромашка",
        "position": "Разработчик",
        "description": "Описание профиля",
        "portfolio": "https://portfolio.example.com"
      }
      ```

### Выбор шаблона визитки
- **PUT** `/api/v1/profile/{userId}/card/template?templateId={templateId}`
    - Описание: Позволяет пользователю выбрать шаблон визитки
    - Параметры:
        - `userId` (UUID, path): идентификатор пользователя
        - `templateId` (UUID, query): идентификатор шаблона визитки
    - Ответ:
      ```json
      {
        "userId": "uuid",
        "templateId": "uuid",
        "data": "2024-07-01T12:00:00Z"
      }
      ```
      
## Card

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