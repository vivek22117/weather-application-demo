## Weather Application to search the weather data and history for a city

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Local Testing](#local-testing-via-postman)
* [GitHub Statistics](#my-github-statistics)



## General info
This project has two modules, backend [weather-api] and frontend [weather-ui].
> * Weather API Swagger Documentation link: http://localhost:8001/swagger-ui/#/
> * Frontend Application can be accessed via:  http://localhost:5000/
> * Backend H2 database can be accessed via: http://localhost:8001/h2-console/login.jsp [username: test, password:test]
> * Use `local` spring boot profile to run the application
> * Application log file will be generated in the `log` folder at root directory


## Technologies
Project is being developed using:
* Java8 & Spring Boot for backend API
* H2 for backend Database
* Swagger for API documentation
* ReactJS for frontend  


## Testing via ReactJS 
> Start react application using `npm start` command from `weather-ui` module
> The npm start command will take around 3-5 minutes to start
> the application on port. Access link `http://localhost:5000`: 
## Login Page
![Login Page](./images/Home_Page_Without_login.JPG)

## SignUp or SignIn Page:
![SignUp Or SignIn Page](./images/Login_Or_SignUp_Screen.JPG)

## Swagger API Document Link: `http://localhost:8001/swagger-ui/#/`
![Swagger Doc Image](./images/Swagger_API_doc.JPG)


## Local testing via PostMan
> * Register a new user [POST API]: `http://localhost:8001/api/auth/signup`
 ```
{
    "username": "aws@gmail.com",
    "password": "abcd@1234",
    "dob": "1987-03-14"
}
```
> * Generate jwt token using below api using any API client application [Postman, ARC]
``` 
POST API: http://localhost:8001/api/auth/login
JSON Body: { "username": "ll@gmail.com", "password": "abcd@1234"}
```
> * Use the generated token to make an API call by passing below header:
> `Authorization: Bearer <TOKEN VALUE>` 


## Note
> Management tab is being used for `Administration`, for this application all the registered user will be considered as admin.


<!-- CONTACT -->
## Contact

Vivek Mishra - [@linkedin](https://www.linkedin.com/in/vivek-mishra-22aa44bb55cc/) - vivekkmishra2020@gmail.com


<!-- GitHub Stats -->
## My GitHub Statistics

![Vivek Mishra github stats](https://github-readme-stats.vercel.app/api?username=vivek22117&show_icons=true&theme=tokyonight)
<img src="https://github-readme-streak-stats.herokuapp.com/?user=vivek22117&theme=tokyonight" alt="mystreak"/>
![Vivek's Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=vivek22117&theme=tokyonight&layout=compact)