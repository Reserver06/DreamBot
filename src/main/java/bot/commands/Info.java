package bot.commands;

import bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String list = "<:white_small_square:827279848853733406>~loot [name] - Displays most recent loot information.\n" +
                "<:white_small_square:827279848853733406>~lootList - Displays alliance weekly loot info\n" +
                "<:white_small_square:827279848853733406>~clear [amount] - Deletes messages specified.\n"+
                "<:white_small_square:827279848853733406>~info - Displays the info you are currently reading";

        if(args[0].equalsIgnoreCase(Bot.prefix + "info")){
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("DreamBot - DT");
            info.setDescription("A multi-purpose Bot designed for the DT server");
            info.setColor(0x0e37ed);
            info.addField("Commands:",list,false);
            info.setFooter("Created by Colossus/reserver");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }

}
