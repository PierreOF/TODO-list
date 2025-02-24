# TODO-LIST

- Permite que usuários cadastrados façam login.
- Cada usuário pode criar, listar, atualizar e excluir tarefas.
- As tarefas possuem os seguintes atributos:
  - Título
  - Descrição
  - Prioridade (LOW, NORMAL, IMPORTANT, VERY_IMPORTANT, CRITICAL)
  - Categoria
  - Status (TODO, DOING, DONE)
  - Data de término
- Tarefas podem ser ordenadas por prioridade, categoria, status ou data de término.
- Persistência dos dados usando SQLite, garantindo que as tarefas não sejam perdidas ao fechar o programa.

# Tecnologias utilizadas
 - Frontend
   - Html, Css e JavaScript

 - Backend
   - Java

# Pré-requisitos

- Java 17 ou superior
- Gradle
- Sistema linux

# Rodando o projeto

- Clone o repositório
  ```
  git clone https://github.com/PierreOF/TODO-list
- Antes de rodar o projeto, é importante compilar e garantir que todas as dependências estejam corretas:
  ```
  ./gradlew clean build
- Execute o projeto
  ```
  ./gradlew run
# Possíveis problemas

- Caso esteja usando wsl o formato do arquivo gradlew pode ser reconhecido como CRTF mas o wsl não irá conseguir encontrar, resultando nesse erro .
"bad interpreter: No such file or directory"

- Solução 
  - converta gradlew para o formato Unix
```
sed -i 's/\r$//' gradlew
chmod +x gradlew
