# Product Service

Product service is a project develop as proof of concept to test Spring Boot to build a RESTful WebService. This project also consumes remote services.

## How to execute

```sh
$ git clone https://github.com/vhblasco/package_service.git
$ cd package_service
$ mvn install
$ java -jar ./target/package_service-0.0.1-SNAPSHOT.jar
```
- Requeriments: The project needs Java 8 to run and Maven 2 to build it.

## Endpoints
| METHOD | ENDPOINT | DESCRIPTION |
| ------ | ------ | ------ |
| GET | http://localhost:8080/packages | Get all packages. By default the price will be in USD |
| GET | http://localhost:8080/packages?currency={currency} | Get all packages in the given currency. |
| GET | http://localhost:8080/packages/package/{packageId} | Get the package with id = packageId. By default the price will be in USD |
| GET | http://localhost:8080/packages/package/{packageId}?currency={currency} | Get the package with id = packageId. The price will be in the given currency |
| PUT | http://localhost:8080/packages/package | Add a new package if not exists the packageId |
| POST | http://localhost:8080/packages/package | Update the package with the id = packageId. if not exists then creates it |
| POST | http://localhost:8080/packages/package/{packageId} | Deletes the package with the id = packageId. |

## Complete Cicle
- GET http://localhost:8080/packages

Response:
```
[]
```
- PUT http://localhost:8080/packages/package
```
{
"id": "1111",
"name": "package name",
"description": "package description",
"productsIds": ["VqKb4tyj9V6i", "DXSQpv6XVeJm"]
}
```
Then GET response:
```
[
   {
      "id": "1111",
      "name": "package name",
      "description": "package description",
      "price": 21.48,
      "products": [
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         }
      ],
      "productsIds": [
         "VqKb4tyj9V6i",
         "DXSQpv6XVeJm"
      ]
   }
]
```
- POST  http://localhost:8080/packages/package
```
 {
      "id": "1111",
      "name": "package name updated",
      "description": "package description",
      "price": 21.48,
      "products": [
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         }
      ],
      "productsIds": [
         "VqKb4tyj9V6i",
         "DXSQpv6XVeJm"
      ]
   }
```
Then GET Response:
```
[
   {
      "id": "1111",
      "name": "package name updated",
      "description": "package description",
      "price": 42.96,
      "products": [
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         },
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         }
      ],
      "productsIds": [
         "VqKb4tyj9V6i",
         "DXSQpv6XVeJm"
      ]
   }
]
```
- PUT http://localhost:8080/packages/package
```
{
"id": "2222",
"name": "package 2 name",
"description": "package  2 description",
"productsIds": ["IJOHGYkY2CYq", "8anPsR2jbfNW"]
}
```
Then GET Response:
```
[
   {
      "id": "1111",
      "name": "package name updated",
      "description": "package description",
      "price": 42.96,
      "products": [
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         },
         {
            "id": "VqKb4tyj9V6i",
            "name": "Shield",
            "usdPrice": 1149
         },
         {
            "id": "DXSQpv6XVeJm",
            "name": "Helmet",
            "usdPrice": 999
         }
      ],
      "productsIds": [
         "VqKb4tyj9V6i",
         "DXSQpv6XVeJm"
      ]
   },
   {
      "id": "2222",
      "name": "package 2 name",
      "description": "package  2 description",
      "price": 6.99,
      "products": [
         {
            "id": "IJOHGYkY2CYq",
            "name": "Bow",
            "usdPrice": 649
         },
         {
            "id": "8anPsR2jbfNW",
            "name": "Silver Coin",
            "usdPrice": 50
         }
      ],
      "productsIds": [
         "IJOHGYkY2CYq",
         "8anPsR2jbfNW"
      ]
   }
]
```
- GET http://localhost:8080/packages/1111
Response:
```
{
   "id": "1111",
   "name": "package name updated",
   "description": "package description",
   "price": 42.96,
   "products": [
      {
         "id": "VqKb4tyj9V6i",
         "name": "Shield",
         "usdPrice": 1149
      },
      {
         "id": "DXSQpv6XVeJm",
         "name": "Helmet",
         "usdPrice": 999
      },
      {
         "id": "VqKb4tyj9V6i",
         "name": "Shield",
         "usdPrice": 1149
      },
      {
         "id": "DXSQpv6XVeJm",
         "name": "Helmet",
         "usdPrice": 999
      }
   ],
   "productsIds": [
      "VqKb4tyj9V6i",
      "DXSQpv6XVeJm"
   ]
}
```
- DELETE http://localhost:8080/packages/package/1111
Then GET Response:
```
[
   {
      "id": "2222",
      "name": "package 2 name",
      "description": "package  2 description",
      "price": 6.99,
      "products": [
         {
            "id": "IJOHGYkY2CYq",
            "name": "Bow",
            "usdPrice": 649
         },
         {
            "id": "8anPsR2jbfNW",
            "name": "Silver Coin",
            "usdPrice": 50
         }
      ],
      "productsIds": [
         "IJOHGYkY2CYq",
         "8anPsR2jbfNW"
      ]
   }
]
```
- GET http://localhost:8080/packages?currency=GBP
(Currency at writing time 1USD=0.74988GBP)
Response:
```
[
   {
      "id": "2222",
      "name": "package 2 name",
      "description": "package  2 description",
      "price": 5.2416612,
      "products": [
         {
            "id": "IJOHGYkY2CYq",
            "name": "Bow",
            "usdPrice": 649
         },
         {
            "id": "8anPsR2jbfNW",
            "name": "Silver Coin",
            "usdPrice": 50
         }
      ],
      "productsIds": [
         "IJOHGYkY2CYq",
         "8anPsR2jbfNW"
      ]
   }
]
```

