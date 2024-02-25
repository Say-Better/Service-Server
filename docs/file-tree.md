# File Tree
> this document is for the file tree of the SayBetter project.

## Overall structure

```bash
📦src
 ┗ 📂main
   ┣ 📂generated
   ┣ 📂java
   ┃ ┗ 📂com
   ┃   ┗ 📂saybetter
   ┃     ┣ 📂client # client package for communication with other services
   ┃     ┃ ┣ 📂api
   ┃     ┃ ┃ ┗ 📂dto
   ┃     ┃ ┗ 📂converter
   ┃     ┣ 📂domain # domain package for main api service
   ┃     ┃ ┗ 📂temp
   ┃     ┃   ┣ 📂application
   ┃     ┃   ┣ 📂dao
   ┃     ┃   ┣ 📂domain
   ┃     ┃   ┣ 📂exception
   ┃     ┃   ┗ 📂ui
   ┃     ┣ 📂global # global package for using setting, auth, common, jwt, utils ... etc
   ┃     ┃ ┣ 📂auth
   ┃     ┃ ┣ 📂common
   ┃     ┃ ┣ 📂config
   ┃     ┃ ┣ 📂jwt
   ┃     ┃ ┗ 📂utils
   ┃     ┗ 📜SayBetterApplication.java
   ┗ 📂resources # resource for spring boot application
```

## Client structure
> it's example for Client structure

```bash
📦client
 ┣ 📂api
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📜ExampleRequest.java
 ┃ ┃ ┣ 📜ExampleResponse.java
 ┃ ┃ ┗ 📜ExampleResult.java
 ┃ ┣ 📜ExampleApi.java
 ┃ ┗ 📜ExampleClient.java
 ┣ 📂converter
 ┃ ┗ 📜ResultConverter.java
 ┗ 📂exception
    ┗ 📜ClientException.java
```

### description

| package | description |
| --- | --- |
| `api` | package for api communication |
| `dto` | package for data transfer object |
| `converter` | package for converting \*response to \*result |
| `exception` | package for handling exception |

- What is the `Result`?
  - `Response` is a dto class that is just used for communication with other services.
  - `Result` is a dto class that has enabled response to be used by the service server
  - so, `Response` is converted to `Result` by `ResultConverter` and then it is used in the service layer.

## Domain structure
> it's example for Domain structure

```bash
📦domain
 ┗ 📂temp
   ┣ 📂application
   ┃ ┣ 📂converter
   ┃ ┃ ┣ 📜TempConverter.java
   ┃ ┃ ┗ 📜TempResponseConverter.java
   ┃ ┣ 📂impl
   ┃ ┃ ┗ 📜TempService.java
   ┃ ┗ 📜TempFacade.java
   ┣ 📂dao
   ┃ ┗ 📂repository
   ┃   ┗ 📜TempRepository.java
   ┣ 📂domain
   ┃ ┗ 📜Temp.java
   ┣ 📂exception
   ┃ ┗ 📜TempException.java
   ┗ 📂ui
     ┣ 📂dto
     ┃ ┣ 📜TempRequest.java
     ┃ ┗ 📜TempResponse.java
     ┗ 📜TempController.java 
```

### description

| package | description |
| --- | --- |
| `application` | application layer for service logic |
| `converter` | converter for dto to entity and entity to dto |
| `impl` | implementation class for service logic |
| `Facade` | facade for service logic |
| `dao` | data access layer for database communication |
| `domain` | domain layer for entity |
| `exception` | exception layer for handling exception |
| `ui` | ui layer for controller, dto |

### Domain Type

| Type | description | 
| --- | --- |
| `member` | member domain |
| `review` | domain for solution review |
| `solution` | domain for education solution |
| `symbol` | symbol domain |

## Global structure
> it's example for Global structure

```bash
📦global
 ┣ 📂auth
 ┃ ┣ 📂exception
 ┃ ┃ ┗ 📜AuthException.java
 ┃ ┣ 📂handler
 ┃ ┃ ┣ 📜OAuth2LoginFailureHandler.java
 ┃ ┃ ┗ 📜OAuth2LoginSuccessHandler.java
 ┃ ┣ 📂service
 ┃ ┃ ┗ 📜CustomOAuth2UserService.java
 ┃ ┣ 📂userInfo
 ┃ ┃ ┣ 📜GoogleOAuth2UserInfo.java
 ┃ ┃ ┗ 📜OAuth2UserInfo.java
 ┃ ┣ 📜CustomOAuth2User.java
 ┃ ┗ 📜OAuthAttributes.java
 ┣ 📂common
 ┃ ┣ 📂code
 ┃ ┃ ┣ 📂status
 ┃ ┃ ┃ ┣ 📜ErrorStatus.java
 ┃ ┃ ┃ ┗ 📜SuccessStatus.java
 ┃ ┃ ┣ 📜BaseCode.java
 ┃ ┃ ┗ 📜BaseErrorCode.java
 ┃ ┣ 📂constant
 ┃ ┃ ┣ 📜Provider.java
 ┃ ┃ ┣ 📜RoleType.java
 ┃ ┃ ┗ 📜Status.java
 ┃ ┣ 📂entity
 ┃ ┃ ┗ 📜BaseTimeEntity.java
 ┃ ┣ 📂exception
 ┃ ┃ ┣ 📜ExceptionAdvice.java
 ┃ ┃ ┗ 📜GeneralException.java
 ┃ ┗ 📂response
 ┃ ┃ ┣ 📜JwtTokenResponseDto.java
 ┃ ┃ ┗ 📜ResponseDto.java
 ┣ 📂config
 ┃ ┣ 📂docs
 ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┣ 📂properties
 ┃ ┃ ┣ 📜JwtProperties.java
 ┃ ┃ ┗ 📜PropertiesConfig.java
 ┃ ┣ 📂security
 ┃ ┃ ┗ 📜SecurityConfig.java
 ┃ ┗ 📂web
 ┃ ┃ ┣ 📜CorsConfig.java
 ┃ ┃ ┗ 📜OpenFeignConfig.java
 ┣ 📂jwt
 ┃ ┣ 📂filter
 ┃ ┃ ┗ 📜JwtAuthenticationProcessingFilter.java
 ┃ ┗ 📂service
 ┃ ┃ ┗ 📜JwtService.java
 ┗ 📂utils
   ┣ 📜CodeUtil.java
   ┣ 📜RedisUtil.java
   ┗ 📜SecurityUtil.java
```

### description

| package | description | key scope |
| --- | --- | --- |
| `auth` | package for authentication | `OAuth2` |
| `common` | package for common setting | `error_code`, `constant`, `base_entity`, `exception`, `response` |
| `config` | package for spring boot configuration | `docs`, `properties`, `security`, `web` |
| `jwt` | package for jwt setting | `filter`, `service` |
| `utils` | package for util setting | `code generation`, `redis`, `security` |
