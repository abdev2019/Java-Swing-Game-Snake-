The architecture compose from five layers:

- --Entities layer contains all JPA entities
- --DAO layer contains all JPA repositories related to each JPA entity
- --Service layer contains all services classes related to each entity
- --Controller(WS) layer contains Product controller which provide all endpoints of the API
- --In addition we have a tools package contains all functions needed in service layer

As file functions (write Image file, read Image file, generate file&#39;s path, create directory of some product to store images files)

It contains also all needed custom exceptions to rewrite the http response:

- ProductNotFoundException : handled when the product related to requested ID not exist

Sent a status code 404 [NOT\_FOUND]

- RatingNotFoundException : handled when the rating related to requested ID not exist

Sent a status code 404 [NOT\_FOUND]

- ImageNotFoundException : handled when the image related to requested ID not exist

Sent a status code 404 [NOT\_FOUND]

- OnlyImagesAllowedException : handled when the file uploaded is not an image,

Sent a status code 406 [NOT\_ACCEPTABLE]

- ValidationExceptionHandler : handled when activating the validation of entities with @Valid anotation
-

This API provide many endpoints to consume data:

- -- **Adding** new Product  ----------------------------------------------------------------------------------------------

POST [/products](http://localhost:8080/products)

POST Body: there are two fields which are should respect validator: title should be at least two characters. In addition, price should be positive decimal

Example:

{

   &quot;title&quot;:&quot;example title&quot;,

   &quot;subtitle&quot;:&quot;example subtitle&quot;,

   &quot;description&quot;:&quot;example description&quot;,

   &quot;price&quot;: 5000.00

}

- -- **Updating** product

PUT or PATCH /products/{idProduct}

POST Body can contains only the field needed to update

Example:

{

   &quot;description&quot;:&quot;description updated&quot;,

   &quot;price&quot;: 6000.00

}

- -- **Deleting** a product

DELETE [ /products/{idProduct}](http://localhost:8080/products/%7bidProduct%7d)

If the product doesn&#39;t exist the response will contains a notification message



- -- **Fetch** products

GET  /products

The response always give at least empty array

We can **fetch** all products by keywords:

- **By title :** GET  /products?title=xxxx
- **By description :** GET  /products?description=xxxx
- **By all keywords :** GET  /products?title=xxxx&amp;subtitle=yyyy&amp;description=zzzz

Retrieving response as pageable type by adding request params size&amp;page&amp;sort

- GET /products?title=xxxx&amp;subtitle=yyyy&amp;description=zzzz
- GET /products?title=xxxx&amp;subtitle=yyyy&amp;description=zzzz&amp;page=0&amp;size=5&amp;sort=asc



- -- **Fetch** special product

GET  /products/{id}

The response will be a JSON product data

We can **fetch** only specific fields of product by adding _filterField_ param with values separated by comma:

- Fetch only ratings : GET /products/{id}?filterFields=ratings
- Fetch only images : GET /products/{id}?filterFields=images
- Fetch title and images : GET /products/{id}?filterFields=title,ratings

- -- **Adding** rating to product  ---------------------------------------------------------------------------------------

POST  /ratings?idProduct=xxx

The response will be a JSON rating data or can be a message to notify that the related product does not exist



- -- **Removing** rating

DELETE  /ratings/{id}

The response will be true on success or a message to notify that the rating does not exist



- -- **Uploading and affect** image to product --------------------------------------------------------------------------

POST  /images?idProduct=xxx

POST Body should has content-type as **multipart/form-data** and **image** as field name.

The response, on success, will be a JSON containing information about the image or a message to notify that the product does not exist, or that the type of file should be Image

- -- **Removing** image

DELETE  /images/{id}

The response will be true on success or a message to notify that the image does not exist



- -- **Loading** image resource

GET /images/{id}.jpeg

The response is image/jpeg content or message notify that the image not exist
