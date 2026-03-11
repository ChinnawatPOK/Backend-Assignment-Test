# Job Data API

A read-only REST API built with **Spring Boot** + **Java 21**.

---

## 🚀 Getting Started

### Prerequisites
- Java 21

### Guides CURL
The following guides illustrate how to use some features concretely:

**Get all data**
```bash
curl --location 'http://localhost:8080/api/v1/job_data'
```

**Filter By Salary Gte**
```bash
curl --location 'http://localhost:8080/api/v1/job_data?salaryGte=120000'
```

**Filter By Salary Lte**
```bash
curl --location 'http://localhost:8080/api/v1/job_data?salaryLlte=80000'
```

**Filter Some fields**
```bash
curl --location 'http://localhost:8080/api/v1/job_data?fields=job_title%2Cgender%2Csalary&salaryGte=120000'
```

**Sort by salary DESC**
```bash
curl --location 'http://localhost:8080/api/v1/job_data?fields=job_title%2Cgender%2Csalary&salaryGte=120000'
```

**Combine All params**
```bash
curl --location 'http://localhost:8080/api/v1/job_data?job_title=Engineer&gender=Male&salaryGte=100000&sort=salary&sort_type=DESC&fields=job_title%2Csalary%2Cgender&page=0&size=10'
```




