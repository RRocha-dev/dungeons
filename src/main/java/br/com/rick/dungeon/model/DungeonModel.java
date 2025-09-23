package br.com.rick.dungeon.model;

import lombok.Data;
import org.bukkit.Location;

@Data
public class DungeonModel {
    private String name;
    private Integer level;
    private Location spawn;
}
