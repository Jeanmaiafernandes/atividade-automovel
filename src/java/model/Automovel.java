package model;

public class Automovel {
    private Integer id;
    private String cor;
    private Integer ano;
    private String motor;
    private Double preco;
    private String marca;
    private String cambio;
    private String tracao;
    private Integer cavalos;
    private String categoria;
    private String corInterna;
    private String combustivel;
    
/**
 *
 * @author jeanm
 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public void setTracao(String tracao) {
        this.tracao = tracao;
    }

    public void setCavalos(Integer cavalos) {
        this.cavalos = cavalos;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCorInterna(String corInterna) {
        this.corInterna = corInterna;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getCor() {
        return cor;
    }

    public Integer getAno() {
        return ano;
    }

    public String getMotor() {
        return motor;
    }

    public Double getPreco() {
        return preco;
    }

    public String getMarca() {
        return marca;
    }

    public String getCambio() {
        return cambio;
    }

    public String getTracao() {
        return tracao;
    }

    public Integer getCavalos() {
        return cavalos;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCorInterna() {
        return corInterna;
    }

    public String getCombustivel() {
        return combustivel;
    }

    // debug/log para gravar no banco os dados.
    @Override
    public String toString() {
        return "Automovel{"
                + "id=" + id + ","
                + " cor=" + cor + ","
                + " ano=" + ano + ","
                + " motor=" + motor + ","
                + " preco=" + preco + ","
                + " marca=" + marca + ", "
                + "cambio=" + cambio + ","
                + " tracao=" + tracao + ", "
                + "cavalos=" + cavalos + ", "
                + "categoria=" + categoria + ","
                + " corInterna=" + corInterna + ", "
                + "combustivel=" + combustivel + '}';
    }  
}
