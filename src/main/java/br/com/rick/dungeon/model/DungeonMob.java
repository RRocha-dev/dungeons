package br.com.rick.dungeon.model;

import lombok.Data;

@Data
public class DungeonMob {
    private String nome;
    private Double vida;
    private Double velocidade;
    private Double dano;

    public boolean estaVivo() {
        return vida > 0.0;
    }
}
