# DSM-PI3-G03-2026-2 - Apoio
### Sistema Marido de Aluguel: orçamentos de reparos a partir de fotos

**Repositório do GRUPO 03 do Projeto Interdisciplinar do 3º semestre DSM 2026/2.**

**Alunos:** Denner Victor Pimenta, Richard Gabriel dos Santos, Tiago William Valeriano Pereira

**Vídeo de demonstração:** assista no YouTube: https://www.youtube.com/playlist?list=PLRzgoQWBAXE8

# Sobre o Projeto

&emsp;O Apoio é uma plataforma web que aproxima clientes e prestadores de serviços residenciais, como eletricistas, encanadores, pedreiros e pintores. Hoje, o prestador precisa ir até a casa do cliente apenas para avaliar o problema e passar um preço, gastando tempo e combustível sem garantia de que o serviço será fechado. No Apoio, o cliente descreve o problema e anexa fotos, e os prestadores respondem com valor, prazo e uma mensagem. Com a triagem feita por imagens, a visita apenas para avaliar deixa de ser necessária, e o cliente consegue comparar preços, prazos e a reputação de quem vai entrar em sua casa.

## Principais funcionalidades

- Cadastro de clientes e de prestadores (com validação de CPF e senha criptografada)
- Login com e-mail e senha, levando cada usuário para o seu painel
- Solicitação de orçamento com descrição e fotos do problema
- Envio de orçamentos pelos prestadores, com valor, prazo e mensagem
- Comparação, aceite e recusa dos orçamentos pelo cliente
- Registro da conclusão do serviço
- Avaliação do prestador, que atualiza a nota média do seu perfil

## Tecnologias

### Back-End

- Java + Spring Boot - API REST
- MongoDB Atlas - Banco de dados NoSql

### Front-End

- HTML5 - Estrutura das páginas
- CSS3 - Estilização das páginas
- JavaScript - Funcionalidades e interações do sistema, com comunicação com a API pelo `fetch`

## Bibliotecas utilizadas

- Spring Data MongoDB: Acesso e operações no banco de dados.
- Spring Validation (Hibernate Validator): Validação dos dados recebidos pela API, incluindo o `@CPF`.
- Spring Security Crypto: Criptografia das senhas com BCrypt.
- Spring Boot DevTools: Reinício automático do servidor durante o desenvolvimento.

## Estrutura do Projeto

```text
DSM-PI3-G03-2026-2/
├── .devcontainer/                 # Configurações do ambiente de desenvolvimento
│
├── api/
│   ├── .mvn/                      # Configurações do Maven Wrapper
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/edu/fatecfranca/api/
│   │   │   │   ├── config/        # Configurações da aplicação (CORS)
│   │   │   │   ├── controllers/   # Controladores das requisições HTTP
│   │   │   │   ├── dtos/          # Objetos de transferência de dados
│   │   │   │   ├── exceptions/    # Tratamento de exceções e erros
│   │   │   │   ├── models/        # Modelos e estruturas de dados
│   │   │   │   ├── repositories/  # Acesso e operações no MongoDB
│   │   │   │   └── services/      # Regras de negócio e serviços
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── application.properties.example
│   │   │
│   │   └── test/                  # Testes do Back-End
│   │
│   ├── mvnw                       # Maven Wrapper (Linux/macOS)
│   ├── mvnw.cmd                   # Maven Wrapper (Windows)
│   └── pom.xml                    # Dependências e configurações do Maven
│
├── frontend/
│   ├── css/                       # Arquivos de estilização CSS
│   ├── images/                    # Imagens e recursos visuais
│   ├── js/                        # Scripts de cada página e comunicação com a API
│   └── *.html                     # Páginas da aplicação
│
├── docs/                          # Documentação do projeto
│   ├── db/                        # Memorial do Projeto e diagrama NoAM
│   ├── gestao/                    # Termo de Abertura do Projeto (TAP)
│   └── IHC/                       # Espaço do Problema
│
└── README.md                      # Documentação principal do projeto
```

## Instalação e configuração

### Pré-requisitos

- MongoDB: local ou MongoDB Atlas
- Java 25: local ou usar o GitHub Codespaces

# Como testar o Sistema:
#### **1ª Etapa:** Clone o repositório e entre na pasta.
```bash
git clone https://github.com/FatecFranca/DSM-PI3-G03-2026-2.git

cd DSM-PI3-G03-2026-2
```

## Back-End

#### **2ª Etapa:** Entre na pasta da api do projeto
```bash
cd api
```
#### **3ª Etapa:** Edite o application.properties com suas configurações

Crie a cópia do arquivo de exemplo
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

E adicione o seu banco de dados
```bash
spring.application.name=api

# Configura a porta da aplicação
server.port=8888

# Desabilita (por enquanto) o acesso a banco de dados
spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration

# Serve as páginas do front-end (pasta frontend, ao lado da pasta api)
spring.web.resources.static-locations=file:../frontend/

# Configure a URI do banco no arquivo local application.properties.
spring.mongodb.uri=
```
### Como preencher cada campo:
- `spring.mongodb.uri=`: É a rota de conexão do MongoDB, por exemplo `mongodb+srv://USUARIO:SENHA@SEU-CLUSTER.mongodb.net/pi3`

**Observação:** O `application.properties` está no `.gitignore`, então a senha do banco não vai para o GitHub.

#### **4ª Etapa:** Inicie o servidor do Back-End
```bash
./mvnw spring-boot:run
```

## Front-End

#### **5ª Etapa:** Acesse o Front-End

O Front-End não precisa de instalação nem de um servidor separado: a própria API entrega as páginas da pasta `frontend`. Com o Back-End rodando, acesse:

- http://localhost:8888

## Rotas:

**Cliente:** Pede orçamentos com fotos do problema, compara os orçamentos recebidos, aceita ou recusa, registra a conclusão do serviço e avalia o prestador.

**Prestador:** Visualiza os pedidos disponíveis para orçar e envia orçamentos com valor, prazo e mensagem.

- Página inicial: http://localhost:8888/index.html
- Cadastro: http://localhost:8888/cadastro.html
- Login: http://localhost:8888/login.html
- Painel do cliente: http://localhost:8888/painel.html
- Pedir orçamento: http://localhost:8888/solicitacoes.html
- Orçamentos recebidos: http://localhost:8888/orcamentos.html
- Painel do prestador: http://localhost:8888/painel-prestador.html

**Por segurança** As páginas internas só abrem com um usuário logado. As telas de orçamentos (cliente) e de pedidos (prestador) conferem o tipo de usuário, e a API valida cada operação, por exemplo, só um cliente cadastrado consegue abrir uma solicitação.

## O que fazer para testar
Para começar a utilizar o Apoio, é necessário cadastrar pelo menos um cliente e um prestador.

1. Entre como **cliente** e peça um orçamento, com a descrição e as fotos do problema.
2. Entre como **prestador** e envie um orçamento para o pedido.
3. Volte como **cliente**, aceite o orçamento e, quando o serviço terminar, registre a conclusão.
4. Por fim, o cliente **avalia** o prestador, e a nota média do prestador é atualizada.

**Observação:** Algumas informações são geradas automaticamente pelo sistema, como as datas, os status e a nota média. Por isso, não é necessário preencher manualmente dados internos do banco de dados.

## Arquitetura do Projeto

O Apoio utiliza uma **arquitetura em camadas no Back-End**, separando as responsabilidades da aplicação para facilitar a organização e a manutenção do código.

### Back-End
- **Controllers:** recebem as requisições HTTP e encaminham as operações para os serviços.
- **Services:** concentram as regras de negócio, como recusar os demais orçamentos pendentes quando um orçamento é aceito e recalcular a nota média do prestador a cada avaliação.
- **Repositories:** realizam o acesso e as operações no banco de dados MongoDB, utilizando Spring Data.
- **Models:** representam os dados e as estruturas utilizadas pela aplicação, com as validações de cada campo.
- **DTOs (Data Transfer Objects):** definem os dados recebidos e enviados pela API. As listagens de solicitações e orçamentos já trazem os documentos vinculados (cliente, solicitação e prestador).
- **Config:** reúne as configurações da aplicação, incluindo o CORS.
- **Exceptions:** centralizam o tratamento de erros e a padronização das respostas de erro da API.

### Front-End
O Front-End utiliza HTML, CSS e JavaScript puro. Cada página tem o seu próprio script na pasta `js`, que se comunica com a API pelo `fetch` e guarda o usuário logado no navegador.

## Endpoints da API

### Login
```bash
POST    /login      # Entra com e-mail e senha e retorna o id, o nome e o tipo do usuário.
```

### Clientes
```bash
GET     /clientes               # Lista todos os clientes.
GET     /clientes/{id}          # Busca um cliente pelo ID.
GET     /clientes/email/{email} # Busca um cliente pelo e-mail.
POST    /clientes               # Cadastra um cliente.
PUT     /clientes/{id}          # Atualiza um cliente.
DELETE  /clientes/{id}          # Exclui um cliente.
```

### Prestadores
```bash
GET     /prestadores                # Lista todos os prestadores.
GET     /prestadores/{id}           # Busca um prestador pelo ID.
GET     /prestadores/email/{email}  # Busca um prestador pelo e-mail.
POST    /prestadores                # Cadastra um prestador.
PUT     /prestadores/{id}           # Atualiza um prestador.
DELETE  /prestadores/{id}           # Exclui um prestador.
```

### Solicitações
```bash
GET     /solicitacoes                       # Lista as solicitações, com os dados do cliente.
GET     /solicitacoes/{id}                  # Busca uma solicitação pelo ID.
GET     /solicitacoes/cliente/{clienteId}   # Lista as solicitações de um cliente.
GET     /solicitacoes/status/{status}       # Lista as solicitações por status.
POST    /solicitacoes                       # Abre uma solicitação com descrição e fotos.
```

### Orçamentos
```bash
GET     /orcamentos                             # Lista os orçamentos, com a solicitação e o prestador.
GET     /orcamentos/{id}                        # Busca um orçamento pelo ID.
GET     /orcamentos/solicitacao/{solicitacaoId} # Lista os orçamentos de uma solicitação.
GET     /orcamentos/prestador/{prestadorId}     # Lista os orçamentos de um prestador.
POST    /orcamentos                             # Envia um orçamento.
PUT     /orcamentos/{id}/status                 # Aceita ou recusa um orçamento (ACEITO ou RECUSADO).
PUT     /orcamentos/{id}/concluir               # Registra a conclusão do serviço.
```

### Avaliações
```bash
GET     /avaliacoes                         # Lista todas as avaliações.
GET     /avaliacoes/{id}                    # Busca uma avaliação pelo ID.
GET     /avaliacoes/prestador/{prestadorId} # Lista as avaliações de um prestador.
GET     /avaliacoes/cliente/{clienteId}     # Lista as avaliações feitas por um cliente.
POST    /avaliacoes                         # Registra a avaliação de um serviço concluído.
```

## Troubleshooting (Problemas comuns)

### 1. Erro na configuração do JAVA_HOME

**Problema:** Ao tentar executar o backend, o terminal informa que o Java não foi encontrado ou que a variável `JAVA_HOME` não está configurada.

**Solução:** Verifique se o JDK 25 está instalado e se a variável de ambiente `JAVA_HOME` aponta para o diretório de instalação do Java. Após configurar, reinicie o terminal.

Para verificar a versão do Java digite esse comando no cmd
```cmd
javac --version
```
Se não tiver retorno, é porque o Java não está instalado na máquina.

Para definir o JAVA_HOME após baixar o JDK execute o comando como administrador e coloque o caminho da pasta que seu Java foi baixado
```cmd
setx -m JAVA_HOME "C:\Program Files\Java\jdk-25"
```

### 2. Erro ao iniciar o backend por falta de configuração

**Problema:** A aplicação apresenta erros ao iniciar devido à ausência das configurações necessárias no `application.properties`.

**Solução:** Verifique se o arquivo `application.properties` foi criado a partir do `application.properties.example` e se a conexão com o MongoDB (`spring.mongodb.uri`) foi preenchida corretamente.

### 3. Páginas abrem, mas mostram "Não foi possível conectar ao servidor"

**Problema:** O site abre, mas o login, os cadastros e as listagens não funcionam.

**Solução:** Verifique se o Back-End está rodando (4ª Etapa) e se o site foi aberto pelo endereço `http://localhost:8888`, pois as páginas chamam a API nesse endereço. No Codespaces, use o VS Code conectado ao Codespace, que encaminha a porta 8888 para o seu computador.

## Sugestões de melhorias futuras

- Guardar as fotos em um serviço de armazenamento de arquivos, salvando no banco apenas o link da imagem.
- Adicionar uma busca de prestadores por especialidade e cidade.
- Implementar a verificação do perfil do prestador, usando o campo `verificado` que já existe no cadastro.
- Adicionar um chat entre cliente e prestador para tirar dúvidas antes do orçamento.
- Enviar notificações quando chegar um orçamento novo ou quando um orçamento for aceito.
- Adicionar a recuperação de senha pelo e-mail.

## Equipe
**GRUPO 03 - DSM 3º Semestre 2026/2**  

- Denner Victor Pimenta
- Richard Gabriel dos Santos
- Tiago William Valeriano Pereira

**Instituição:** FATEC Franca  
**Curso:** Desenvolvimento de Software Multiplataforma  
**Semestre:** 3º - 2026/2
