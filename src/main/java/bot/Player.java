package bot;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class Player implements Comparable<Player>{

    private final String discordId;
    private final String name;
    private final long phone;
    private final long loot;

    private final DecimalFormat f = new DecimalFormat("###,###,###");

    public Player(String discordId,String name,String phone,String loot){
        this.discordId = discordId;
        this.name = name;
        this.phone = Long.parseLong(phone);
        this.loot = Long.parseLong(loot);


    }

    public String getDiscordId() {
        return discordId;
    }

    public String getName() {
        return name;
    }

    public long getPhone() {
        return phone;
    }

    public long getLoot() {
        return loot;
    }

    @Override
    public String toString() {
        return name +": "+f.format(loot);
    }

    @Override
    public int compareTo(@NotNull Player player) {
        if(this.loot>player.getLoot())
            return -1;
        else
            return 1;
    }
}
