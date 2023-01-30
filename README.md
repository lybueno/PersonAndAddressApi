# Person and Address API

### Sobre a aplicação:

A aplicação foi desenvolvida utilizando a linguagem Java, Spring, banco de dados H2 e arquitetura flexível baseada 
nos princípios SOLID, separando cada camada com suas próprias responsabilidades. <br>
Foram implementados testes unitários utilizando JUnit e Mockito. <br>
O sistema desenvolvido consiste em uma ferramenta para gerenciamento de cadastro de pessoas e seus respectivos 
endereços.
É possível por meio do sistema:
- Cadastrar pessoa,
- Editar pessoas previamente cadastradas,
- Consultar o cadastro de uma pessoa informando seu ID,
- Consultar todas as pessoas já cadastradas,
- Criar endereço(s) para uma pessoa já cadastrada,
- Consultar todos endereços cadastrados de uma pessoa já cadastrada,
- Consultar o endereço principal de uma pessoa cadastrada.

A aplicação foi documentada com Swagger e pode ser acessada pelo endereço <http://localhost:8080/swagger-ui/index.html> após rodar a aplicação. Também é possível utilizar a documentação para testar as funcionalidades da API.
***
### Ferramentas necessárias:
- JDK 17;
- IDE de sua preferência
***
### Como executar a aplicação?
1. Clone o respositório:<br>
``git clone https://github.com/lybueno/PersonAndAddressApi.git`` <br>
2. Crie as variáveis de ambiente para configurar o seu banco de dados que se encontram no arquivo ``application.
   properties``,
   ou 
   substitua os valores:
    - ``${DB_URL}`` pelo nome do seu banco de dados,
    - ``${DB_USER}`` pelo nome de usuário do seu banco de dados,
    - ``{DB_PASSWORD}`` pela senha do seu banco de dados.
3. Execute a aplicação:
<br> Pode ser por comando ou rodando o método ``main()`` na classe AttornatusApplication.java
4. Acesse a aplicação no navegador pelo endereço <http://localhost:8080/swagger-ui/index.htm> <br>
***
### Dependências e versões utilizadas
As dependências utilizadas e versões se encontram no arquivo pom.xml


