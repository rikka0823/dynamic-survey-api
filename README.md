# dynamic-survey-api

`dynamic-survey-api` 是一個基於 Spring Boot 的後端應用程式，與 `dynamic-survey-ui` 前端操作介面結合的**動態問卷系統**，專門用於創建、管理和分析動態問卷調查。該 API 提供靈活的問卷設計、即時回饋、統計分析等功能，並適用於市場研究、用戶滿意度調查等多種場景。

前端操作介面，詳見於 [`dynamic-survey-ui`](https://github.com/rikka0823/dynamic-survey-ui)。

前後端整合，詳見於 [`dynamic-survey`](https://github.com/rikka0823/dynamic-survey)。

## Project Structure

專案架構（Figma），請參考 [`動態問卷系統`](https://www.figma.com/design/7jcsPZxB0Q26WYPpvk3tzr/%E5%8B%95%E6%85%8B%E5%95%8F%E5%8D%B7%E7%B3%BB%E7%B5%B1-Template_For_Class-)。

## Key Features

- **問卷管理**：創建、更新和刪除問卷，支持基於多種條件（如名稱、日期等）進行問卷模糊查詢。
- **問卷填寫**：用戶可以填寫問卷，並即時提交答案。
- **數據與統計**：查詢問卷資料、回饋資料及統計數據，並支持圖表化展示。
- **帳戶管理**：提供用戶註冊和登入功能，支援帳戶的創建和驗證。

## Tech Stack

此專案使用以下技術：

- **Java 17**：作為開發語言。
- **Spring Boot 2.7.18**：作為應用核心框架，簡化開發流程並提升可維護性，並搭配 **Gradle** 進行構建與依賴管理。
- **Spring Security**：提供應用的安全性功能，如以 bcrypt 將使用者密碼雜湊後安全儲存。
- **Spring Data JPA**：以 Hibernate 作為底層邏輯，將 Object 與 MySQL 資料庫進行 ORM 操作，如 CRUD 或 計數統計。
- **Spring Boot Validation**：以 @Valid、@Validated 處理 Exception，並建立 GlobalExceptionHandler，採用 @ExceptionHandler 客製化錯誤、例外返回 JSON 訊息。
- **MySQL 8.0.40**：關聯式資料庫管理系統，欄位一對多、複合主鍵的方式，儲存問卷數據和用戶資訊；並結合 Spring Boot 的 @Transactional，處理 ACID 之交易特性。
- **Caffeine Cache**：透過 Cache 技術，提高問卷搜尋的效能，減少重複查詢時的延遲。
- **SpringDoc OpenAPI**：用於生成 Swagger UI 文件，詳細描述 API 端點。
- **JUnit 5**：用於單元測試，確保程式的穩定性與可靠性。
- **Docker**：編寫 Dockerfile 以建立 Spring Boot 應用程式和 MySQL 資料庫的容器映像檔（image），並使用 Docker Compose 管理多容器架構，實現 Spring Boot 應用與 MySQL 資料庫的整合，簡化並自動化部署流程。

## Running the Project in Docker

在執行以下 Shell 指令前，請先確保電腦安裝及啟動 Docker：

- `docker-compose build --no-cache`：建立 Spring Boot 應用程式和 MySQL 資料庫的 Docker 映像檔（image），並強制不使用 Cache。
- `docker-compose up -d`：啟動 docker-compose，並在 Docker daemon 背景中運作所有容器。
- `docker-compose down`： 停止、移除運作中的容器，並且移除相關的映像檔（image）。
- `docker volume rm dynamic-survey-api_db-data`：移除本地 Docker Volume 中存放的資料庫資料。

容器順利運作後，API 相關操作，可參考 [`Swagger UI`](http://localhost:8080/swagger-ui/index.html) 內 API 文件操作範例，或將本專案之 `dynamic-survey-api.postman_collection.json` 匯入 Postman 查看範例，並依需求輸入 Shell 指令停止、移除運作中的容器，停止服務。

## API Endpoints

### 問卷相關 API

- `POST /quiz/create`  
  創建一個新的問卷。請求參數為 `CreateUpdateReq`，包含問卷的基本資料。

- `POST /quiz/update`  
  更新一個已有的問卷。請求參數為 `CreateUpdateReq`，包含要更新的問卷資料。

- `POST /quiz/delete`  
  刪除問卷。請求參數為 `DeleteReq`，可依問卷 ID 批量刪除問卷。

- `POST /quiz/search`  
  根據條件（問卷名稱、開始日期、結束日期）搜尋問卷。返回符合條件的問卷資料。

- `POST /quiz/fillin`  
  用戶填寫問卷，請求參數為 `FillinReq`。

- `GET /quiz/getQuizData`  
  獲取所有問卷資料。

- `GET /quiz/getQuizDataById`  
  根據問卷 ID 獲取指定問卷的資料。

- `GET /quiz/feedback`  
  獲取問卷的回饋資料，請求參數為 `quizId`。

- `GET /quiz/statistics`  
  獲取問卷統計數據，請求參數為 `quizId`。

- `GET /quiz/getQuesData`  
  根據問卷 ID 獲取問卷問題資料，請求參數為 `quizId`。

### 帳戶相關 API

- `POST /account/register`  
  註冊新帳戶，請求參數為 `AccountReq`。

- `POST /account/login`  
  用戶登入，請求參數為 `AccountReq`。

## Frontend Application

前端應用負責提供使用者操作介面，並通過 RESTful API 與後端進行串接。

前端專案請參考 [`dynamic-survey-ui`](https://github.com/rikka0823/dynamic-survey-ui)。

## Fullstack Application

採用 Docker Compose 的三層式架構（three tier architecture），前端（ui）、後端（api）、資料庫（db）個別容器化及整合的技術，簡化部署流程。

前後端整合專案請參考 [`dynamic-survey`](https://github.com/rikka0823/dynamic-survey)。

