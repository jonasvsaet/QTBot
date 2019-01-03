package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;

/**
 * Created by jonas on 24/01/2017.
 */
public class BanCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Member member = event.getGuild().getMember(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);

        if(member.hasPermission(Permission.BAN_MEMBERS)){
            for (User eachUser: event.getMessage().getMentionedUsers()){
                event.getGuild().getController().ban(eachUser.getId(), 5).queue();
                embedBuilder.setThumbnail("http://img06.deviantart.net/5d8d/i/2011/261/b/6/reimu_is_not_amused_by_yukirumo990-d4a7diz.png");
                embedBuilder.setDescription("Enjoy your VACation " + eachUser.getName());
            }
        } else {
            embedBuilder.setDescription("You don't have the permission to use this command");
        }

        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return "used to ban people. You need the ban permission to use this.";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
