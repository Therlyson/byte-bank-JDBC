package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
    private Connection connection;

    public ContaDAO(Connection connection){
        this.connection = connection;
    }

    public boolean salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente);

        String sql = "INSERT INTO conta(numero, saldo, cliente_nome, cliente_cpf, cliente_email) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, conta.getNumero());
            ps.setBigDecimal(2, BigDecimal.ZERO);
            ps.setString(3, cliente.getNome());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEmail());

            int resultado = ps.executeUpdate();
            return resultado == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();
        String sql = "SELECT * FROM conta";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            while(result.next()){
                Integer numeroConta = result.getInt(1);
                BigDecimal saldo = result.getBigDecimal(2);
                String nome = result.getString(3);
                String cpf = result.getString(4);
                String email = result.getString(5);

                Cliente cliente = new Cliente(new DadosCadastroCliente(nome, cpf, email));
                Conta conta = new Conta(numeroConta, cliente);
                contas.add(conta);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return contas;
    }
}
