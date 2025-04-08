# Sistema de Gerenciamento de Farmácia

Este é um projeto JavaFX para gerenciar o estoque e as informações de medicamentos em uma farmácia. Ele permite adicionar, excluir, consultar e gerar relatórios sobre os medicamentos cadastrados.

## Funcionalidades

* **Adicionar Medicamento:** Permite cadastrar novos medicamentos no sistema, incluindo código, nome, descrição, princípio ativo, data de validade, quantidade em estoque, preço, se é controlado ou não, e informações do fornecedor (CNPJ e razão social).
* **Excluir Medicamento:** Remove um medicamento do sistema com base no código e nome.
* **Consultar Medicamento:** Busca um medicamento específico utilizando o código e o nome.
* **Relatório de Estoque Baixo:** Exibe uma lista de medicamentos com quantidade em estoque inferior a 15 unidades.
* **Relatório de Próximo Vencimento:** Lista os medicamentos com data de validade dentro dos próximos 30 dias.
* **Relatório de Valor Total por Fornecedor:** Calcula e exibe o valor total do estoque de medicamentos de um fornecedor específico, inserindo o CNPJ do fornecedor.
* **Buscar Medicamentos Controlados/Não Controlados:** Filtra e exibe os medicamentos que são controlados ou não controlados.

## Tecnologias Utilizadas

* **Java:** Linguagem de programação principal.
* **JavaFX:** Framework para a criação da interface gráfica do usuário.
* **FXML:** Linguagem de marcação XML para definir a estrutura da interface gráfica.
* **Java IO:** Classes para operações de leitura e escrita de arquivos (para persistência de dados em um arquivo CSV).
* **Java Time API:** Para manipulação de datas (LocalDate).
* **Java Collections Framework:** Para trabalhar com coleções de dados (ObservableList, ArrayList, List).
* **Java Streams API:** Para operações de agregação e filtragem em coleções.
* **Java Regular Expressions:** Para validação de formatos de entrada (código e CNPJ).

## Como Executar

1.  **Pré-requisitos:**
    * Java Development Kit (JDK) instalado no seu sistema.
    * Um ambiente de desenvolvimento integrado (IDE) como IntelliJ IDEA, Eclipse ou NetBeans (opcional, mas recomendado).
    * JavaFX SDK (geralmente incluído nas versões mais recentes do JDK, mas pode ser necessário configurar separadamente em alguns IDEs).

2.  **Clonar o Repositório (se disponível):**
    ```bash
    git clone [https://github.com/lucasfreitaas/Avaliacao_P1.git](https://github.com/lucasfreitaas/Avaliacao_P1.git)
    cd Avaliacao_P1
    ```

3.  **Abrir o Projeto no IDE (opcional):**
    * Abra o projeto no seu IDE preferido.
    * Certifique-se de que o projeto reconhece as dependências do JavaFX.

4.  **Executar o Aplicativo:**
    * Localize a classe principal que inicia a aplicação JavaFX (geralmente uma classe que estende `javafx.application.Application`).
    * Execute esta classe a partir do seu IDE ou através da linha de comando usando o comando `java` (certificando-se de incluir os módulos JavaFX no classpath).

5.  **Arquivo de Dados:**
    * O sistema lê e grava os dados dos medicamentos em um arquivo chamado `medicamentos.csv` localizado em `C:\\Users\\Lucas\\Documents\\Avaliacao_N1\\src\\main\\java\\cadastro\\`.
    * Certifique-se de que este caminho seja acessível ou ajuste-o no código-fonte (`farmaciaController.java`) se necessário. O arquivo será criado se não existir.

## Estrutura do Projeto

farmacia/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── farmacia/
│                       ├── farmaciaApplication.java      (Ponto de entrada da aplicação)
│                       ├── farmaciaController.java       (Controlador da interface gráfica)
│                       ├── Medicamentos.java             (Classe para representar medicamentos)
│                       ├── Fornecedores.java             (Classe para representar fornecedores)
│                       └── farmacia.fxml                 (Arquivo FXML da interface principal)
└── README.md


## Como Usar

1.  Execute a aplicação.
2.  A interface gráfica exibirá campos para inserir as informações de um novo medicamento.
3.  Preencha os campos necessários e clique no botão "Adicionar Medicamento".
4.  Para excluir ou consultar, preencha o código e o nome do medicamento e clique no botão correspondente.
5.  Para gerar relatórios, utilize os botões específicos para estoque baixo, próximo vencimento ou valor total por fornecedor (insira o CNPJ do fornecedor).
6.  Para filtrar medicamentos controlados ou não controlados, selecione o respectivo RadioButton. Para exibir todos os medicamentos novamente, desmarque ambos os RadioButtons.
7.  Os resultados das operações e os relatórios serão exibidos na tabela de medicamentos.
8.  Alertas informativos e de erro serão mostrados para feedback ao usuário.

## Considerações

* **Persistência de Dados:** Os dados dos medicamentos são armazenados em um arquivo CSV local. Para uma aplicação mais robusta, seria recomendado o uso de um banco de dados.
* **Tratamento de Erros:** O sistema inclui tratamento básico de erros para validação de entrada e operações de arquivo. Mais tratamento de erros e feedback ao usuário podem ser implementados.
* **Caminho do Arquivo:** O caminho do arquivo CSV está fixo no código. Em uma aplicação real, seria melhor utilizar configurações ou solicitar o caminho ao usuário.
* **Interface do Usuário:** A interface gráfica pode ser aprimorada com mais funcionalidades e um design mais intuitivo.

## Contribuição

Contribuições para este projeto são bem-vindas. Sinta-se à vontade para abrir issues para relatar bugs ou propor melhorias, ou enviar pull requests com suas alterações.

## Autor

[Lucas Freitas Fé Silva - 5° Período de Engenharia de Software]
