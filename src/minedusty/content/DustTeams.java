package minedusty.content;

import arc.graphics.Color;
import mindustry.game.Team;

// this class is heavily referencing Twcash's Tantros Mod
public class DustTeams {
	public static Team team1, team2;

	public static void load(){
		team1 = newTeam(77, "team1", Color.valueOf("ffe9e9"));
		team2 = newTeam(78, "team2", Color.valueOf("752905"));
	}

	private static Team newTeam(int id, String name, Color color) {Team team = Team.get(id);
		team.name = name;
		team.color.set(color);
		
		team.palette[0] = color;
        team.palette[1] = color.cpy().mul(0.75f);
        team.palette[2] = color.cpy().mul(0.5f);

		for(int i = 0; i < 3; i++){
            team.palettei[i] = team.palette[i].rgba();
        }
        return team;
	}

}
