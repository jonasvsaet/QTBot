package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by jonas on 12/18/2016.
 */
public class UserinfoCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

        List<User> users = event.getMessage().getMentionedUsers();
        User originalUser = event.getAuthor();


        if(users.size() == 0){
            User user = event.getAuthor();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            OffsetDateTime creationTime = user.getTimeCreated();
            Member member = event.getGuild().getMember(user);
            OffsetDateTime joinTime = member.getTimeJoined();
            String currentGame;
            String rolesText = "";
            List<Role> roles = member.getRoles();

            if (member.getActivities() == null || member.getActivities().size() == 0){
                currentGame = "Not playing a game";
            } else {
                currentGame = member.getActivities().get(0).getName();
            }

            if (roles.size() >= 1){
                rolesText += roles.get(0).getName();
            }
            for (int i = 1; i < roles.size(); i++) {
                rolesText += ", " + roles.get(i).getName();
            }

            embedBuilder.setColor(Color.green);
            embedBuilder.setThumbnail(user.getAvatarUrl());
            embedBuilder.addField("Name", user.getName(), true);
            embedBuilder.addField("Id", user.getId(), true);
            embedBuilder.addField("Joined Server", member.getTimeJoined().format(formatter), true);
            embedBuilder.addField("Joined Discord", user.getTimeCreated().format(formatter), true);
            embedBuilder.addField("Current Game", currentGame, true);
            embedBuilder.addField("Roles", rolesText, true);

            MessageEmbed messageEmbed = embedBuilder.build();
            event.getChannel().sendMessage(messageEmbed).queue();
        } else {
            for (User user : users) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                OffsetDateTime creationTime = user.getTimeCreated();
                Member member = event.getGuild().getMember(user);
                OffsetDateTime joinTime = member.getTimeJoined();
                String currentGame;
                String rolesText = "";
                List<Role> roles = member.getRoles();


                if (member.getActivities() == null || member.getActivities().size() == 0){
                    currentGame = "Not playing a game";
                } else {
                    currentGame = member.getActivities().get(0).getName();
                }
                if (roles.size() >= 1){
                    rolesText += roles.get(0).getName();
                }
                for (int i = 1; i < roles.size(); i++) {
                    rolesText += ", " + roles.get(i).getName();
                }

                embedBuilder.setColor(Color.green);
                embedBuilder.setThumbnail(user.getAvatarUrl());
                embedBuilder.addField("Name", user.getName(), true);
                embedBuilder.addField("Id", user.getId(), true);
                embedBuilder.addField("Joined Server", member.getTimeJoined().format(formatter), true);
                embedBuilder.addField("Joined Discord", user.getTimeCreated().format(formatter), true);
                embedBuilder.addField("Current Game", currentGame, true);
                embedBuilder.addField("Roles", rolesText, true);

                MessageEmbed messageEmbed = embedBuilder.build();
                event.getChannel().sendMessage(messageEmbed).queue();


            }
        }


    }

    @Override
    public String help() {
        return "Gives back information about the user. Usage: !userinfo @user";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
