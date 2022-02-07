package bot.commands;

import bot.Contacts;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class Call extends ListenerAdapter {
    Dotenv dotenv = Dotenv.load();
    public final String ACCOUNT_SID= dotenv.get("ACCOUNT_SID");
    public final String AUTH_TOKEN= dotenv.get("AUTH_TOKEN");
    public final String TWILIO_NUMBER = dotenv.get("TWILIO_NUMBER");

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        ArrayList<Contacts> contacts = new ArrayList<>();




    }
}
