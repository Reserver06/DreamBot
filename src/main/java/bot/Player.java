package bot;

import bot.commands.Loot;

import java.text.DecimalFormat;

public class Player extends Loot {

    private final String name;
    private final int loot;
    private int average;
    private final DecimalFormat f = new DecimalFormat("###,###,###");

    public Player(String name,int loot){
        this.name = name;this.loot = loot;
    }
    public String getName(){
        return name;
    }
    public int getLoot(){
        return loot;
    }

    @Override
    public String toString() {
        return name +": "+f.format(loot);
    }

}
