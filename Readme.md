# User Service

The User Service provides following operations for managing user data. It allows you to interact with user resources through RESTful endpoints. This document guide you to develop the understanding that how you can interact with endpoints, their functionality, and the expected request and response formats.

- Create: You can create a new user.
- Read: You can retrieve a specific user or all users.
- Delete: You can delete a specific user by id

## Base URL

```
http://localhost:8888/api/v1/user
```

All endpoints provided in this API should be appended to the base URL mentioned above. We can change the port in yaml if required.

## Endpoints

### Retrieve a User

```
GET /{id}
```

This endpoint retrieves information about a specific user identified by their `id`. 
The response will include details such as the id, firstname, and lastname information.

#### Example Request

```
GET /1
```

#### Example Response

if the record is found it will get the required data with 200 status code, if the record doesn't exist it will return 404 status code.

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Jerry"
}
```
If we pass a id which doesn't exist.
```json
{
  "code": "404",
  "message": "User with id 4 does not exist."
}
```

### Create a User

```
POST /
```

we can create a new user by passing the required data in the request body using a post request. You need to provide the user's details in the request body, including their first name and last name. If any required information is missing it will generate a validation error due to missing data.

#### Example Request

```json
{
  "firstName": "John",
  "lastName": "Jerry"
}
```

#### Example Response

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Jerry"
}
```
if the same data already exist

```json
{
  "code": "409",
  "message": "User with First Name John and Last Name Jerry already exist."
}
```
In case last name is missing in the request body.
```json
{
  "code": "400",
  "message": "Invalid request",
  "fieldErrors": [
    {
      "code": "NotBlank",
      "field": "lastName",
      "message": "Last name is a mandatory field."
    }
  ]
}
```

### Delete a User

```
DELETE /{id}
```

This endpoint allows you to delete a user resource identified by their `id`.

#### Example Request

```
DELETE /1
```

#### Example Response

```
User deleted Successfully with id.
```

## Error Handling

In case of errors, the API will respond with appropriate HTTP status codes and error messages. Here are some common error cases:

- `404 Not Found`: When a user with the specified `id` does not exist.
- `400 Bad Request`: When the request is invalid or missing required parameters.
- `409 Conflict`: When a duplicate found which indicates that the request could not be processed because of the conflict.

## Authentication and Authorization

Currently, User Service API is using basic authentication to access the endpoints. Please refer to the application.yml file to get the credentials information. 

## Swagger-UI
```
{server-url}/swagger-ui.html#
```
We have added swagger-ui as well to interact with API. Please use the above URL to access.


## Prerequisite 
Java 11 is required in order to run this project.