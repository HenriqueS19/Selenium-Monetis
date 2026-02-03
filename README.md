# Testes Automatizados Monetis

Projeto de testes para a aplicação Monetis usando Selenium e Cucumber.

## Tecnologias

- Java 17
- Selenium WebDriver
- Cucumber
- RestAssured
- Maven

## Como correr os testes

```bash
mvn clean test
```
Ou abrir os ficheiros .feature em `src/test/resources/features/` e correr no IDE (botão direito → Run).

## Testes implementados

- Login
- Criar contas
- Transferências entre contas próprias
- Transferências para terceiros (IBAN)
- Pagamentos

## Estrutura

O projeto usa Page Object Model:
- `pages/` - Page Objects
- `stepdefinition/` - Steps do Cucumber
- `features/` - Cenários em Gherkin
- `hooks/` - Setup dos testes
- `utils/` - Métodos auxiliares para API

## Relatório

Depois de correr os testes abre `target/cucumber-reports.html`.
