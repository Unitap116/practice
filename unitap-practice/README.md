# API Endpoints

## POST /api/v1/register

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

## GET /api/v1/admin/users

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

## DELETE /api/v1/admin/users/{id}

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
