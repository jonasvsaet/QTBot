package net.ddns.jonasvansaet;

import net.ddns.jonasvansaet.Objects.ByeSettings;
import net.ddns.jonasvansaet.Objects.Serversettings;
import net.ddns.jonasvansaet.utils.ByeServers;
import net.ddns.jonasvansaet.utils.WelcomeServers;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


/**
 * Created by jonas on 31/01/2017.
 */
public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){

        if (WelcomeServers.welcomeServers.containsKey(event.getGuild().getId())) {
            String key = event.getGuild().getId();
            Serversettings serversettings = WelcomeServers.welcomeServers.get(key);
            String welcomeMessage = serversettings.getWelcomeMessage().replace("%user%", event.getMember().getAsMention());
            event.getGuild().getTextChannelById(serversettings.getWelcomeChannelId()).sendMessage(welcomeMessage).queue();
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event){
        if (ByeServers.byeservers.containsKey(event.getGuild().getId())) {
            String key = event.getGuild().getId();
            ByeSettings byeSettings = ByeServers.byeservers.get(key);
            String byeMessage = byeSettings.getByeMessage().replace("%user%", event.getMember().getEffectiveName());
            event.getGuild().getTextChannelById(byeSettings.getByeChannelId()).sendMessage(byeMessage).queue();
        }
    }

}
