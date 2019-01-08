package net.ddns.jonasvansaet;

import net.ddns.jonasvansaet.Commands.Admin.*;
import net.ddns.jonasvansaet.Commands.Debug.*;
import net.ddns.jonasvansaet.Commands.Games.EightBallCommand;
import net.ddns.jonasvansaet.Commands.Games.RollCommand;
import net.ddns.jonasvansaet.Commands.Lookup.*;
import net.ddns.jonasvansaet.Commands.Quote.GetquoteCommand;
import net.ddns.jonasvansaet.Commands.Quote.InsertquoteCommand;
import net.ddns.jonasvansaet.utils.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.time.LocalDateTime;
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
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(Config.devBotToken)
                    .addEventListener(new CommandListener())
                    .addEventListener(new JoinListener())
                    .setGame(Game.playing("\"@mention help\" for help"))
                    .buildBlocking();

            jda.setAutoReconnect(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


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