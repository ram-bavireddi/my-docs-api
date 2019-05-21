# my-docs-api

## How do I run in my local
`mvn spring-boot:run`

## Get Access Token
- Open http://127.0.0.1:8080/my-docs
- Enter your email address
- Click on Let's Drive in button
- Sign in With Google
- Copy the token in the next screen

## And use the token in a rest client, for example, Postman
- To list files from your Google Drive
- To upload a file
- To download a file
- To create a folder
- To upload a file to a folder

## Sample CURL requests
#### list files
`curl -X GET http://localhost:8080/my-docs/api/files -H 'Authorization: Bearer <token>' -H 'x-username: ramu.bavireddi@gmail.com'`
#### create a folder
`curl -X POST http://localhost:8080/my-docs/api/folders -H 'Authorization: Bearer <token>'-H 'Content-Type: application/json' -H 'x-username: ramu.bavireddi@gmail.com' -d '{"name": "MyDocs"}'`
