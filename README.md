# Getting Started
This project use JAVA21

### Guides CURL
The following guides illustrate how to use some features concretely:
[GET] get all data: curl --location 'http://localhost:8080/api/v1/job_data'
[GET] Filter By Salary Gte: curl --location 'http://localhost:8080/api/v1/job_data?salaryGte=120000'
[GET] Filter By Salary Lte: curl --location 'http://localhost:8080/api/v1/job_data?salaryLlte=80000'
[GET] Filter Some fields: curl --location 'http://localhost:8080/api/v1/job_data?fields=job_title%2Cgender%2Csalary&salaryGte=120000'
[GET] Sort by salary DESC: curl --location 'http://localhost:8080/api/v1/job_data?sort=salary&sort_type=DESC&salaryGte=120000'
[GET] Combine All params: curl --location 'http://localhost:8080/api/v1/job_data?job_title=Engineer&gender=Male&salaryGte=100000&sort=salary&sort_type=DESC&fields=job_title%2Csalary%2Cgender&page=0&size=10'



