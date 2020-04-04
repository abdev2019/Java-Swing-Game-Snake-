# RESTfull Product Api
 #
 #
 #
 
 ### Architecture

![alt text](https://raw.githubusercontent.com/abdev2019/ProductApi/master/architecture.PNG)

The architecture compose from five layers:

-   Entities layer contains all JPA entities
-	DAO layer contains all JPA repositories related to each JPA entity
-	Service layer contains all services classes related to each entity
-	Controller(WS) layer contains Product controller which provide all endpoints of the API
-	In addition we have a tools package contains all functions needed in service layer
    >As file functions (write Image file, read Image file, generate file’s path, create directory of some product to store images files)

    >It contains also all needed custom exceptions to rewrite the http response: 
    -    `ProductNotFoundException` : handled when the product related to requested ID not exist 
         Sent a status code 404 [NOT_FOUND]
    -	`RatingNotFoundException` : handled when the rating related to requested ID not exist 
Sent a status code 404 [NOT_FOUND]
    -	`ImageNotFoundException` : handled when the image related to requested ID not exist
Sent a status code 404 [NOT_FOUND]
    -	`OnlyImagesAllowedException` : handled when the file uploaded is not an image, 
Sent a status code 406 [NOT_ACCEPTABLE]
    - `ValidationExceptionHandler` : handled when activating the validation of entities with @Valid anotation and sent  status code 400 [BAD_REQUEST]
 #
 #
<br/><br/><br/>
 
 ### This API provide many endpoints to consume data:
 
##### Adding new Product
#
```sh
POST /products
```
POST Body: there are two fields which are should respect validator: title should be at least two characters. In addition, price should be positive decimal 
Example:
```json
{
   "title" : "example title", 
   "subtitle" : "example subtitle", 
   "description" : "example description",
   "price" : 5000.00
} 
```
\
\
\

##### Updating product 
#
```sh
PUT or PATCH /products/{idProduct}
``` 

POST Body can contains only the field needed to update
Example:
```json
{ 
    "description" : "description updated",
    "price" : 6000.00
} 
```
  
 \
 \
 \
  
##### Deleting a product 
#
```sh
DELETE  /products/{idProduct}
``` 

If the product doesn’t exist the response will contains a notification message

   
 \
 \
 \
 
##### Fetch products 
#
```sh
GET  /products
``` 

The response always give at least empty array
We can fetch all products by keywords:
•	By title : `GET  /products?title=xxxx `
•	By description : `GET  /products?description=xxxx`
•	By all keywords : `GET  /products?title=xxxx&subtitle=yyyy&description=zzzz`
Retrieving response as pageable type by adding request params size&page&sort
•	`GET /products?title=xxxx&subtitle=yyyy&description=zzzz`
•	`GET /products?title=xxxx&subtitle=yyyy&description=zzzz&page=0&size=5&sort=asc`


 \
 \
 \
 
##### Fetch special product 
#
```sh
GET  /products/{id}
```  

The response will be a JSON product object
We can fetch only specific fields of product by adding filterField param with values separated by comma: 
•	Fetch only ratings : `GET /products/{id}?filterFields=ratings`
•	Fetch only images : `GET /products/{id}?filterFields=images`
•	Fetch title and images : `GET /products/{id}?filterFields=title,ratings`


 \
 \
 \
 
##### Adding rating to product  
#
```sh
POST  /ratings?idProduct=xxx
```   

The response will be a JSON rating data or can be a message to notify that the related product does not exist


 \
 \
 \
 
##### Removing rating 
#
```sh
DELETE  /ratings/{id}
```     
The response will be true on success or a message to notify that the rating does not exist


 \
 \
 \
 
##### Uploading and affect image to product 
#
```sh
POST  /images?idProduct=xxx
```      

POST Body should has content-type as multipart/form-data and image as field name. 
The response, on success, will be a JSON containing information about the image or a message to notify that the product does not exist, or that the type of file should be Image


 \
 \
 \
 
##### Removing image 
#
```sh
DELETE  /images/{id}
```    

The response will be true on success or a message to notify that the image does not exist


 \
 \
 \
 
##### Loading image resource 
#
```sh
GET /images/{id}.jpeg
```     
The response's content is image/jpeg  or a message to notify that the image not exist

 
#
#
#
#
#
# 
 
 
  
