# dynamic-survey-api

`dynamic-survey-api` 是一個基於 Spring Boot 的後端應用程式，與`dynamic-survey-ui`前端操作介面結合，專門用於創建、管理和分析動態問卷調查。該 API 提供靈活的問卷設計、即時回饋、統計分析等功能，並適用於市場研究、用戶滿意度調查等多種場景。

前端操作介面，詳見於 [`dynamic-survey-ui`](https://github.com/rikka0823/dynamic-survey-ui)。

## Key Features

- **問卷管理**：創建、更新、刪除問卷，並支持基於多種條件進行問卷的模糊查詢。
- **問卷填寫**：用戶可以填寫問卷，並即時提交答案。
- **數據與統計**：查詢問卷資料、回饋資料及統計數據，並支持圖表化展示。
- **帳戶管理**：提供用戶註冊和登錄功能，支援帳戶的創建和驗證。

## Tech Stack

此專案使用以下技術：

- **Java 17**：作為開發語言。
- **Spring Boot 2.7.18**：核心框架，負責應用的基礎設置與管理。
- **Spring Security**：提供應用的安全性功能，如以 bcrypt 將使用者密碼加密安全儲存。
- **Spring Data JPA**：以 Hibernate 作為底層邏輯，將 Object 與 MySQL 資料庫進行 ORM 操作，如 CRUD 或 計數統計。
- **MySQL 8.0.40**：關聯式資料庫管理系統，欄位一對多、複合主鍵的方式，儲存問卷數據和用戶資訊。
- **Caffeine Cache**：透過 Cache 技術，提高問卷搜尋的效能，減少重複查詢時的延遲。
- **Springdoc OpenAPI**：用於生成 Swagger UI 文件，詳細描述 API 端點。
- **JUnit 5**：用於單元測試，確保程式的穩定性與可靠性。

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
  用戶登錄，請求參數為 `AccountReq`。

## 前端應用

前端應用負責提供使用者操作介面，並通過 RESTful API 與後端進行串接。前端專案請參考 [`dynamic-survey-ui`](https://github.com/rikka0823/dynamic-survey-ui)。
