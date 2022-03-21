# Parking spots - Java 
First recruitment task for the first stage of **intive Patronage 2022**. 
Simple Java and Spring Boot based system for parking spots booking at the parking lot. Application provides the very basic **REST API** to perform domain operations using HTTP.  
## Requirements  
- Java 11 or higher (tested with Amazon Corretto 11)  
## Build and run  
Application has been created as a **Maven** based project. Each of the project lifecycle events shall be called by the **Maven Wrapper** included in the project directory.   

Run tests  
` ./mvnw test`
  
Build and package (without testing)  
`./mvnw package -Dmaven.test.skip=true `

Run packaged application  
`java -jar target/parking-lot-intive-patronage-1.0.0-RELEASE.jar`
## Concept  
Application uses **PostgreSQL** database which can be migrated with **Flyway** and SQL scripts contained in `resources/db directory`
  
Application runs on **8080** port by default. It provides several endpoints to perform basic domain operations described in task specification. Communication shall be done by using **HTTP** protocol. Response bodies are returned as **JSON**.  
## API endpoints
The following endpoints allow performing basic domain operations described in the task requirements. They can be tested with tools such as **cURL** or **Postman**.
### GET 
`host:port`[/api/parking-spots/search/by-owner](#get-apiparking-spotssearchby-owner)  
`host:port`[/api/parking-spots/seach/by-availability](#get-apiparking-spotssearchby-owner) 
___
### POST  
`host:port`[/api/users/{userId}/booked-spots/{spotId}](#post-apiusersuseridbooked-spotsspotid)
___
### DELETE  
`host:port`[/api/users/{userId}/booked-spots/{spotId}](#delete-apiusersuseridbooked-spotsspotid)

___
### GET /api/parking-spots/search/by-owner
Fetches all booked parking spots by user defined by `userId` query parameter.

**Parameters**
| Name     | Required  | Type | Located in | Description
|:--------:|:---------:|:----:|:----------:|:---------:
| `userId` | mandatory | Long | query | User ID for which to fetch the parking spots. <br> <br> Supported values: `1 or higher`.

**Sample call with cURL**
```
curl -i localhost:8080/api/parking-spots/search/by-owner?id=1
```
**Response**

Status: 200 OK
```
{
  "_embedded" : {
    "parking-spots" : [ {
      "id" : 1,
      "number" : 1,
      "storey" : 1,
      "disability" : false,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/parking-spots/1"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/parking-spots/search/by-owner?id=1"
    }
  }
}
```
___
### GET /api/parking-spots/search/by-availability

Fetches all available or not available parking spots in the system depending on the `available` parameter equal to true or false, respectively.

**Parameters**
| Name        | Required  | Type    |Located in| Description
|:-----------:|:---------:|:-------:|:----: |:---------:
| `available` | mandatory | Boolean | query |Decides whether the parking spots to be fetched are to be available or booked. <br> <br> Supported values: `true` or `false`.

**Sample call with cURL**
```
curl -i localhost:8080/api/parking-spots/search/by-availability?available=true
```
**Response**

Status: 200 OK
```
{
  "_embedded" : {
    "parking-spots" : [ {
      "id" : 2,
      "number" : 2,
      "storey" : 1,
      "disability" : true,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/parking-spots/2"
        }
      }
    }, {
      "id" : 5,
      "number" : 5,
      "storey" : 1,
      "disability" : false,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/parking-spots/5"
        }
      }
    }, {
      "id" : 4,
      "number" : 4,
      "storey" : 1,
      "disability" : true,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/parking-spots/4"
        }
      }
    }, {
      "id" : 3,
      "number" : 3,
      "storey" : 1,
      "disability" : false,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/parking-spots/3"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/parking-spots/search/by-availability?available=true"
    }
  }
}
```
___
### POST /api/users/{userId}/booked-spots/{spotId}
Creates a booking of a parking spot defined by `spotId` for the user defined by `userId`.

**Parameters**
| Name        | Required  | Type |Located in| Description
|:-----------:|:---------:|:----:|:-----: |:---------:
| `userId` | mandatory | Long |path| User ID for which to create a booking. <br><br> Supported values: `1 or higher`.
| `spotId` | mandatory | Long |path| Booked parking spot ID. <br><br> Supported values: `1 or higher`.

**Sample call with cURL**
```
curl -i -X POST localhost:8080/api/users/1/booked-spots/2
```
**Response**

Status: 204 NO CONTENT
```
No response body
```
___
### DELETE /api/users/{userId}/booked-spots/{spotId}
Deletes the booking of a parking spot defined by `spotId` for the user defined by `userId`.

**Parameters**
| Name        | Required  | Type |Located in| Description
|:-----------:|:---------:|:----:|:-----:|:---------:
| `userId` | mandatory | Long |path| User ID for which to create a booking <br><br> Supported values: `1 or higher`.
| `spotId` | mandatory | Long |path|Booked parking spot ID <br><br> Supported values: `1 or higher`.

**Sample call with cURL**
```
curl -i -X DELETE localhost:8080/api/users/1/booked-spots/1
```
**Response**

Status: 204 NO CONTENT
```
No response body
```
___
