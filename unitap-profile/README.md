# Unitap Profile API

## Эндпоинты

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

---
