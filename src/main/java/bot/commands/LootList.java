package bot.commands;

import bot.Bot;
import bot.readers.LootSheet;
import bot.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class LootList extends ListenerAdapter {
    protected final DecimalFormat f = new DecimalFormat("###,###,###");
    protected final ArrayList<Player> members = new ArrayList<>();

    //Reads messages in chat and handles ~lootlist command
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Bot.prefix + "lootList")) {
            readFile(members);
            EmbedBuilder list = new EmbedBuilder();
            list.setTitle("This Week's Loot");
            list.setColor(0x00ff08);
            list.addField("*<:fireworks:827276473026215976>Top Looter<:fireworks:827276473026215976>*", members.get(0).toString(), false);
            list.addField("The Next Five:", getTopFive(members), false);
            list.addField("Honorable Mentions:", honorableMentions(members), true);
            list.addField("Stats:", stats(members, f), false);
            list.setFooter("\nShoutout to Maz for doing loot!");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(list.build()).queue();
            list.clear();
            members.clear();
        }
    }
    //File reader
    private void readFile(ArrayList<Player> members) {
        try{
            LootSheet.main(null);
            Scanner sc = new Scanner(new File("numbers.txt"));
            while (sc.hasNext()) {
                String name = sc.next();
                int total = sc.nextInt();
                members.add(new Player(name, total));
            }
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("File was not found");
        }
    }

    //Returns the next top 5 loot scores after the first.
    private static String getTopFive(ArrayList<Player> members){
        StringBuilder total = new StringBuilder();
        for(int i=1;i<6;i++)
            total.append("<:white_small_square:827279848853733406>").append(members.get(i).toString()).append("\n");
        return total.toString();
    }
    //Returns players who's loot exceeded 30mil (weekly)
    private static String honorableMentions(ArrayList<Player> members) {
        StringBuilder total = new StringBuilder();
        for (int i = 6; i < members.size(); i++) {
            if (members.get(i).getLoot() >= 30000000)
                total.append("<:white_small_square:827279848853733406>").append(members.get(i).toString()).append("\n");
        }
        return total.toString();
    }
    //Returns Loot Goal, # who reached the goal, and the alliance weekly average
    private static String stats(ArrayList<Player> members, DecimalFormat f){
        String total = "Loot Goal: 15mil";
        int made=0;
        for (Player member : members) {
            if (member.getLoot() >= 15000000)
                made++;
        }
        total+= "\n# who succeeded: "+made+"\nAverage: "+getAverage(members, f);

        return total;
    }
    //Returns average of alliance loot for the week
    private static String getAverage(ArrayList<Player> members, DecimalFormat f) {
        int total = 0;

        for (Player member : members) {
            total += member.getLoot();
        }
        return f.format(total / members.size());
    }

}
