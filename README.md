# Backend-Practical-Exam


## API Reference

### Register New User

```
  POST /register
  Host: localhost:8080
  Content-Type: application/json

  {
    "email": <string>
    "password": <string>
  }
```
---
#### Successful Response:
Status Code: ```201 Created```
```
{
  "message": "User successfully registered"
}
```
---
#### Failed Response (Email Already Taken):
Status Code: ```400 Bad Request```
```
{
  "message": "Email already taken"
}
```

---
---
---

### Login Existing User

```
  POST /login
  Host: localhost:8080
  Content-Type: application/json

  {
    "email": <string>,
    "password": <string>
  }
```
---
#### Successful Response
Status Code: ```201 Created```
```
{
  "access_token": <token>
}
```
---
#### Failed Response (Email Already Taken):
Status Code: ```401 Unauthorized```
```
{
  "message": "Invalid Credentials"
}
```

---
---
---

### Place an Order

```
  POST /order
  Host: localhost:8080
  Content-Type: application/json
  Authorization: Bearer <access_token>

  {
    "product id": <string>,
    "quantity": <string>
  }
```
---
#### Successful Response:
Status Code: ```201 Created```
```
{
  "message": "You have successfully ordered this product"
}
```
---
##### Failed Response (Invalid Credentials):
Status Code: ```400 Bad Request```
```
{
  "message": "Failed to order this product due to unavailability of the stock"
}
```