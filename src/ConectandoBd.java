import javax.swing.*;
import java.sql.*;
public class ConectandoBd {
    private Statement stm;
    private Connection conectando;

    //CONEXÃO COM O BANCO DE DADOS DA APS
    public ConectandoBd() {

        try {
            conectando = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "kl9909");

            JOptionPane.showMessageDialog(null, "Conexão estabelecida com o BD");

            stm = conectando.createStatement();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Ops! Falha na conexão com o BD" + e.getMessage());
        }

//        inserirBd();
//        consultarPlaca();
//        atualizarPlaca();
//        deletarRegistro();
//        buscarRegistro();
//        buscarRegistroCliente();
        inserirCliente ();
    }

    //INSERINDO UM REGISTRO NO BANCO DE DADOS DA APS

    public void inserirBd() {

        try {
            stm.executeUpdate("INSERT INTO carro VALUES ( 'AAA2022', 'carro', 'Corola', 'Toyota', 'AZUL', '8736072796')");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro na operação, NÃO foi possível iserir os dados no BD - erro: " + e.getMessage());
        }
    }

    //FAZENDO CONSULTA NO BANCO DE DADOS DA APS
    public void consultarPlaca() {

        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM carro");

            while (rs.next()) {
                JOptionPane.showMessageDialog(null, rs.getString("placa"));
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro na operação, NÃO foi possível ler o BD." + e.getMessage());
        }
    }

    //MÉTODO PARA ATUALIZAR UM CAMPO DO BANCO DE DADOS DA APS

    public void atualizarPlaca() {

        try {
            stm.executeUpdate("UPDATE carro SET modelo = '812GTS' WHERE  placa = 'XYZ2022'");
            System.out.println("Atualizado");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro na operação, NÃO foi possível atualizar os dados no BD - ERRO: " + e.getMessage());
        }
    }


    //MÉTODO PARA DELETAR_REGISTRO DO BANCO DE DADOS DA APS
    public void deletarRegistro() {

        try {
            stm.executeUpdate("DELETE FROM carro WHERE placa = 'XYZ2022'");
            System.out.println("Carro deletado do sistema!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NÃO foi possível deletar o Registro no BD. ERRO: " + e.getMessage());
        }
    }

    //MÉTODO PARA BUSCAR UM REGISTRO NO BANCO DE DADOS DA APS
    public void buscarRegistro() {
        try {
            ResultSet resultado = stm.executeQuery("SELECT * FROM carro WHERE placa = 'KNZ4252'");
            while (resultado.next()) {
                JOptionPane.showMessageDialog(null, "Placa encontrada no sistema: " + resultado.getString("placa") + " " + resultado.getString("modelo"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NÃO foi possível buscar o Registro no BD. ERRO: " + e.getMessage());
        }
    }

    //MÉTODO PARA BUSCAR UM REGISTRO NO BANCO DE DADOS DA APS
    public void buscarRegistroCliente() {

        try {
            ResultSet resultado = stm.executeQuery("SELECT * FROM carro");
            Statement stm2 = conectando.createStatement();

            while (resultado.next()) {

               String cpf = resultado.getString("clientes_cpf");

                ResultSet resultado2 = stm2.executeQuery("SELECT * FROM clientes WHERE cpf = " + cpf +"  ");

                if (resultado2.next()) {

                    JOptionPane.showMessageDialog(null, "Placa: "+resultado.getString("placa") +" "+ "CPF: " + resultado2.getString("cpf" ) +" "+ "NOME: " + resultado2.getString("nome"));

                } else {

                    System.out.println("Não foi possível realizar a busca no BD!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro: Não foi possível buscar o registro. " + e.getMessage());
        }
    }

    //USANDO A CLASSE PREPAREDSTATEMENT PARA ADICIONAR UM REGISTRO NO BANCO DE DADOS DA APS
    public void inserirCliente () {

        String cpf = "4436072796";
        String nome = "Ketlim Silva";
        String endereco = "Rua das Quebradas";
        String telefone = "65782145";

        try {
            PreparedStatement pstmt = conectando.prepareStatement("INSERT INTO clientes VALUES (?,?,?,?)");

            pstmt.setString(1,cpf);
            pstmt.setString(2,nome);
            pstmt.setString(3,endereco);
            pstmt.setString(4,telefone);

            pstmt.execute();

        }catch (SQLException e) {
            System.out.println("erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ConectandoBd();
    }
}