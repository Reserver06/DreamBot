package bot.commands;

import bot.Bot;
import bot.readers.LootSheet;
import bot.readers.LootSheetAvg;
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

public class Loot extends ListenerAdapter {
    private final DecimalFormat f = new DecimalFormat("###,###,###");
    private final ArrayList<Player> members = new ArrayList<>();

    //Event Listener
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Bot.prefix + "loot")) {
            readFile(members);
            try {
                lootTotal(args, members, event);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
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

    //Sends embedded message of loot total
    private void lootTotal(String [] args, ArrayList<Player> members,GuildMessageReceivedEvent event) throws IOException, GeneralSecurityException {
        if(args.length>1 && checkLoot(args,members)>-1) {
            EmbedBuilder loot = new EmbedBuilder();
            loot.setTitle("Loot stats for "+ args[1]);
            loot.addField("Weekly Loot: ", f.format(checkLoot(args, members)),false);
            loot.addField("Overall Average: ",f.format(memberAverage(args[1])),false);
            loot.setColor(0x00ff08);


            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(loot.build()).queue();
            loot.clear();
        }
        else{
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xf70505);
            error.setTitle("Player Not Found");
            error.setDescription("~loot [name of player] -- Make sure to remove all spaces from the name.");
            event.getChannel().sendMessage(error.build()).queue();
        }

    }
    //Confirms player and checks loot
    private int checkLoot(String [] args,ArrayList<Player> members){
        for (Player member : members) {
            if (member.getName().equalsIgnoreCase(args[1]))
                return member.getLoot();
        }
        return -1;
    }
    //Gets player specific loot average
    private static int memberAverage(String name) throws IOException, GeneralSecurityException {
        ArrayList<Player> temp = new ArrayList<>();
        LootSheetAvg.main(null);
        Scanner sc = new Scanner(new File("averages.txt"));
        while (sc.hasNext()) {
            String aName = sc.next();
            int total = sc.nextInt();
            temp.add(new Player(aName, total));
        }
        for(Player k : temp) {
            if (k.getName().equalsIgnoreCase(name))
                return k.getLoot();
        }
        return 0;
    }
}
