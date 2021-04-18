package bot.commands;

import bot.Bot;
import bot.Contacts;
import bot.readers.ContactSheet;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Scanner;

public class Incoming extends ListenerAdapter {
    public static final String ACCOUNT_SID="AC7d17f961390d2b8fa96a48d14e462c65";
    public static final String AUTH_TOKEN="bec47f38bf389fb295b2e1079d95f7aa";
    public static final String TWILIO_NUMBER = "+17632517836";

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        ArrayList<Contacts> contacts = new ArrayList<>();

        if (args[0].equalsIgnoreCase(Bot.prefix + "incoming") && args.length > 2) {
            StringBuilder concat= new StringBuilder();
            boolean exists =false;
            for(int i=2;i<args.length;i++)
                concat.append(args[i]).append(" ");
            String BODY = "DreamBot: incoming in "+ concat;
            String LANDING = args[3];
            readFile(contacts);

            for (Contacts contact : contacts) {
                if (contact.getName().equalsIgnoreCase(args[1]) && !args[2].equals("call")) {
                    exists = true;
                    String num = "+" + contact.getNum();
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message.creator(
                            new PhoneNumber(num),
                            new PhoneNumber(TWILIO_NUMBER), BODY).create();
                    event.getChannel().sendMessage("SMS sent to " + contact.getName()).queue();
                }
            }
            for(Contacts contacts1 : contacts) {
                if (contacts1.getName().equalsIgnoreCase(args[1]) && args[2].equalsIgnoreCase("call")) {
                    exists = true;
                    String num2 = "+" + contacts1.getNum();
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Call.creator(
                            new PhoneNumber(num2),
                            new PhoneNumber(TWILIO_NUMBER),
                            new Twiml("<Response><Say voice=\"alice\">This is DreamBot.There is an incoming landing in " + LANDING + "</Say></Response>")).create();
                }
            }
            if (exists==false){
                EmbedBuilder notFound = new EmbedBuilder();
                notFound.setTitle("Player Not Found");
                notFound.setDescription("There is no number associated with the player: "+args[1]);
                notFound.setColor(0xf70505);
                event.getChannel().sendMessage(notFound.build()).queue();
            }
            contacts.clear();
        }
        if (args[0].equalsIgnoreCase(Bot.prefix + "incoming") && args.length <3) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xf70505);
            error.setTitle("Incorrect Syntax");
            error.setDescription("~incoming [Player_Name] [Time Until Landing] [Optional Notes] - Only a players in-game name will work." +
                            " Make sure to remove any spaces in the name itself.");
            event.getChannel().sendMessage(error.build()).queue();
        }

    }
    private void readFile(ArrayList<Contacts> contacts) {
        try{
            ContactSheet.main(null);
            Scanner sc = new Scanner(new File("contacts.txt"));
            while (sc.hasNext()) {
                String name = sc.next();
                String number = sc.next();
                contacts.add(new Contacts(name,number));
            }
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("File was not found");
        }
    }
}
