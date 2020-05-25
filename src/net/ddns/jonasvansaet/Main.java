package net.ddns.jonasvansaet;

import net.ddns.jonasvansaet.Commands.Admin.*;
import net.ddns.jonasvansaet.Commands.Debug.*;
import net.ddns.jonasvansaet.Commands.Games.EightBallCommand;
import net.ddns.jonasvansaet.Commands.Games.RollCommand;
import net.ddns.jonasvansaet.Commands.Lookup.*;
import net.ddns.jonasvansaet.Commands.Quote.GetquoteCommand;
import net.ddns.jonasvansaet.Commands.Quote.InsertquoteCommand;
import net.ddns.jonasvansaet.DA.DAReminder;
import net.ddns.jonasvansaet.Objects.Reminders;
import net.ddns.jonasvansaet.utils.*;
import net.dv8tion.jda.api.JDA;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Main {

    public static JDA jda;
    public static HashMap<String, Command> commands = new HashMap<>();
    public static CommandParser parser = new CommandParser();
    public static WelcomeServers welcomeServers = new WelcomeServers();
    public static ByeServers byeServers = new ByeServers();

    public static void main(String[] args) {
        Logger.logLine("Bot booted");
        try
        {
            JDABuilder builder = JDABuilder.createDefault(Config.botToken);
            builder.addEventListeners(new CommandListener());
            builder.addEventListeners(new JoinListener());
            builder.setActivity(Activity.playing("\"@mention help\" for help"));
            builder.setAutoReconnect(true);
            jda = builder.build().awaitReady();

            /*jda = new JDABuilder(AccountType.BOT)
                    .setToken(Config.devBotToken)
                    .addEventListeners(new CommandListener())
                    .addEventListeners(new JoinListener())
                    //.setActivity(Activity.playing("\"@mention help\" for help"))
                    .build().awaitReady();
            jda.setAutoReconnect(true);*/
            Logger.logLine("bot built");
        }
        catch (Exception e)
        {
            Logger.logLine(e.getMessage());
            e.printStackTrace();
        }

        Logger.logLine("adding commands");


        //add all the commands
        commands.put("ping", new PingCommand());
        commands.put("boottime", new BoottimeCommand(LocalDateTime.now()));
        commands.put("uptime", new UptimeCommand(LocalDateTime.now()));
        commands.put("echo", new EchoCommand());
        commands.put("userinfo", new UserinfoCommand());
        commands.put("getquote", new GetquoteCommand());
        commands.put("insertquote", new InsertquoteCommand());
        commands.put("yt", new YoutubeCommand());

        commands.put("img", new ImgCommand());
        commands.put("lmgtfy", new LmgtfyCommand());
        commands.put("ban", new BanCommand());
        commands.put("kick", new KickCommand());
        commands.put("admin", new AdminCommand());
        commands.put("welcomemessage", new WelcomemessageCommand());
        commands.put("8ball", new EightBallCommand());
        commands.put("roll", new RollCommand());
        commands.put("leavemessage", new LeavemessageCommand());
        commands.put("ud", new UrbanDictionaryCommand());
        commands.put("serverinfo", new ServerinfoCommand());
        commands.put("anime", new AnimeCommand());
        commands.put("manga", new MangaCommand());
        commands.put("mute", new MuteCommand());
        commands.put("unmute", new UnMuteCommand());
        commands.put("hentai", new HentaiCommand());
        commands.put("philadelphia", new PhiladelphiaCommand());
        commands.put("remindme", new RemindMeCommand());
        commands.put("prefix", new PrefixCommand());

        commands.put("help", new HelpCommand(commands));

        Logger.logLine("Commands Loaded");

        welcomeServers.updateWelcomeMessageServers();
        welcomeServers.updatePrefixServers();
        byeServers.updateByeMessageServers();
        IgnoredPeople.loadIgnoredPeople();

        Logger.logLine("welcomeServers updated");

        Logger.logLine("getting reminders");

        DAReminder daReminder = new DAReminder();
        ArrayList<Reminders> reminders = daReminder.getReminders();

        for (Reminders reminder: reminders) {
            if(LocalDateTime.now().isAfter(reminder.getTime().toLocalDateTime()) || LocalDateTime.now().isEqual(reminder.getTime().toLocalDateTime())) {
                MessageChannel channel = jda.getTextChannelById(reminder.getChannelId());

                User user = jda.retrieveUserById(reminder.getUserId()).complete();
                channel.sendMessage(user.getAsMention() + " " + reminder.getReminder()).queue();
                daReminder.removeReminder(reminder.getId());
            }
            else {
                long diff = ChronoUnit.NANOS.between(LocalDateTime.now(), reminder.getTime().toLocalDateTime());

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                MessageChannel channel = jda.getTextChannelById(reminder.getChannelId());
                                channel.sendMessage(((TextChannel) channel).getGuild().getMemberById(reminder.getUserId()).getAsMention() + " " + reminder.getReminder()).queue();

                                DAReminder reminderAL = new DAReminder();
                                reminderAL.removeReminder(reminder.getId());
                            }
                        },
                        diff
                );

            }
        }

        Logger.logLine("reminders set and registered");

    }

    public static void handleCommand(CommandParser.CommandContainer command){
        if (commands.containsKey(command.getInvoke())){
            Random random = new Random();
            commands.get(command.getInvoke()).action(command.getEvent());

            int rng = random.nextInt(100);
            if (rng == 1){
                command.getEvent().getChannel().sendMessage("https://discord.gg/ZZp86F6").queue();
            }
            Logger.logEvent(command.getEvent());
        }
    }

}