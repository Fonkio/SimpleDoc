package fr.fonkio.command.impl;

import fr.fonkio.MvWildBot;
import fr.fonkio.command.AbstractCommand;
import fr.fonkio.utils.Doc;
import fr.fonkio.utils.EmbedGenerator;
import fr.fonkio.utils.IdEnum;
import fr.fonkiomessage.StringsConst;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class RemoveDocCommand extends AbstractCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(guild == null) {
            return;
        }
        OptionMapping idOptionMapping = event.getOption(IdEnum.ARG_ID.getId());
        if (idOptionMapping == null) {
            return;
        }
        String docId = idOptionMapping.getAsString();
        Doc docToRemove = new Doc(docId, "", "", "");
        if (MvWildBot.CONFIGURATION.containsGuildDoc(guild.getId(), docId)) {
            MvWildBot.CONFIGURATION.removeGuildDocList(guild.getId(), docToRemove);
            event.replyEmbeds(EmbedGenerator.generate(event.getUser(), StringsConst.COMMAND_REMOVEDOC_TITLE, String.format(StringsConst.COMMAND_REMOVEDOC_SUCCESS_DESC, docId))).setEphemeral(true).queue();
        } else {
            event.replyEmbeds(EmbedGenerator.generate(event.getUser(), StringsConst.COMMAND_REMOVEDOC_TITLE, StringsConst.COMMAND_ERROR_NOT_EXIST_DESC)).setEphemeral(true).queue();
        }
    }
}
