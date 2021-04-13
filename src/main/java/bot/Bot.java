package bot;

import bot.commands.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {
    public static String prefix = "~";

    public static void main(String[] args) throws LoginException {
        final JDABuilder builder = JDABuilder.createDefault("ODA4MzY0MTk1Njk5NDkwODc3.YCFd0g.tmsWKufkdGiQhQPnBuFk2zBR4jM");
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.playing("Goodgame Empire"));
        builder.setStatus(OnlineStatus.ONLINE);
        System.out.println("Bot is online");

        builder.addEventListeners(new Info());
        builder.addEventListeners(new Clear());
        builder.addEventListeners(new Loot());
        builder.addEventListeners(new LootList());
        builder.addEventListeners(new NoUReader());
        builder.addEventListeners(new Ping());
        builder.addEventListeners(new Incoming());
        //GatewayIntent for event etc.
        for(final GatewayIntent gatewayIntent : GatewayIntent.values()){
            builder.enableIntents(gatewayIntent);
        }
        builder.build();

    }

}
