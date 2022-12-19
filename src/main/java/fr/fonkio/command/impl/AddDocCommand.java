package fr.fonkio.command.impl;

import fr.fonkio.MvWildBot;
import fr.fonkio.command.AbstractCommand;
import fr.fonkio.utils.Doc;
import fr.fonkio.utils.EmbedGenerator;
import fr.fonkio.utils.IdEnum;
import fr.fonkiomessage.StringsConst;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class AddDocCommand extends AbstractCommand {
    @Override
    public void run(SlashCommandInteractionEvent eventSlash) {
        Guild guild = eventSlash.getGuild();
        User author = eventSlash.getUser();
        if (guild == null) {
            return;
        }
        OptionMapping idOptionMapping = eventSlash.getOption(IdEnum.ARG_ID.getId());
        OptionMapping titleOptionMapping = eventSlash.getOption(IdEnum.ARG_TITLE.getId());
        OptionMapping descOptionMapping = eventSlash.getOption(IdEnum.ARG_DESC.getId());
        OptionMapping linkOptionMapping = eventSlash.getOption(IdEnum.ARG_LINK.getId());
        if (idOptionMapping == null || titleOptionMapping == null || linkOptionMapping == null) {
            return;
        }

        String id = idOptionMapping.getAsString();
        if (id.contains(" ")) {
            eventSlash.replyEmbeds(EmbedGenerator.generate(author, StringsConst.COMMAND_ADDDOC_TITLE, String.format(StringsConst.COMMAND_ADDDOC_ERROR_SPACE_ID_DESC, id))).setEphemeral(true).queue();
            return;
        }
        String title = titleOptionMapping.getAsString();
        String desc;
        if (descOptionMapping == null) {
            desc = StringsConst.DEFAULT_DESC;
        } else {
            desc = descOptionMapping.getAsString();
        }
        String link = linkOptionMapping.getAsString();

        Doc docToAdd = new Doc(id, title, desc, link);

        if (MvWildBot.CONFIGURATION.containsGuildDoc(guild.getId(), id)) {
            eventSlash.replyEmbeds(EmbedGenerator.generate(author, StringsConst.COMMAND_ADDDOC_TITLE, String.format(StringsConst.COMMAND_ADDDOC_ERROR_ID_ALREADY_EXIST_DESC, id))).setEphemeral(true).queue();
            return;
        }

        MvWildBot.CONFIGURATION.addGuildDocList(guild.getId(), docToAdd);
        eventSlash.replyEmbeds(EmbedGenerator.generate(author, StringsConst.COMMAND_ADDDOC_TITLE, String.format(StringsConst.COMMAND_ADDDOC_SUCCESS_DESC, id))).setEphemeral(true).queue();
    }
}
