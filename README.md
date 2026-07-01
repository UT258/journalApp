# Journal Application

A Spring Boot REST API for private journaling with MongoDB persistence, stateless JWT security, per-user entry isolation, tag-based categorization, and lightweight sentiment trend analysis.

## Features

- **JWT authentication**
  - `POST /public/signup` registers a user.
  - `POST /public/login` returns a bearer token for authenticated requests.
  - Spring Security runs statelessly; protected `/journal/**` and `/user/**` endpoints require `Authorization: Bearer <token>`.

- **Per-user journal CRUD**
  - `GET /journal` lists only the authenticated user's entries.
  - `POST /journal` creates an entry for the authenticated user.
  - `GET /journal/{id}`, `PUT /journal/{id}`, and `DELETE /journal/{id}` verify ownership before reading, updating, or deleting an entry.

- **Tags and categorization**
  - Entries support a `tags` array.
  - `GET /journal?tag=<tag>` filters the authenticated user's entries by tag.

- **Sentiment analysis and trends**
  - Entries are auto-scored with a simple NLP lexicon and classified as `POSITIVE`, `NEUTRAL`, or `NEGATIVE`.
  - `GET /journal/trends` aggregates sentiment counts for the authenticated user.

- **Swagger/OpenAPI**
  - Springdoc exposes the API docs at `/swagger-ui.html` and `/v3/api-docs`.

## Tech Stack

- Java 17
- Spring Boot, Spring Web, Spring Security
- Spring Data MongoDB / MongoDB Atlas
- JWT via JJWT
- Swagger UI via Springdoc OpenAPI
- Maven, Lombok, JUnit

