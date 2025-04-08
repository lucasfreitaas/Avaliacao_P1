package com.example.farmacia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

//import java.awt.TextField;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class farmaciaController {

    @FXML
    private TextField codigoField;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField descricaoField;
    @FXML
    private TextField principioAtivoField;
    @FXML
    private TextField dataValidadeField;
    @FXML
    private TextField quantidadeEstoqueField;
    @FXML
    private TextField precoField;
    @FXML
    private RadioButton controladoRadioButton, naoControladoRadioButton;
    @FXML
    private ToggleGroup controladoGroup;
    @FXML
    private TextField cnpjFornecedorField;
    @FXML
    private TextField razaoSocialField;
    @FXML
    private TableView<Medicamentos> tabelaMedicamentos;
    @FXML
    private TableColumn<Medicamentos, String> colunaCodigo;
    @FXML
    private TableColumn<Medicamentos, String> colunaNome;
    @FXML
    private TableColumn<Medicamentos, String> colunaDescricao;
    @FXML
    private TableColumn<Medicamentos, String> colunaPrincipio;
    @FXML
    private TableColumn<Medicamentos, LocalDate> colunaDataValidade;
    @FXML
    private TableColumn<Medicamentos, Integer> colunaEstoque;
    @FXML
    private TableColumn<Medicamentos, Double> colunaPreco;
    @FXML
    private TableColumn<Medicamentos, Boolean> colunaControlado;
    @FXML
    private TableColumn<Medicamentos, String> colunaFornecedor;

    private final ObservableList<Medicamentos> listaMedicamentos = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo_original"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaPrincipio.setCellValueFactory(new PropertyValueFactory<>("principioAtivo"));
        colunaDataValidade.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
        colunaEstoque.setCellValueFactory(new PropertyValueFactory<>("qtdEstoque"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaControlado.setCellValueFactory(new PropertyValueFactory<>("Controlado"));
        colunaControlado.setCellFactory(col -> {
            return new TableCell<Medicamentos, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item ? "Sim" : "Não");
                    }
                }
            };
        });
        colunaFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedores"));

        carregarDados();
        tabelaMedicamentos.setItems(listaMedicamentos);
    }
    @FXML
    public void adicionarMedicamento(){
        try{
            String codigo_original = codigoField.getText().trim();

            boolean codigoExiste = listaMedicamentos.stream()
                    .anyMatch(medicamento -> medicamento.getCodigo_original().equals(codigo_original));

            if (codigoExiste){
                mostrarAlerta("O código original já existe! Altere para inserir.");
                return;
            }

            if (!Pattern.matches("[A-Za-z0-9]{7}", codigo_original)){
                mostrarAlerta("Código inválido! Deve ter 7 caracteres alfanuméricos.");
                return;
            }

            String nome = nomeField.getText();
            if (nome.isEmpty() || nome.length() < 3){
                mostrarAlerta("Nome do medicamente deve ter pelo menos 3 caracteres!");
                return;
            }
            String descricao = descricaoField.getText().trim();
            String principioAtivo = principioAtivoField.getText().trim();

            LocalDate dataValidade;
            try{
                dataValidade = LocalDate.parse(dataValidadeField.getText().trim());
                if (dataValidade.isBefore(LocalDate.now())){
                    return;
                }
            } catch (DateTimeParseException e){
                mostrarAlerta("Formato de data inválido! Use o yyyy-MM-dd");
                return;
            }

            int qtdEstoque;
            try{
                qtdEstoque = Integer.parseInt(quantidadeEstoqueField.getText().trim());
                if (qtdEstoque < 0){
                    mostrarAlerta("A quantidade informada em estoque não pode ser negativa!");
                    return;
                }
            } catch (NumberFormatException e){
                mostrarAlerta("Quantidade deve ser um número inteiro. (Por exemplo: 5)");
                return;
            }

            double preco;
            try{
                preco = Double.parseDouble(precoField.getText().trim());
                if (preco <= 0){
                    mostrarAlerta("Preço deve ser um valor positivo diferente de zero!");
                }
            } catch (NumberFormatException e){
                mostrarAlerta("Formato do preço inválido. Use um número decimal! (Por exemplo: 5.48)");
                return;
            }

            boolean controlado;

            if (controladoRadioButton.isSelected()){
                controlado = true;
            } else if (naoControladoRadioButton.isSelected()){
                controlado = false;
            } else {
                controlado = false;
                mostrarAlerta("Não foi marcado se o medicamento é ou não controlado," +
                        "por valor default, ele será cadastrado como NÃO CONTROLADO.");
            }

            String cnpjFornecedor = cnpjFornecedorField.getText().trim();
            if (!Pattern.matches("\\d{14}", cnpjFornecedor)){
                mostrarAlerta("CNPJ inválido! Deve conter 14 dígitos númericos!");
                return;
            }

            String razaoSocial = razaoSocialField.getText().trim();
            if (razaoSocial.isEmpty()){
                mostrarAlerta("Razão Social do fornecedor não pode estar vazia.");
                return;
            }

            Fornecedores fornecedor = new Fornecedores(cnpjFornecedor, razaoSocial);
            Medicamentos medicamentos = new Medicamentos(codigo_original, nome, descricao, principioAtivo, dataValidade, qtdEstoque
            , preco, controlado, fornecedor);

            File file = new File("C:\\Users\\Lucas\\Documents\\Avaliacao_N1\\src\\main\\java\\cadastro\\medicamentos.csv");

            if (!medicamentoExisteNoArquivo(medicamentos)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    StringBuilder linha = new StringBuilder();
                    linha.append(medicamentos.getCodigo_original())
                            .append(";").append(medicamentos.getNome())
                            .append(";").append(medicamentos.getDescricao())
                            .append(";").append(medicamentos.getPrincipioAtivo())
                            .append(";").append(medicamentos.getDataValidade())
                            .append(";").append(medicamentos.getQtdEstoque())
                            .append(";").append(medicamentos.getPreco())
                            .append(";").append(medicamentos.isControlado())
                            .append(";").append(medicamentos.getFornecedores())
                            .append("\n");
                    writer.write(linha.toString());
                    writer.newLine();

                    informacao("Produto inserido.");
                } catch (IOException e) {
                    mostrarAlerta("Não foi possível salvar os dados no Arquivo!");
                    e.printStackTrace();
                }
            } else {
                mostrarAlerta("Este medicamento já foi adicionado anteriormente.");
            }

            listaMedicamentos.add(medicamentos);

            limparCampos();
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void limparCampos() {
        codigoField.clear();
        nomeField.clear();
        descricaoField.clear();
        principioAtivoField.clear();
        dataValidadeField.clear();
        quantidadeEstoqueField.clear();
        precoField.clear();
        controladoGroup.selectToggle(null);
        cnpjFornecedorField.clear();
        razaoSocialField.clear();
    }

    @FXML
    public void excluirMedicamento(){
        try{
            String codigo_original = codigoField.getText().trim();
            if (!Pattern.matches("[A-Za-z0-9]{7}", codigo_original)){
                mostrarAlerta("Código inválido! Deve ter 7 caracteres alfanuméricos.");
                return;
            }

            String nome = nomeField.getText().trim();
            if (nome == null || nome.isEmpty() || nome.length() < 3){
                mostrarAlerta("Nome do medicamente deve ter pelo menos 3 caracteres!");
                return;
            }

            Medicamentos medicamentosEncontrado = null;

            for (Medicamentos medicamento : listaMedicamentos){
                if(medicamento.getCodigo_original().equals(codigo_original) && medicamento.getNome().equals(nome)){
                    medicamentosEncontrado = medicamento;
                    break;
                }
            }

            if (medicamentosEncontrado == null){
                mostrarAlerta("O medicamento com o código e nome informado não foi encontrado. Tente novamente!");
                return;
            }

            listaMedicamentos.remove(medicamentosEncontrado);
        } catch (Exception e){
            e.printStackTrace();
            mostrarAlerta("Erro ao tentar excluir medicamento.");
        }
    }

    @FXML
    public void consultarMedicamento(){
        String codigo_original = codigoField.getText().trim();
        if (!Pattern.matches("[A-Za-z0-9]{7}", codigo_original)){
            mostrarAlerta("Código inválido! Deve ter 7 caracteres alfanuméricos.");
            return;
        }

        String nome = nomeField.getText().trim();
        if (nome == null || nome.isEmpty() || nome.length() < 3){
            mostrarAlerta("Nome do medicamente deve ter pelo menos 3 caracteres!");
            return;
        }

        Medicamentos medicamentoEncontrado = null;

        for (Medicamentos medicamentos : listaMedicamentos){
            if (medicamentos.getNome().equals(nome) && medicamentos.getCodigo_original().equals(codigo_original)){
                medicamentoEncontrado = medicamentos;
                break;
            }
        }

        if (medicamentoEncontrado == null) {
            mostrarAlerta("Não foi encontrado nenhum produto com o código e nome informado. Tente novamente!");
            return;
        }

        listaMedicamentos.clear();

        listaMedicamentos.add(medicamentoEncontrado);
    }

    @FXML
    public void relatorioEstoqueBaixo(){
        ObservableList<Medicamentos> medicamentosBaixoEstoque = listaMedicamentos.stream()
                .filter(l -> l.getQtdEstoque() < 15)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tabelaMedicamentos.setItems(medicamentosBaixoEstoque);
    }

    @FXML
    public void relatorioProximoVencimento(){
        LocalDate hoje = LocalDate.now();

        ObservableList<Medicamentos> medicamentosProximosVencimento = listaMedicamentos.stream()
                .filter(l -> {
                    LocalDate dataValidade = l.getDataValidade();
                    long diasParaVencimento = ChronoUnit.DAYS.between(hoje, dataValidade);
                    return diasParaVencimento <= 30 && diasParaVencimento >= 0;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tabelaMedicamentos.setItems(medicamentosProximosVencimento);
    }

    @FXML
    public void relatorioValorTotalPorFornecedor(){
        String cnpjFornecedor = cnpjFornecedorField.getText().trim();

        double valorTotalFornecedor = listaMedicamentos.stream().
                filter(fornecedor -> fornecedor.getFornecedores().getCnpj().trim().equals(cnpjFornecedor))
                .mapToDouble(medicamento -> medicamento.getPreco() * medicamento.getQtdEstoque()).sum();

        informacao("O valor do seu estoque é: " + valorTotalFornecedor);
    }

    @FXML
    public void buscarMedicamentoControlado(){
        if (controladoRadioButton.isSelected()){
            ObservableList<Medicamentos> medicamentosControlados = listaMedicamentos.stream()
                    .filter(Medicamentos::isControlado).collect(Collectors.toCollection(FXCollections::observableArrayList));

            tabelaMedicamentos.setItems(medicamentosControlados);

            controladoGroup.selectToggle(null);
        } else if (naoControladoRadioButton.isSelected()){
            ObservableList<Medicamentos> medicamentosNaoControlados = listaMedicamentos.stream()
                    .filter(medicamento -> !medicamento.isControlado()).collect(Collectors.toCollection(FXCollections::observableArrayList));

            tabelaMedicamentos.setItems(medicamentosNaoControlados);

            controladoGroup.selectToggle(null);
        } else {
            tabelaMedicamentos.setItems(listaMedicamentos);
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Aviso!");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void informacao(String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação!");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public boolean medicamentoExisteNoArquivo(Medicamentos medicamento) {
        File file = new File("C:\\Users\\Lucas\\Documents\\Avaliacao_N1\\src\\main\\java\\cadastro\\medicamentos.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains(medicamento.getCodigo_original())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void carregarDados(){
        File arquivo = new File("C:\\Users\\Lucas\\Documents\\Avaliacao_N1\\src\\main\\java\\cadastro\\medicamentos.csv");

        if (!arquivo.exists()){
            System.out.println("Arquivo não encontrado.");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String linha;

            while ((linha = br.readLine()) != null){
                String[] dados = linha.split(";");

                if (dados.length >= 9) {
                    Fornecedores fornecedores = new Fornecedores(dados[8], dados[9]);

                    String dataString = dados[4].replaceAll("\\s+", " ").trim().replaceAll("[^\\p{Print}\\s]", "");

                    try {
                        LocalDate dataValidade = LocalDate.parse(dataString);

                        int quantidade = Integer.parseInt(dados[5].trim());

                        double preco = Double.parseDouble(dados[6].trim());

                        boolean controlado = Boolean.parseBoolean(dados[7].trim());

                        Medicamentos medicamentos = new Medicamentos(
                                dados[0],
                                dados[1],
                                dados[2],
                                dados[3],
                                dataValidade,
                                quantidade,
                                preco,
                                controlado,
                                fornecedores
                        );

                        listaMedicamentos.add(medicamentos);
                    } catch (DateTimeParseException e) {
                        System.out.println("Erro ao converter data: " + dataString);
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter número: " + dados[5]);
                        e.printStackTrace();
                    }

                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
