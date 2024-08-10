# local development
## run local postgres
```bash
docker run --name budgets -e POSTGRES_USER=roba -e POSTGRES_PASSWORD=budgets -e POSTGRES_DB=roba_budgets -p 5432:5432 -d postgres:16-alpine
```

## run curl against endpoint
```bash
curl -v -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/v1/income
```
```bash
curl -v -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"title":"test income 1","amountInCents":123,"dueDate":"2024-08-07T07:55:34Z"}' http://localhost:8080/api/v1/income
```
