package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class ContaService {
    private ConnectionFactory connectionFactory;

    public ContaService(){
        connectionFactory = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection connection = connectionFactory.recuperarConexao();
        ContaDAO contaDAO = new ContaDAO(connection);
        return contaDAO.listar();
    }

    public boolean abrir(DadosAberturaConta dadosDaConta) {
        Connection connection = connectionFactory.recuperarConexao();
        ContaDAO contaDAO = new ContaDAO(connection);
        return contaDAO.salvar(dadosDaConta);
    }

    private Conta buscarContaPorNumero(Integer numero) {
        Connection connection = connectionFactory.recuperarConexao();
        ContaDAO contaDAO = new ContaDAO(connection);
        if(contaDAO.listarPorNumero(numero) != null){
            return contaDAO.listarPorNumero(numero);
        }else{
            throw new RegraDeNegocioException("Não foi encontrado uma conta com esse número");
        }
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public boolean realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        conta.sacar(valor);
        Connection connection = connectionFactory.recuperarConexao();
        ContaDAO contaDAO = new ContaDAO(connection);
        return contaDAO.atualizar(conta);
    }

    public boolean realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        conta.depositar(valor);
        Connection connection = connectionFactory.recuperarConexao();
        ContaDAO contaDAO = new ContaDAO(connection);
        return contaDAO.atualizar(conta);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        //contas.remove(conta);
    }
}
