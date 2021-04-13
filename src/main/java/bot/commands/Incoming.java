package bot.commands;

import bot.Bot;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Incoming extends ListenerAdapter {
    public static final String ACCOUNT_SID="AC7d17f961390d2b8fa96a48d14e462c65";
    public static final String AUTH_TOKEN="bec47f38bf389fb295b2e1079d95f7aa";

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Bot.prefix + "incoming") /*&& args.length > 1*/) {
            int num = 2;
            String BODY = "DreamBot: incoming landing in "+num+"mins";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(
                    new PhoneNumber("+19293777895"),
                    new PhoneNumber("+17632517836"), BODY).create();
            event.getChannel().sendMessage("SMS Sent").queue();
        }
    }
}
