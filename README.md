## Training Blog

Web App written in Spring Framework which is a blog template.
Application allows to:
* Register new user
* Reset password
* Change credentials for registered user
* For not logged users:
  * view posts
  * write a message
  * filter posts by tag
  * search post by phase
* For logged users:
  * with role: USER:
    * view posts
    * add comments
  * with role: MODERATOR (same as USER) and:
    * add new post
    * edit own post
    * answer to message by MailService
  * with role: ADMIN (same as USER) and:
    * add new post
    * edit post
    * delete post
    * delete comment
    * answer to message by MailService
    * change user's privileges
    * add tag
    * delete tag
Invalid requests are handled by exception handlers.

## Built with

* Spring Boot
* Maven
* Sprint Data JPA
* Spring Security
* H2 Database - in dev enviroment
* PostgreSQL
* Thymeleaf
* Spring Mail
* Lombok
* Jasypt

## Default credentials
* for user:
  * login: user
  * password: user
* for moderator:
  * login: moderator
  * password: moderator
* for admin:
  * login: admin
  * password: avaiable on demand*
## Live demo:
 Application is available under:
https://training-blog-web.herokuapp.com/

## Authors

* [Grzegorz Konopka](https://github.com/konopkagrzegorz)

## License

This project is an open source application
