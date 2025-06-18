package br.com.sistema.coworking.Enum;

public enum TipoSala {

    REUNIAO("Reuniao"),
    CONFERENCIA("Conferencia"),
    EVENTO("Evento"),
    PRIVADA("Privada"),
    PUBLICA("Publica"),
    TREINAMENTO("Treinamento"),
    SILENCIOSA("Silenciosa"),
    CRIATIVA("Criativa"),
    CALL("Call"),
    LOUNGE("Lounge"),
    ESTUDIO("Estudio"),
    COWORKING("Coworking"),
    DESENVOLVIMENTO("Desenvolvimento"),
    MULTIMIDIA("Multimida");

    private String descricao;

    TipoSala(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

