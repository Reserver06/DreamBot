package bot.commands;

import bot.Bot;
import bot.readers.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Add extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");


        if(args[0].equalsIgnoreCase(Bot.prefix + "add") && args.length>=3) {
            String discordId = "<@!"+event.getAuthor().getId()+">";
            String name = args[1];
            long phone = Long.parseLong(args[2].replaceAll("\\D+",""));

            Database data = new Database();
            try {
                data.addData(discordId, name, phone);
                event.getChannel().sendMessage("Info was successfully added.").queue();
            } catch (SQLIntegrityConstraintViolationException e){

                try {
                    data.updateData(event.getAuthor().getId(),name,phone);
                    event.getChannel().sendMessage("Info was successfully updated.").queue();
                } catch (SQLException ex) {
                    event.getChannel().sendMessage("Info was unsuccessfully added. Reach out to an admin if you require further assistance.").queue();
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                event.getChannel().sendMessage("Info was unsuccessfully added. Reach out to an admin if you require further assistance.").queue();
                e.printStackTrace();
            }

        }
        if(args[0].equalsIgnoreCase(Bot.prefix + "add") && args.length<3) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xf70505);
            error.setTitle("Incorrect Syntax");
            error.setDescription("~add [Player_Name] [Phone_Number] -" +
                    " Make sure your name is exactly as is in-game.");
            event.getChannel().sendMessage(error.build()).queue();
        }
    }
}
