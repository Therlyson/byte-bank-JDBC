package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.util.Objects;

public class Conta {
    private Integer numero;
    private BigDecimal saldo;
    private Cliente titular;
    private Boolean estaAtiva;

    public Conta(Integer numero, Cliente titular, Boolean estaAtiva) { //construtor para quando ser criado uma conta pela primeira vez
        this.numero = numero;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.estaAtiva = estaAtiva;
    }

    public Conta(Integer numero, BigDecimal saldo, Cliente titular, Boolean estaAtiva) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.estaAtiva = estaAtiva;
    }

    public Boolean getEstaAtiva() {
        return estaAtiva;
    }

    public boolean possuiSaldo() {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return numero.equals(conta.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Número da conta: " + numero +
                ", Titular: " + titular +
                ", Saldo: " + saldo +
                ", ativa: " + estaAtiva;
    }

    public Integer getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getTitular() {
        return titular;
    }
}
